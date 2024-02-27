package eu.matfx.component;



import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

/**
 * Tiny button, on top or bottom of the sensor component.
 * @author m.goerlich
 *
 */
public class ButtonRectangle extends Rectangle
{
	
	/**
	 * Kommandos können hier empfangen werden (listener anschluss)
	 * <br>to listen from outside connect via {@link #getCommandProperty()}
	 */
	private SimpleObjectProperty<Command> commandProperty = new SimpleObjectProperty<Command>();
	
	private PositionGradient positionGradient;
	
	private double percentValue_X, percenValue_Y, percentValue_Width, percentValue_Height,arcWidthAndHeight;
	
	private Color baseColor = Color.web("#5abaa0");
	
	public enum PositionGradient
	{
		FROM_DOWN_TO_UP,
		
		FROM_UP_TO_DOWN;
	}
	
	
	public enum Command
	{
		BUTTON_PRESSED, BUTTON_RELEASED,
		
		RESET_COMMAND;
	}
	
	/**
	 * 
	 * @param positionGradient
	 * @param percentValue_X
	 * @param percenValue_Y
	 * @param percentValue_Width
	 * @param percentValue_Height
	 * @param arcWidthAndHeight
	 */
	public ButtonRectangle(PositionGradient positionGradient, double percentValue_X, double percenValue_Y, double percentValue_Width, double percentValue_Height, double arcWidthAndHeight)
	{
		this.positionGradient = positionGradient;
		this.percentValue_X = percentValue_X;
		this.percenValue_Y = percenValue_Y;
		this.percentValue_Width = percentValue_Width;
		this.percentValue_Height = percentValue_Height;
		this.arcWidthAndHeight = arcWidthAndHeight;
		
		
		this.setOnMousePressed(e -> setNodeMouseEvent(Command.BUTTON_PRESSED, e));
		this.setOnMouseReleased(e -> setNodeMouseEvent(Command.BUTTON_RELEASED, e));
	}

	/**
	 * change the color after received a new mouse event.
	 */
	private void setNodeMouseEvent(Command commandValue, MouseEvent e) 
	{
		if(commandValue == Command.BUTTON_PRESSED)
		{
			LinearGradient gradient = null;
			if(positionGradient == PositionGradient.FROM_DOWN_TO_UP)
			{
				gradient = new LinearGradient(0, 0.3, 0, 1, true, CycleMethod.NO_CYCLE,
					    new Stop(0, baseColor),
					    new Stop(1, baseColor.darker()));
			}
			else
			{
				gradient = new LinearGradient(0, 0, 0, 0.7, true, CycleMethod.NO_CYCLE,
					    new Stop(1, baseColor),
					    new Stop(0, baseColor.darker()));
			}
			

			this.setFill(gradient);
		}
		else if(commandValue == Command.BUTTON_RELEASED)
		{
			this.setFill(baseColor);
		}
		
		commandProperty.set(commandValue);
		e.consume();
	}

	public SimpleObjectProperty<Command> getCommandProperty()
	{
		return commandProperty;
	}

	/**
	 * Calculates the new dimension of the button from the given width and height.
	 * @param width_component
	 * @param height_component
	 */
	public void refreshSize(double width_component, double height_component) 
	{
		this.setX(width_component * percentValue_X);
		this.setY(height_component * percenValue_Y);
		this.setWidth(width_component * percentValue_Width);
		this.setHeight(height_component *  percentValue_Height);
		this.setArcWidth(width_component * arcWidthAndHeight);
		this.setArcHeight(width_component * arcWidthAndHeight);
		
	}

	/**
	 * set base color to fill the button. It will be used for the linear gradient or the solid color.
	 * @param newValue
	 */
	public void setBaseColor(Color newValue) 
	{
		this.baseColor = newValue;
		this.setFill(baseColor);
	}
	
	
}
