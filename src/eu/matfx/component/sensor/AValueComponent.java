package eu.matfx.component.sensor;

import java.util.HashMap;

import eu.matfx.tools.Command;
import eu.matfx.tools.UIToolBox;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

public abstract class AValueComponent extends Region
{
	

	//Orginal color Color.web("#5abaa0")
	/**
	 * Changeable color of the component.
	 */
	protected SimpleObjectProperty<Color> baseColor = new SimpleObjectProperty<Color>();
	
	protected Rectangle base_background_component;
	
	private int MIN_BLUR_RADIUS = 0;
	
	private int MAX_BLUR_RADIUS = 10;
	
	private int MIN_GRADIENT_ALPHA_CHANNEL = 0;
	
	private int MAX_GRADIENT_ALPHA_CHANNEL = 0x80;
	

	/**
	 * Changeable the alpha channel from the linear gradient of the background.
	 */
	private SimpleIntegerProperty alphaChannelProperty  = new SimpleIntegerProperty();
	
	/**
	 * Changeable blur effect of the border.
	 */
	private SimpleIntegerProperty blurRadiusProperty = new SimpleIntegerProperty();
	

	/**
	 * Colors for the linear gradient
	 */
	protected HashMap<StopIndizes, Stop[]> stopMap = new HashMap<StopIndizes, Stop[]>();
	
	
	/**
	 * Connect from outside when a response to the buttons is required.
	 */
	private SimpleObjectProperty<Command> commandProperty = new SimpleObjectProperty<Command>();
	
	public enum StopIndizes
	{
		BASE_BACKGROUND_LINEAR_GRADIENT;
	}
	
	
	
	protected AValueComponent()
	{
		//init the color
		baseColor.set(Color.web("#5abaa0"));
		baseColor.addListener(new ChangeListener<Color>(){

			@Override
			public void changed(ObservableValue<? extends Color> observable, Color oldValue, Color newValue) {
				reColoredComponent();
			}
			
		});
		

		//stops for the gradient overlay 
		Stop[] stopArray = new Stop[]{
				new Stop(0, Color.web("#ffffff00")),
				new Stop(1, Color.web("#ffffff33"))
			};
		stopMap.put(StopIndizes.BASE_BACKGROUND_LINEAR_GRADIENT, stopArray);
		alphaChannelProperty.addListener(new ChangeListener<Number>(){

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if(newValue != null)
				{
					
					double alphaValueAsDouble = 0;
					if(newValue.intValue() > 0)
						alphaValueAsDouble = newValue.intValue() / 255D;
					
					Stop endStop = stopMap.get(StopIndizes.BASE_BACKGROUND_LINEAR_GRADIENT)[1];
					Color colorValue = endStop.getColor();
					String webColorString = UIToolBox.getWebColorString(colorValue.getRed(), colorValue.getGreen(), colorValue.getBlue(), alphaValueAsDouble);
				
					stopMap.get(StopIndizes.BASE_BACKGROUND_LINEAR_GRADIENT)[1] = new Stop(1, Color.web(webColorString));
					
					//ignore if the init call
					if(base_background_component != null)
						resize();
					
				
				}
				
			}
			
		});
		//init the alphachannel
		alphaChannelProperty.set(0x33);
		
		base_background_component = new Rectangle();
		base_background_component.setStroke(baseColor.get());
		
		//property to react when the blur is changed
		blurRadiusProperty.addListener(new ChangeListener<Number>(){

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) 
			{
				if(newValue != null)
				{
					if(newValue.intValue() < MIN_BLUR_RADIUS)
						newValue = new Integer(MIN_BLUR_RADIUS);
					else if(newValue.intValue()  > MAX_BLUR_RADIUS)
						newValue = new Integer(MAX_BLUR_RADIUS);
					
					
					GaussianBlur g = new GaussianBlur();  
			        g.setRadius(newValue.intValue());  
			        base_background_component.setEffect(g);
				}
			
				
			}
    		
    	});
    	//initial blur
		blurRadiusProperty.set(4);
	}

	protected abstract void initGraphics();
	
	protected abstract void registerListener();
	
	protected abstract void reColoredComponent();
	
	protected abstract void resize(); 
	
	
	public int getMinBlurRadiusValue() {
		return MIN_BLUR_RADIUS;
	}

	public int getMaxBlurRadiusValue() {
		return MAX_BLUR_RADIUS;
	}

	public double getMinGradientAlphaChannel() {
		return MIN_GRADIENT_ALPHA_CHANNEL;
	}

	public double getMaxGradientAlphaChannel() {
		return MAX_GRADIENT_ALPHA_CHANNEL;
	}
	

	/**
	 * Property to change the blur of the frame from outside the component
	 * @return
	 */
	public SimpleIntegerProperty getBlurRadiusProperty()
	{
		return blurRadiusProperty;
	}

	/**
	 *  Property to change the alpha channel of the base backround (stop values from the linear gradient)
	 * @return
	 */
	public SimpleIntegerProperty getAlphaChannelProperty()
	{
		return alphaChannelProperty;
	}
	

	public Color getBaseColor() 
	{
		return baseColor.get();
	}
	

	public void setBaseColor(Color colorSelected) 
	{
		this.baseColor.set(colorSelected);
	}
	
	

	/**
	 * for listing the commands
	 * @return
	 */
	public SimpleObjectProperty<Command> getCommandProperty() 
	{
		return this.commandProperty;
	}
	





}
