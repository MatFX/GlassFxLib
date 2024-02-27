package eu.matfx.component.sensor;

import eu.matfx.component.ButtonRectangle;
import eu.matfx.component.ButtonRectangle.PositionGradient;
import eu.matfx.tools.Command;
import eu.matfx.tools.ImageLoader;
import eu.matfx.tools.UIToolBox;
import javafx.beans.property.SimpleStringProperty;
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
 * Simple sensor component with two buttons to change the view; 
 * @author m.goerlich
 *
 */
public class SingleValueImageComponent extends AValueComponent
{
	
	
	//started dimension from the svg file
	private double width_component = 90;
	private double height_component = 100;
	/**
	 * Main content to visualize the value from the sensor e.g. 
	 */
	private Canvas textCanvas;
	/**
	 * Optional. Display of an image as additional visualization for the sensor value.
	 */
	private Canvas imageCanvas;
	
	/**
	 * ValueProperty as String. To Change the visulisation of the component set here the value.
	 */
	private SimpleStringProperty valueProperty = new SimpleStringProperty();
	
	/**
	 * Property to change the image at the component.
	 */
	private SimpleStringProperty imageFileNameProperty = new SimpleStringProperty();
	
	protected ButtonRectangle button_up, button_down;
	
	public SingleValueImageComponent()
	{
		super();
		this.initGraphics();
		this.registerListener();
	}


	protected void initGraphics() 
	{	
		
		textCanvas = new Canvas();
		imageCanvas = new Canvas();
		
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
	
		this.getChildren().addAll(base_background_component, textCanvas, imageCanvas, button_down, button_up);
		
		imageFileNameProperty.set("");
		valueProperty.set("");
		valueProperty.addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				resize();
				
			}
			
		});
	
	}
	
	protected void registerListener() 
	{
		widthProperty().addListener(observable -> resize());
		heightProperty().addListener(observable -> resize());
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
		
		double newX = width_component * 0.23555555562222225;
		double newY = height_component * 0.09619787962;
		
		
		double refresh_w_i = imageCanvas.getWidth();
		double refresh_h_i = imageCanvas.getHeight();
		
	
		double newWidth = width_component * 0.5288888887333334;
		double newHeight = height_component * 0.27541007289999997;
		imageCanvas.setWidth(newWidth);
		imageCanvas.setHeight(newHeight);
		imageCanvas.relocate(newX, newY);
		
		refreshImageContent(refresh_w_i, refresh_h_i);
	
		
		double refresh_w = textCanvas.getWidth();
		double refresh_h = textCanvas.getHeight();
		
		
		newX = width_component * 0.01666649998888889;
		newY = height_component * 0.38780583212999997;
		newWidth = width_component * 0.966667;
		newHeight = height_component * 0.51865887269;
		
		textCanvas.setWidth(newWidth);
		textCanvas.setHeight(newHeight);
		textCanvas.relocate(newX, newY);
		
		refreshLCDContent(refresh_w, refresh_h);

		button_down.refreshSize(width_component, height_component);
		button_up.refreshSize(width_component, height_component);
	}
	
	private void refreshImageContent(double previous_w, double previous_h)
	{
		GraphicsContext gc = imageCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, previous_w, previous_h);
		
		double x = imageCanvas.getLayoutX();
		double y = imageCanvas.getLayoutY();
		double newCanvasWidth = imageCanvas.getWidth();
		double newCanvasHeight = imageCanvas.getHeight();
	
		System.out.println("x: " + x + " y: " +y + " w: " + newCanvasWidth + " h: "+newCanvasHeight);
		
		double middle_x = newCanvasWidth/2d;
		double middle_y = newCanvasHeight/2d;
		
		Image rawImage = ImageLoader.getImageFromIconFolder(imageFileNameProperty.get());
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
	
		Image scaledImage = ImageLoader.getImageFromIconFolder(imageFileNameProperty.get(), newIconWidth, newIconHeight, false, true);
		
		Image coloredImage = UIToolBox.getColorizedImage(scaledImage, baseColor.get());
		
		gc.drawImage(coloredImage, newXLocation, newYLocation);
		
		
		
	}

	/**
	 * drawing the value of the sensor at the canvas
	 * @param previous_w
	 * @param previous_h
	 */
	private void refreshLCDContent(double previous_w, double previous_h) 
	{
		
		GraphicsContext gc = textCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, previous_w, previous_h);
		
		Font fontLcd = Font.font("Verdana", 10);
		
		Bounds maxTextAbmasseLCD = UIToolBox.getMaxTextWidth(fontLcd, valueProperty.get());
		double tempSizeLCD;
		if(maxTextAbmasseLCD.getWidth() < textCanvas.getWidth()*0.97  && maxTextAbmasseLCD.getHeight() < textCanvas.getHeight() *0.97)
		{
			tempSizeLCD = UIToolBox.getGreaterFont(fontLcd.getSize()+1, textCanvas.getWidth()*0.97, textCanvas.getHeight()*0.97, valueProperty.get(), 0.01, fontLcd);
		}
		else
		{
			tempSizeLCD = UIToolBox.getLesserFont(fontLcd.getSize(), textCanvas.getWidth()*0.97, textCanvas.getHeight()*0.97, valueProperty.get(),  0.01, fontLcd);
		}
		
		fontLcd = Font.font(fontLcd.getName(), tempSizeLCD);
		gc.setFill(baseColor.get());
		gc.setFont(fontLcd);
		Text valueText = new Text();
		valueText.setText(valueProperty.get());
		valueText.setFont(fontLcd);
		double masseinheitXLCD = textCanvas.getWidth()*0.97 - (valueText.getLayoutBounds().getWidth());
		gc.fillText(valueText.getText(), masseinheitXLCD,   valueText.getLayoutBounds().getHeight() );
		
	}


	/**
	 * value property to set text from outer the class
	 * @return
	 */
	public SimpleStringProperty getValueProperty() {
		return valueProperty;
	}
	
	/**
	 * image property to set icon from outer the class.
	 * @return
	 */
	public SimpleStringProperty getImageProperty() 
	{
		return imageFileNameProperty;
	}

	public Color getBaseColor() 
	{
		return baseColor.get();
	}

	public void setBaseColor(Color colorSelected) 
	{
		this.baseColor.set(colorSelected);
	}

	protected void reColoredComponent() {
		base_background_component.setStroke(baseColor.get());
		button_down.setBaseColor(baseColor.get());
		button_up.setBaseColor(baseColor.get());
		this.resize();
		
	}
	
}
