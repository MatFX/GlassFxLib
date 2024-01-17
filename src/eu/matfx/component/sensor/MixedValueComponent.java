package eu.matfx.component.sensor;

import eu.matfx.component.ButtonRectangle;
import eu.matfx.tools.AColor_Component;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.Canvas;

/**
 * presentation of text oder images as values.
 * @author Matthias
 *
 */
public class MixedValueComponent extends AValueComponent
{
	
	//started dimension from the svg file
	private double width_component = 90;
	private double height_component = 100;
	
	private Canvas topCanvas, bottomCanvas, textCanvas;
	
	protected ButtonRectangle button_up, button_down;
	
	private SimpleObjectProperty<AColor_Component> valueTopProperty = new SimpleObjectProperty<AColor_Component>();
	
	private SimpleObjectProperty<AColor_Component> valueProperty = new SimpleObjectProperty<AColor_Component>();
	
	private SimpleObjectProperty<AColor_Component> valueBottomProperty = new SimpleObjectProperty<AColor_Component>();
		

	@Override
	protected void initGraphics() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void registerListener() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void reColoredComponent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void resize() {
		// TODO Auto-generated method stub
		
	}

}
