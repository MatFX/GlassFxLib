package eu.matfx.component.sensor;


import eu.matfx.component.ButtonRectangle;
import eu.matfx.component.ButtonRectangle.PositionGradient;
import eu.matfx.tools.Command;
import eu.matfx.tools.UIToolBox;
import eu.matfx.tools.Value_Color_Component;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MoreValueComponent extends AValueComponent
{
	
	//started dimension from the svg file
	private double width_component = 90;
	private double height_component = 100;

	private Canvas topCanvas, bottomCanvas, textCanvas;
	
	protected ButtonRectangle button_up, button_down;
	/**
	 * ValueProperty as String. To Change the visulisation of the text canvas set here the value.
	 */
	private SimpleObjectProperty<Value_Color_Component> valueProperty = new SimpleObjectProperty<Value_Color_Component>();
	
	/**
	 * content topCanvas
	 */
	private SimpleObjectProperty<Value_Color_Component> valueTopProperty = new SimpleObjectProperty<Value_Color_Component>();
	
	/**
	 * content bottomCanvas
	 */
	private SimpleObjectProperty<Value_Color_Component> valueBottomProperty = new SimpleObjectProperty<Value_Color_Component>();
	
		
	public MoreValueComponent()
	{
		super();
		this.initGraphics();
		this.registerListener();
	}
	

	protected void initGraphics() {
		
		
		topCanvas = new Canvas();
		textCanvas = new Canvas();
		bottomCanvas = new Canvas();
		
		button_down = new ButtonRectangle(PositionGradient.FROM_UP_TO_DOWN, 0.23555555562222225, 0.92000000001, 0.5288888887222222, 0.07999999995, 0.022222222222222223);
		button_down.setFill(baseColor.get());
		button_down.getCommandProperty().addListener(new ChangeListener<ButtonRectangle.Command>(){

			@Override
			public void changed(ObservableValue<? extends ButtonRectangle.Command> observable, ButtonRectangle.Command oldValue, ButtonRectangle.Command newValue) {
				if(newValue == ButtonRectangle.Command.BUTTON_RELEASED)
				{
					getCommandProperty().set(Command.RESET_COMMAND);
					getCommandProperty().set(Command.NEXT_SENSOR_VALUE);
				}
			}
			
		});
		
		
		
		button_up = new ButtonRectangle(PositionGradient.FROM_DOWN_TO_UP, 0.23555555563333336, 0.0, 0.5288888887222222, 0.07999999995, 0.022222222222222223);
		button_up.setFill(baseColor.get());
		button_up.getCommandProperty().addListener(new ChangeListener<ButtonRectangle.Command>(){

			@Override
			public void changed(ObservableValue<? extends ButtonRectangle.Command> observable, ButtonRectangle.Command oldValue, ButtonRectangle.Command newValue) {
				if(newValue == ButtonRectangle.Command.BUTTON_RELEASED)
				{
					getCommandProperty().set(Command.RESET_COMMAND);
					getCommandProperty().set(Command.PREVIOUS_SENSOR_VALUE);
					
				}
				
			}
			
		});
	
		this.getChildren().addAll(base_background_component, topCanvas, textCanvas, bottomCanvas, button_down, button_up);
	
	}

	protected void registerListener() 
	{
		widthProperty().addListener(observable -> resize());
		heightProperty().addListener(observable -> resize());
		
		valueProperty.set(new Value_Color_Component(""));
		valueProperty.addListener(new ChangeListener<Value_Color_Component>(){

			@Override
			public void changed(ObservableValue<? extends Value_Color_Component> observable, Value_Color_Component oldValue, Value_Color_Component newValue) {
				resize();
				
			}
			
		});
		
		valueTopProperty.set(new Value_Color_Component(""));
		valueTopProperty.addListener(new ChangeListener<Value_Color_Component>(){

			@Override
			public void changed(ObservableValue<? extends Value_Color_Component> observable, Value_Color_Component oldValue, Value_Color_Component newValue) {
				resize();
				
			}
			
		});
		
		valueBottomProperty.set(new Value_Color_Component(""));
		valueBottomProperty.addListener(new ChangeListener<Value_Color_Component>(){

			@Override
			public void changed(ObservableValue<? extends Value_Color_Component> observable, Value_Color_Component oldValue, Value_Color_Component newValue) {
				resize();
				
			}
			
		});
	}
	
	protected void reColoredComponent() {
		base_background_component.setStroke(baseColor.get());
		button_down.setBaseColor(baseColor.get());
		button_up.setBaseColor(baseColor.get());
		this.resize();
		
	}

	protected void resize() {
		width_component = getWidth();
		height_component = getHeight();
		
		base_background_component.setWidth(width_component);
		base_background_component.setHeight(height_component);
		base_background_component.setArcWidth(width_component * 0.044444444444444446);
		base_background_component.setArcHeight(width_component * 0.044444444444444446);
		base_background_component.setStrokeWidth(width_component * 0.022222222222222223);
		
		LinearGradient lg = new LinearGradient(width_component * 0.8452471254888888, 
				height_component * 1.03818700626, 
				width_component * 0.15475287451111114,
				height_component *  -0.03818700626, 
				false, CycleMethod.NO_CYCLE, stopMap.get(StopIndizes.BASE_BACKGROUND_LINEAR_GRADIENT));
		base_background_component.setFill(lg);
	
		
		double previous_w = textCanvas.getWidth();
		double previous_h = textCanvas.getHeight();
		
		double newX = width_component * 0.03911901127777778;
		double newY = height_component * 0.33381546094000003;
		
		double newWidth = width_component *   0.9386587666222223;
		double newHeight = height_component * 0.32;
	
		textCanvas.setWidth(newWidth);
		textCanvas.setHeight(newHeight);
		textCanvas.relocate(newX, newY);
		
		refreshTextContent(previous_w, previous_h, textCanvas, valueProperty);
		
		
		previous_w = topCanvas.getWidth();
		previous_h = topCanvas.getHeight();
		
		newX = width_component * 0.03911901127777778;
		newY = height_component * 0.09176343978;
		newWidth = width_component *  0.9309931590666663;
		newHeight = height_component * 0.22;
		
		topCanvas.setWidth(newWidth);
		topCanvas.setHeight(newHeight);
		topCanvas.relocate(newX, newY);	
		
		
		refreshTextContent(previous_w, previous_h, topCanvas, valueTopProperty);
		
		
		previous_w = bottomCanvas.getWidth();
		previous_h = bottomCanvas.getHeight();
		
		newX = width_component *  0.04295181506666667;
		newY = height_component * 0.67383772251;
		newWidth = width_component *  0.9309931590666666;
		newHeight = height_component * 0.22;
		
		bottomCanvas.setWidth(newWidth);
		bottomCanvas.setHeight(newHeight);
		bottomCanvas.relocate(newX, newY);	
		
		
		refreshTextContent(previous_w, previous_h, bottomCanvas, valueBottomProperty);
		
		button_down.refreshSize(width_component, height_component);
		button_up.refreshSize(width_component, height_component);
		
	}
	
	/**
	 * draw the content of the passed canvas
	 * @param previous_w
	 * @param previous_h
	 * @param canvas
	 * @param simpleStringValueProperty
	 */
	private void refreshTextContent(double previous_w, double previous_h, Canvas canvas, SimpleObjectProperty<Value_Color_Component> simpleObjectProperty) 
	{
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, previous_w, previous_h);
		
		Font fontLcd = Font.font("Verdana", 10);
		
		Bounds maxTextAbmasseLCD = UIToolBox.getMaxTextWidth(fontLcd, simpleObjectProperty.get().getValue());
		double tempSizeLCD;
		if(maxTextAbmasseLCD.getWidth() < canvas.getWidth()*0.97  && maxTextAbmasseLCD.getHeight() < canvas.getHeight() *0.97)
		{
			tempSizeLCD = UIToolBox.getGreaterFont(fontLcd.getSize()+1, canvas.getWidth()*0.97, canvas.getHeight()*0.97, simpleObjectProperty.get().getValue(), 0.01, fontLcd);
		}
		else
		{
			tempSizeLCD = UIToolBox.getLesserFont(fontLcd.getSize(), canvas.getWidth()*0.97, canvas.getHeight()*0.97, simpleObjectProperty.get().getValue(),  0.01, fontLcd);
		}
		
		fontLcd = Font.font(fontLcd.getName(), tempSizeLCD);
		
		if(simpleObjectProperty.get().getColor() == null)
			gc.setFill(baseColor.get());
		else
			gc.setFill(simpleObjectProperty.get().getColor());
		
		
		gc.setFont(fontLcd);
		Text valueText = new Text();
		
		
		valueText.setText(simpleObjectProperty.get().getValue());
		valueText.setFont(fontLcd);
		
		double masseinheitXLCD = canvas.getWidth() * 0.97 - (valueText.getLayoutBounds().getWidth());
		gc.fillText(valueText.getText(), masseinheitXLCD /2d,   valueText.getLayoutBounds().getHeight() );
		
		
	}
	
	public SimpleObjectProperty<Value_Color_Component> getValueProperty() {
		return valueProperty;
	}
	
	public SimpleObjectProperty<Value_Color_Component> getTopValueProperty(){
		return valueTopProperty;
	}

	public SimpleObjectProperty<Value_Color_Component> getBottomValueProperty(){
		return valueBottomProperty;
	}

}
