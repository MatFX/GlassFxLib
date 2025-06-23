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
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
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
	@Override
    protected void layoutChildren() 
	{
		this.setWidth(500);
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
	    		//System.out.println("Index " + ((MixedValueComponent)node).getTopValueProperty().get().getValue() +" >> " + map.get(node).getRight() + " on Pane? " + map.get(node).getLeft());
	    	}
	    	else
	    	{
	    		System.out.println("Index " + ((MixedValueComponent)node).getTopValueProperty().get().getValue() +" >> " + map.get(node).getRight() + " on Pane? " + map.get(node).getLeft());
	    	}
	    });
	    System.out.println("w: "+ this.getWidth() +  " ------ h: " +this.getHeight()+"------------------------------------------------------------");
	    
	   
	    /*
	    System.out.println("? " + layoutList.size());
	    for(GenericPair<Node, LayoutBox> genPair : layoutList)
	    {
	    	System.out.println("" + genPair.getRight().toString());
	    }*/
	    
//	    double x_start = hGap + 1;
//        double y_start = vGap + 1;
        //Einbehaltung der Reihenfolge vom hinzufügen
        for(Node node : this.getChildren())
        {
        	if(!map.get(node).getLeft().booleanValue())
         	{
        		//Die Node war bisher noch nicht auf der Map
        		double x_start = hGap + 1;
        	    double y_start = vGap + 1;
        	    double nodeWidth = node.prefWidth(-1);
                double nodeHeight = node.prefHeight(-1);
        		
                
                
              //GenericPair<Double, Double> calculatedCoordinates = getCalculatedCoordinates(x_start, y_start, nodeWidth, nodeHeight);
                
                
        	    for(GenericPair<Node, LayoutBox> genNodelayout : layoutList)
        	    {
        	    	double temp_x = genNodelayout.getRight().getLayoutX();
        	    	double temp_y = genNodelayout.getRight().getLayoutY();
        	    	
        	    	
        	    	//x hinzufügen und anschließend prüfen ob 
        	    	x_start = temp_x + genNodelayout.getRight().getWidth() + hGap + 1;
        	    	System.out.println("this.getWidth() " + this.getWidth());
        	      	System.out.println("(x_start + map.get(node).getRight().getWidth() + hGap + 1) " + (x_start + map.get(node).getRight().getWidth() + hGap + 1));
        	    	if((x_start + nodeWidth + hGap + 1) > getWidth())
        	    	{
        	    		
        	    		
        	    		//dann nächste zeile bzw. freie Position suchen
        	    		System.out.println("müsste doch breiter sein");
        	    		y_start = temp_y + genNodelayout.getRight().getHeight() + vGap + 1;
        	    		x_start = hGap + 1;
        	    		
        	    		
        	    	
        	    	
        	    	}
        	    		
        	    	
        	    	//y_start = temp_y + genNodelayout.getRight().getHeight() + vGap + 1;
        	    	
        	    	
        	    	
        	    }
        	    node.resizeRelocate(x_start, y_start, nodeWidth, nodeHeight);
        	    map.get(node).setLeft(Boolean.valueOf(true));
        	    
        	    layoutList.add(new GenericPair<Node, LayoutBox>(node,  map.get(node).getRight()));
        	    layoutList.sort(Comparator.comparing(GenericPair::getRight, LayoutBoxComparator.getInstance()));
        	    
        	}
        	
        	
        	
        }
        
       /*
        //Einbehaltung der Reihenfolge vom hinzufügen
        for(Node node : this.getChildren())
        {
        	double nodeWidth = node.prefWidth(-1);
            double nodeHeight = node.prefHeight(-1);
        	
            //
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
            	
            	
            	
            	

                System.out.println("nodeWidth " + nodeWidth);
                System.out.println("x  " + node.getLayoutX());
               // double tempLastPos =  x_start + nodeWidth + hGap + 1;
                
                
                double tempLastPos = x_start + nodeWidth + hGap + 1;
                //new line if the node goes over the layout width
                if(tempLastPos > getWidth())
                {
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
                    		
                    		if(collides)
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
        	
        	
        	
        	
        }*/
     }
	
	 private GenericPair<Double, Double> getCalculatedCoordinates(double x_start, double y_start, double nodeWidth, double nodeHeight) 
	 {
		 GenericPair<Double, Double> returnValue = new GenericPair<Double, Double>(x_start, y_start);
		 
		 for(GenericPair<Node, LayoutBox> genNodeLayout : layoutList)
		 {
			 if(genNodeLayout.getRight().getLayoutY() == y_start)
			 {
				double temp_x = genNodeLayout.getRight().getLayoutX();
     	    	//double temp_y = genNodeLayout.getRight().getLayoutY();

    	    	//x hinzufügen und anschließend prüfen ob 
    	    	x_start = temp_x + genNodeLayout.getRight().getWidth() + hGap + 1;
    	    	returnValue.setLeft(x_start);
    	    	if((x_start + nodeWidth + hGap + 1) > getWidth())
    	    	{
    	    		//Ende wurde erreicht, dass bedeutet es wird das kleinste Element benötigt von y_start
    	    		GenericPair<Node, LayoutBox> lowestHeightLayoutBox = getLowestHeightLayoutBox(y_start);
    	    		
    	    		y_start = lowestHeightLayoutBox.getRight().getLayoutX() + lowestHeightLayoutBox.getRight().getHeight() + vGap + 1;
    	    		returnValue.setLeft(y_start);
    	    		break;
    	    	}
    	    	else
    	    		break;
    	    }
		}
		return returnValue;
	}

	private GenericPair<Node, LayoutBox> getLowestHeightLayoutBox(double targetY) 
	{
		Optional<GenericPair<Node, LayoutBox>> minHeightElement = layoutList.stream()
			    .filter(pair -> pair.getRight().getLayoutY() == targetY)
			    .min(Comparator.comparingDouble(pair -> pair.getRight().getHeight()));

		if (minHeightElement.isPresent()) {
			return minHeightElement.get();
		}
		return null;
	}

	@Override
	 protected double computePrefWidth(double height) {
			System.out.println("aufruf computePrefWidth ");
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
    	System.out.println("aufruf computePrefHeight ");
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
    
}

	 

