package eu.matfx.tools;

import javafx.scene.paint.Color;

public class Value_Color_Component 
{
	
	private String value;
	
	private Color color;
	
	public Value_Color_Component(String value)
	{
		this.value = value;
	}
	
	public String getValue()
	{
		return value;
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
