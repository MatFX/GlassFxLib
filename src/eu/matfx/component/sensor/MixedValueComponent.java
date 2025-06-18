package eu.matfx.component.sensor;

import eu.matfx.component.ButtonRectangle;
import eu.matfx.component.ButtonRectangle.PositionGradient;
import eu.matfx.tools.AColor_Component;
import eu.matfx.tools.Command;
import eu.matfx.tools.Image_Color_Component;
import eu.matfx.tools.UIToolBox;
import eu.matfx.tools.Value_Color_Component;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Presentation of text or images as values (maximum three values).
 * @author Matthias
 */
public class MixedValueComponent extends AValueComponent
{
	
	//started dimension from the svg file
	private double width_component = 90;
	private double height_component = 100;
	
	private Canvas topCanvas, bottomCanvas, mainCanvas;
	
	protected ButtonRectangle button_up, button_down;
	
	private SimpleObjectProperty<AColor_Component> valueTopProperty = new SimpleObjectProperty<AColor_Component>();
	
	private SimpleObjectProperty<AColor_Component> valueProperty = new SimpleObjectProperty<AColor_Component>();
	
	private SimpleObjectProperty<AColor_Component> valueBottomProperty = new SimpleObjectProperty<AColor_Component>();
	
	private SimpleBooleanProperty button_up_disable = new SimpleBooleanProperty();
	
	private SimpleBooleanProperty button_down_disable = new SimpleBooleanProperty();
	
	public MixedValueComponent()
	{
		super();
		this.initGraphics();
		this.registerListener();
	}

	@Override
	protected void initGraphics() {

		//TODO gleiche wie bei more Value Component
		topCanvas = new Canvas();
		mainCanvas = new Canvas();
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
		button_down_disable.addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
				if(newValue != null)
				{
					button_down.setDisable(newValue);
					if(newValue.booleanValue())
						button_down.setOpacity(0);
					else
						button_down.setOpacity(1);
				
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
		button_up_disable.addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
			{
				if(newValue != null)
				{
					button_up.setDisable(newValue);
					if(newValue.booleanValue())
						button_up.setOpacity(0);
					else
						button_up.setOpacity(1);
				
				}
				
				
			}
			
		});
		
	
	
