package eu.matfx.tools;


import javafx.scene.paint.Color;

public abstract class AColor_Component 
{
	
	protected String value;
	
	protected Color color;

	protected AColor_Component(String value) 
	{
		this.value = value;
	}

	/**
	 * Get color of the component
	 * @return
	 */
	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
		
	}
	
	/**
	 * Return the string value eg path to image or formatted text value
	 * @return
	 */
	public String getValue()
	{
		return value;
	}


}
