package test.layout;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import eu.matfx.component.sensor.MixedValueComponent;
import eu.matfx.tools.GenericPair;
import eu.matfx.tools.LayoutBox;
import eu.matfx.tools.LayoutBoxComparator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class OwnLayoutPane extends Pane
{
	
	private double lastWidth = -1;
	
	private Map<Node, GenericPair<Boolean, LayoutBox>> map = new HashMap<Node,  GenericPair<Boolean, LayoutBox>>();
	
	private List<GenericPair<Node, LayoutBox>> layoutList = new ArrayList<GenericPair<Node, LayoutBox>>();
	
	
	private enum ScalingDirection
	{
		No_Changing,
		
		Minimize,
		
		Maximize;
		
		
		
		
	}
	
	public enum Direction
	{
		NORTH,
		WEST,
		SOUTH,
		EAST,
		
		;
		
	}
	
	
	private final double hGap = 10;
	private final double vGap = 10;
	    
	private final Insets padding = new Insets(5);
	
	public OwnLayoutPane()
	{
		super();
	}
	
	public OwnLayoutPane(Node... children)
	{
		getChildren().addAll(children);
	}
	
	/**
	 * empty map needed for the initialize of the neighbormap
	 * @return
	 */
	private HashMap<Direction, List<Node>> getEmptyOrientationMap()
	{
		return new HashMap<Direction, List<Node>>();
	}
	
	private Point2D findFreeSpot(
		    Map<Node, GenericPair<Boolean, LayoutBox>> layoutMap,
		    Node current,
		    double width,
		    double height,
		    double maxWidth
		) {
		    for (double y = padding.getTop(); ; y += 5) {
		        for (double x = padding.getLeft(); x <= maxWidth - width; x += 5) {
		            // Box mit zusätzlichem Gap außenrum
		            BoundingBox testBox = new BoundingBox(
		                x - hGap / 2.0,
		                y - vGap / 2.0,
		                width + hGap,
		                height + vGap
		            );

		            boolean fits = layoutMap.entrySet().stream()
		                .filter(e -> e.getValue().getLeft() && !e.getKey().equals(current))
		                .map(e -> {
		                    Node n = e.getKey();
		                    return new BoundingBox(
		                        n.getLayoutX() - hGap / 2.0,
		                        n.getLayoutY() - vGap / 2.0,
		                        n.getLayoutBounds().getWidth() + hGap,
		                        n.getLayoutBounds().getHeight() + vGap
		                    );
		                })
		                .noneMatch(box -> box.intersects(testBox));

		            if (fits) return new Point2D(x, y);
		        }
		    }
		}
	
	
	@Override
    protected void layoutChildren() 
	{
		//this.setWidth(500);
		//for (int i = 0; i < 50; ++i) System.out.println();
		//Breite wird benötigt um zu unterscheiden ob es in die nächste Zeile muss
		double totalWidth = getWidth() - padding.getLeft() - padding.getRight();
		
	    if (getChildren().isEmpty()) return;

	    ScalingDirection scalingDirection = ScalingDirection.No_Changing;
	    //prüfun in welche Richtung es geht
	    //von rechts nach links (verkleinerung)
	    //von links nach rechts (vergößre)
	    if(lastWidth != -1)
	    {
	    	if(lastWidth > totalWidth)
	    		 scalingDirection = ScalingDirection.Minimize;
	    	else if(lastWidth < totalWidth)
	    		scalingDirection = ScalingDirection.Maximize;
	    }
	    lastWidth = totalWidth;
    	System.out.println("scalingDirection " + scalingDirection);
	    
	    this.getChildren().stream().forEach(node -> {
	    	if(!map.containsKey(node))
	    	{
	    		LayoutBox layoutBox = new LayoutBox(node.getLayoutX(), node.getLayoutY(), node.getLayoutBounds().getWidth(), node.getLayoutBounds().getHeight());
	            
	    		layoutBox.layoutXProperty().bind(node.layoutXProperty());
	    		layoutBox.layoutYProperty().bind(node.layoutYProperty());
	    		node.layoutBoundsProperty().addListener(new ChangeListener<Bounds>(){

					@Override
					public void changed(ObservableValue<? extends Bounds> p0, Bounds p1, Bounds newBounds)
					{
						if(newBounds != null)
						{
							layoutBox.setWidth(newBounds.getWidth());
							layoutBox.setHeight(newBounds.getHeight());
						}
						
					}
	    			
	    		});

	    		map.put(node, new GenericPair<Boolean, LayoutBox>(false, layoutBox));
	    	}
	    	else
	    	{
	    		System.out.println("Index " + ((MixedValueComponent)node).getTopValueProperty().get().getValue() +" >> " + map.get(node).getRight() + " on Pane? " + map.get(node).getLeft());
	    	}
	    });
	    System.out.println("w: "+ totalWidth +  " ------ h: " +this.getHeight()+"------------------------------------------------------------");
	    
		double x_start = hGap + 1;
	    double y_start = vGap + 1;
	    
        /*
	    for (Node node : this.getChildren()) {
	        double nodeWidth = node.prefWidth(-1);
	        double nodeHeight = node.prefHeight(-1);

	        Point2D spot = findFreeSpot(map, node, nodeWidth, nodeHeight, getWidth());
	        node.resizeRelocate(spot.getX(), spot.getY(), nodeWidth, nodeHeight);
	        map.get(node).setLeft(Boolean.TRUE);
	    }
	    */
	    
	   
	    
        //Einbehaltung der Reihenfolge vom hinzufügen
        for(Node node : this.getChildren())
        {
        	double nodeWidth = node.prefWidth(-1);
            double nodeHeight = node.prefHeight(-1);
        	
            if(!map.get(node).getLeft().booleanValue())
        	{
            	//hinzufügen
                node.resizeRelocate(x_start, y_start, nodeWidth, nodeHeight);
                //Anschließend Ablage in bereits zugewiesen
            	x_start = x_start + nodeWidth + hGap + 1;
                map.get(node).setLeft(Boolean.valueOf(true));
        		continue;
        	}
            else
            {
            	double tempLastPos = x_start + nodeWidth + hGap + 1;
            	System.out.println("tempLastPos " + tempLastPos +  " mixed " + ((MixedValueComponent)node).getTopValueProperty().get().getValue());
                //new line if the node goes over the layout width
                if(tempLastPos > totalWidth)
                {
                	MixedValueComponent mixedValueCompnent = (MixedValueComponent)node;
                	System.out.println("mixedValueCompnent index : " + mixedValueCompnent.getTopValueProperty().get().getValue());
                	
                	//Nodes are not to check...starts with own
                	List<Node> notToCheck = new ArrayList<Node>();
                	notToCheck.add(node);
                	final double ySearch = y_start;
                	//reset the x coord
                	x_start = hGap+1;
                	
                	boolean found = false;
                	do
                	{
                		Optional<Map.Entry<Node, GenericPair<Boolean, LayoutBox>>> minEntry = map.entrySet().stream()
                    			.filter(predicate -> predicate.getValue().getLeft().booleanValue() 
                    			&& !notToCheck.contains(predicate.getKey()))
                    			.min(Comparator.comparing(predicate -> predicate.getKey().getBoundsInParent().getMaxY()));
                		
                		//minEntry vorhanden?
                		if(minEntry.isPresent())
                    	{
                			
                			//jetzt prüfen ob in der gleichen X-Achse noch weitere Objekte liegen
                    		y_start = minEntry.get().getKey().getLayoutY() + minEntry.get().getKey().getLayoutBounds().getHeight() + vGap + 1;
                    		x_start = minEntry.get().getKey().getLayoutX();
                    		
                    		//mit den Positionen prüfen ob  damit eine Node berührt wird
                    		BoundingBox futureBoundsBox =  new BoundingBox(x_start, y_start, nodeWidth + hGap + 1, nodeHeight);
                    		
                    		boolean collides = map.entrySet().stream()
                    				.filter(entry -> entry.getValue().getLeft().booleanValue() 
                    						&& !entry.getKey().equals(node) 
                    						&& !entry.getKey().equals(minEntry.get().getKey()))
                    				.map(entry -> 
                    					new BoundingBox(entry.getKey().getLayoutX(), entry.getKey().getLayoutY(), entry.getKey().getLayoutBounds().getWidth(), entry.getKey().getLayoutBounds().getHeight()))
                    			    .anyMatch(componentBounds -> futureBoundsBox.intersects(componentBounds)
                    			    );
                    
                    		if(collides || futureBoundsBox.getMaxX() > totalWidth)
                    		{
                    			//gefunden node darf nicht verwendet werden.
                    			//System.out.println("collides " + collides);
                    			notToCheck.add(minEntry.get().getKey());
                    		}
                    		else
                    			found = true;
                    	
                    	}
                		else
                			found = true;
                		
                	}
                	while(!found);
                	
              
                }
            	//System.out.println("MixedValueComponent " + ((MixedValueComponent)node).getValueProperty().get().getValue());
            	
                //hinzufügen
                node.resizeRelocate(x_start, y_start, nodeWidth, nodeHeight);
                //Anschließend Ablage in bereits zugewiesen
                
                map.get(node).setLeft(Boolean.valueOf(true));
                
                x_start = x_start + nodeWidth + hGap +1;
            	
            }
        	
        	
        	
        	
        }
     }
	
	

	private boolean isCollision(double x_start, double y_start, double maxW, double maxH) {
		LayoutBox checkBox = new LayoutBox(x_start, y_start, maxW, maxH);
		
		for(GenericPair<Node, LayoutBox> genNodelayout : layoutList)
		{
			if(genNodelayout.getRight().getBoundingBox().intersects(checkBox.getBoundingBox()))
				return true;
			
		}
		return false;
	}

	

	private GenericPair<Node, LayoutBox> getLowestHeightLayoutBox(double targetY, double nodeWidth, double nodeHeight) 
	{
		for(GenericPair<Node, LayoutBox> element : layoutList)
		{
			if(element.getRight().getLayoutY() == targetY)
			{
				double element_start_y = element.getRight().getBoundingBox().getMaxY() + vGap + 1;
	    		double element_start_x = element.getRight().getBoundingBox().getMinX();
				
	    		double maxW = nodeWidth + hGap + 1;
	    		double maxH = nodeHeight + vGap + 1;
	    		
	    		if(!isCollision(element_start_x, element_start_y, maxW, maxH))
	    		{
	    			//Gefunden
	    			return element;
	    			
	    		}
	    		
			}
			
			
		}
		return null;
	}

	
	@Override
	protected double computePrefWidth(double height) {
			// Berechne maximale Zeilenbreite
	        double maxWidth = 0;
	        double x = padding.getLeft() + padding.getRight();
	
	        for (Node child : getChildren()) {
	            double cw = child.prefWidth(-1) + hGap;
	            if (x + cw > maxWidth) {
	                maxWidth = x + cw;
	            }
	            x += cw;
	        }
	        return maxWidth;
	}

    @Override
    protected double computePrefHeight(double width) {
    	// Berechne benötigte Höhe basierend auf Umbrüchen
        double x = padding.getLeft();
        double y = padding.getTop();
        double maxRowHeight = 0;

        for (Node child : getChildren()) {
            double cw = child.prefWidth(-1);
            double ch = child.prefHeight(-1);

            if (x + cw + padding.getRight() > width) {
                x = padding.getLeft();
                y += maxRowHeight + vGap;
                maxRowHeight = 0;
            }
            maxRowHeight = Math.max(maxRowHeight, ch);
            x += cw + hGap;
        }
        // letzte Zeile hinzufügen
        y += maxRowHeight + padding.getBottom();
        return y;
    }

	public LayoutBox getLayoutBox(Node node) {
		return map.get(node).getRight();
	}
    
}

	 