		this.getChildren().addAll(base_background_component, topCanvas, mainCanvas, bottomCanvas, button_down, button_up);
		
		
	}

	@Override
	protected void registerListener() {
		widthProperty().addListener(observable -> resize());
		heightProperty().addListener(observable -> resize());
		
		valueProperty.set(new Value_Color_Component(""));
		valueProperty.addListener(new ChangeListener<AColor_Component>(){

			@Override
			public void changed(ObservableValue<? extends AColor_Component> observable, AColor_Component oldValue, AColor_Component newValue) {
				resize();
				
			}
			
		});
		
		valueTopProperty.set(new Value_Color_Component(""));
		valueTopProperty.addListener(new ChangeListener<AColor_Component>(){

			@Override
			public void changed(ObservableValue<? extends AColor_Component> observable, AColor_Component oldValue, AColor_Component newValue) {
				resize();
				
			}
			
		});
		
		valueBottomProperty.set(new Value_Color_Component(""));
		valueBottomProperty.addListener(new ChangeListener<AColor_Component>(){

			@Override
			public void changed(ObservableValue<? extends AColor_Component> observable, AColor_Component oldValue, AColor_Component newValue) {
				resize();
				
			}
			
		});
	}

	@Override
	protected void reColoredComponent() {

		if(getTransparenceCheckBoxProperty().get())
		{
			base_background_component.setStroke(Color.web("#00000000"));
		}
		else
			base_background_component.setStroke(baseColor.get());
		
		button_down.setBaseColor(baseColor.get());
		button_up.setBaseColor(baseColor.get());
		this.resize();
		
	}

	@Override
	protected void resize() {
		
		//TODO vieles gleich wie bei MoreValueComponent
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
		

		double previous_w = mainCanvas.getWidth();
		double previous_h = mainCanvas.getHeight();
		
		double newX = width_component * 0.03911901127777778;
		double newY = height_component * 0.33381546094000003;
		
		double newWidth = width_component *   0.9386587666222223;
		double newHeight = height_component * 0.32;
	

		mainCanvas.setWidth(newWidth);
		mainCanvas.setHeight(newHeight);
		mainCanvas.relocate(newX, newY);
		
		if(valueProperty.get() instanceof Value_Color_Component)
		{
			refreshTextContent(previous_w, previous_h, mainCanvas, valueProperty);
		}
		else
		{
			refreshImageContent(previous_w, previous_h, mainCanvas, (Image_Color_Component) valueProperty.get());
		}
		
		previous_w = topCanvas.getWidth();
		previous_h = topCanvas.getHeight();
		
		newX = width_component * 0.03911901127777778;
		newY = height_component * 0.09176343978;
		newWidth = width_component *  0.9309931590666663;
		newHeight = height_component * 0.22;
		
		topCanvas.setWidth(newWidth);
		topCanvas.setHeight(newHeight);
		topCanvas.relocate(newX, newY);	
		
		
		if(valueTopProperty.get() instanceof Value_Color_Component)
		{
			refreshTextContent(previous_w, previous_h, topCanvas, valueTopProperty);
		}
		else
		{
			refreshImageContent(previous_w, previous_h, topCanvas, (Image_Color_Component) valueTopProperty.get());
		}

		previous_w = bottomCanvas.getWidth();
		previous_h = bottomCanvas.getHeight();
		
		newX = width_component *  0.04295181506666667;
		newY = height_component * 0.67383772251;
		newWidth = width_component *  0.9309931590666666;
		newHeight = height_component * 0.22;
		
		bottomCanvas.setWidth(newWidth);
		bottomCanvas.setHeight(newHeight);
		bottomCanvas.relocate(newX, newY);	
		
		if(valueBottomProperty.get() instanceof Value_Color_Component)
		{
			refreshTextContent(previous_w, previous_h, bottomCanvas, valueBottomProperty);
		}
		else
		{
			refreshImageContent(previous_w, previous_h, bottomCanvas,(Image_Color_Component) valueBottomProperty.get());
		}

		button_down.refreshSize(width_component, height_component);
		button_up.refreshSize(width_component, height_component);
		
		
		
	}
	
	private void refreshImageContent(double previous_w, double previous_h, Canvas canvas, Image_Color_Component imageColorComponent)
	{
		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, previous_w, previous_h);
		
		double x = canvas.getLayoutX();
		double y = canvas.getLayoutY();
		double newCanvasWidth = canvas.getWidth();
		double newCanvasHeight = canvas.getHeight();
		
		
		double middle_x = newCanvasWidth/2d;
		double middle_y = newCanvasHeight/2d;
		
		Image rawImage = imageColorComponent.getImageRaw();
		
		//bei Gleichheit 1
		//Breite größer dann Wert > 1
		//Höhe größer dann Wert < 1
		double ratio = rawImage.getWidth() / rawImage.getHeight();
	
		double newIconWidth = newCanvasWidth * 0.9;
		double newIconHeight = newCanvasHeight * 0.9;
		if(ratio == 1)
		{
			if(newCanvasWidth <= newCanvasHeight)
			{
				newIconHeight = newIconWidth;
			}
			else
			{
				//im anderen Fall ist die Zeichenfläche breiter als höher
				newIconWidth = newIconHeight;
			}
		}
		else if(ratio < 1)
		{
			if(newCanvasWidth <= newCanvasHeight)
			{
				newIconHeight = (newIconWidth / ratio);
			}
			else
			{
				newIconWidth = newIconHeight * ratio;
			}
		}
		else if(ratio > 1)
		{
			if(newCanvasWidth <= newCanvasHeight)
			{
				newIconHeight = (newIconWidth / ratio);
			}
			else
			{
				newIconWidth = newIconHeight * ratio;
			}
		}
		
		//calculate x und y to set the image in the middle of the canvas area		
		double halfedWidth = newIconWidth/2d;
		
		double newXLocation = middle_x - halfedWidth;
		
		double halfedHeight = newIconHeight / 2d;
		
		double newYLocation = middle_y - halfedHeight;
	
		Image scaledImage = imageColorComponent.getScaledImageIcon(newIconWidth, newIconHeight, false, true);
		
		
		
		Image coloredImage = null;
		if(imageColorComponent.getColor() == null)
			coloredImage = UIToolBox.getColorizedImage(scaledImage, baseColor.get());
		else
			coloredImage = UIToolBox.getColorizedImage(scaledImage, imageColorComponent.getColor());
		
		gc.drawImage(coloredImage, newXLocation, newYLocation);
		
	}

	/**
	 * draw the content of the passed canvas
	 * @param previous_w
	 * @param previous_h
	 * @param canvas
	 * @param simpleStringValueProperty
	 */
	private void refreshTextContent(double previous_w, double previous_h, Canvas canvas, SimpleObjectProperty<AColor_Component> simpleObjectProperty) 
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

	public SimpleBooleanProperty getButton_Up_DisableProperty(){
		return button_up_disable;
	}

	public SimpleBooleanProperty getButton_Down_DisableProperty(){
		return button_down_disable;
	}
	
	public SimpleObjectProperty<AColor_Component> getValueProperty() {
		return valueProperty;
	}
	
	public SimpleObjectProperty<AColor_Component> getTopValueProperty(){
		return valueTopProperty;
	}

	public SimpleObjectProperty<AColor_Component> getBottomValueProperty(){
		return valueBottomProperty;
	}

	
}
