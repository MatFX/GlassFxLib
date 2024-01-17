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

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
		
	}

}
