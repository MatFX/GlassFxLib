package eu.matfx.tools;

import java.util.Comparator;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.BoundingBox;

/**
 * raw values not the real values from the ui
 * @author m.goerlich
 *
 */
public class LayoutBox
{

	
	private SimpleDoubleProperty layoutXProperty;
	
	private SimpleDoubleProperty layoutYProperty;
	
	private SimpleDoubleProperty widthProperty;
	
	private SimpleDoubleProperty heightProperty;
	
	
	public LayoutBox(double layoutX, double layoutY, double width, double height)
	{
		this.layoutXProperty = new SimpleDoubleProperty(layoutX);
		this.layoutYProperty =  new SimpleDoubleProperty(layoutY);
		this.widthProperty =  new SimpleDoubleProperty(width);
		this.heightProperty =  new SimpleDoubleProperty(height);
		
	}
	
	public SimpleDoubleProperty layoutXProperty()
	{
		return layoutXProperty;
	}
	
	public SimpleDoubleProperty layoutYProperty()
	{
		return layoutYProperty;
	}
	
	public SimpleDoubleProperty widthProperty()
	{
		return widthProperty;
	}
	
	public SimpleDoubleProperty heightProperty()
	{
		return heightProperty;
	}
	

	public double getLayoutX() {
		return layoutXProperty.doubleValue();
	}

	public double getLayoutY() {
		return layoutYProperty.doubleValue();
	}

	public double getWidth() {
		return widthProperty.doubleValue();
	}

	public double getHeight() {
		return heightProperty.doubleValue();
	}

	public void setLayoutX(double layoutX) {
		this.layoutXProperty.set(layoutX);
	}

	public void setLayoutY(double layoutY) {
		this.layoutYProperty.set(layoutY);
	}

	public void setWidth(double width) {
		this.widthProperty.set(width);
	}

	public void setHeight(double height) {
		this.heightProperty.set(height);
	}
	
	public String toString()
	{
		return getBoundingBox().toString();
	}
	
	
	public BoundingBox getBoundingBox()
	{
		return new BoundingBox(this.layoutXProperty.get(), this.layoutYProperty.get(), this.widthProperty.get(), this.heightProperty.get());
	}


}
