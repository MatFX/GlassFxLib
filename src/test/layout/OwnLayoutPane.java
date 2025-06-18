package test.layout;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class OwnLayoutPane extends Pane
{
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
		//Breite wird benötigt um zu unterscheiden ob es in die nächste Zeile muss
		double totalWidth = getWidth() - padding.getLeft() - padding.getRight();
		
	    if (getChildren().isEmpty()) return;

	    
	    

        Map<Node, Boolean> map = new HashMap<Node, Boolean>();
        
        Map<Node, HashMap<Direction, List<Node>>> neighborMap = new HashMap<Node, HashMap<Direction, List<Node>>>();
        
        //Muss eine Node die Nachbarn kennen?
        
        map = this.getChildren().stream().collect(Collectors.toMap(child -> child, child -> false));
        neighborMap = this.getChildren().stream().collect(Collectors.toMap(child -> child, child -> getEmptyOrientationMap()));
        
        System.out.println("map " + map);
        System.out.println("neighborMap " + neighborMap);
        
        //element holen und hinzufügen wenn es in die Breite passt
        
        //wenn nicht dann schauen ob es unter dem Elment passt dass am niedrigsten von der Höhe ist
        
        //vermutlich muss ich die Nachbarn kennen oder`?
        
        double x_start = hGap+1;
        double y_start = vGap+1;
        
        
        for(Entry<Node, Boolean> entry : map.entrySet())
        {
        	Node node = entry.getKey();
        	
        	double nodeWidth = node.prefWidth(-1);
            double nodeHeight = node.prefHeight(-1);
           
            
            double tempLastPos = x_start + nodeWidth + hGap + 1;
            //new line if the node goes over the layout width
            if(tempLastPos > getWidth())
            {
            	
            	for(Entry<Node, Boolean> innerEntry : map.entrySet())
            	{
            		System.out.println("innerEntry " + innerEntry.getKey().getBoundsInParent());
            		System.out.println("innerEntry " + innerEntry.getKey().getLayoutBounds());
            	}
            	
            	
            	//wird benötigt wenn 
            	List<Double> collsionXList = new ArrayList<Double>();
            	final double ySearch = y_start;
            	//reset the x coord
            	x_start = hGap+1;
            	//jetzt y_start ermitteln
            	//dazu werden die Nodes benötigt die bereits hinzugefügt worden sind
            	//TODO funktioniert nicht
            	Optional<Map.Entry<Node, Boolean>> minEntry = map.entrySet().stream()
            			.filter(predicate -> predicate.getKey().getLayoutY() == ySearch && predicate.getValue().booleanValue() && !predicate.getKey().equals(node))
            			.min(Comparator.comparing(predicate -> predicate.getKey().getBoundsInParent().getMaxY()));
            			//.min(Comparator.comparing(predicate -> predicate.getKey().getBoundsInParent().getHeight()));
            	System.out.println("minEntry found? " + minEntry.isPresent());
            	if(minEntry.isPresent())
            	{
            		//jetzt prüfen ob in der gleichen X-Achse noch weitere Objekte liegen
            		
            		
            		
            		
            		
            		System.out.println("minEntry " + minEntry.get().getKey().getBoundsInLocal());
               		System.out.println("minEntry " + minEntry.get().getKey().getBoundsInParent());
               		System.out.println("minEntry " + minEntry.get().getKey().getLayoutY());
            		y_start = minEntry.get().getKey().getLayoutY() + minEntry.get().getKey().getLayoutBounds().getHeight() + vGap + 1;
            		x_start = minEntry.get().getKey().getLayoutX();
            	}
            	System.out.println("y_start now " + y_start);
            	
            	//Prüfung ob mit der ermittelten Position eine anderes Layout berührt würde
            	
            	
            }
            
            //hinzufügen
            node.resizeRelocate(x_start, y_start, nodeWidth, nodeHeight);
            //Anschließend Ablage in bereits zugewiesen
            map.put(node, Boolean.valueOf(true));
            
            
            
            
            x_start = x_start + nodeWidth + hGap +1;
            System.out.println("x_start " + x_start);
          
            
            
            
            
            
        	
        	
        	
        	
        }
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

	 

