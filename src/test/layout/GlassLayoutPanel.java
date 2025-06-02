package test.layout;

import java.util.Random;

import eu.matfx.component.layout.ScrollJFXMasonryPane;
import eu.matfx.component.sensor.MixedValueComponent;
import eu.matfx.component.sensor.MoreValueComponent;
import eu.matfx.component.sensor.SingleValueImageComponent;
import eu.matfx.tools.Command;
import eu.matfx.tools.Image_Color_Component;
import eu.matfx.tools.Value_Color_Component;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import test.stuff.HelperClassMixedValues;
import test.stuff.HelperClassTextValues;
import test.stuff.SensorValue;

public class GlassLayoutPanel extends Application
{
	
	private ScrollJFXMasonryPane masonryPane = new ScrollJFXMasonryPane();
	
	private SingleValueImageComponent singleValueImageComponent = new SingleValueImageComponent();
	
	private HelperClassTextValues helperClassSingleValueImage = new HelperClassTextValues();
	
	private MoreValueComponent moreValueComponent = new MoreValueComponent();
	
	private HelperClassTextValues helperClassMoreValueComponent = new HelperClassTextValues();
	
	private MixedValueComponent mixedVlaueComponent = new MixedValueComponent();
	
	private HelperClassMixedValues helperClassMixedValue = new HelperClassMixedValues();

	
	private void initUI() {
		
		singleValueImageComponent.setPrefWidth(50);
		singleValueImageComponent.setPrefHeight(80);
		singleValueImageComponent.getCommandProperty().addListener(new ChangeListener<Command>()
	        {

				@Override
				public void changed(ObservableValue<? extends Command> observable, Command oldValue, Command newValue) {
					
					switch(newValue)
					{
						case NEXT_SENSOR_VALUE:
							nextSingleValue();
							
							break;
						case PREVIOUS_SENSOR_VALUE:
							previousSingleValue();
							
							break;
						case AUTO_CHANGE:
							break;
					}
					
					
				}
	        	
	        });
		masonryPane.getJFXMasonryPane().getChildren().add(singleValueImageComponent);
		
		
	    moreValueComponent.setPrefWidth(120);
        moreValueComponent.setPrefHeight(140);
		helperClassMoreValueComponent.setCurrentSensorToShow(0);
		drawMoreValuesComponent();
		moreValueComponent.getCommandProperty().addListener(new ChangeListener<Command>()
        {

			@Override
			public void changed(ObservableValue<? extends Command> observable, Command oldValue, Command newValue) {
				
				switch(newValue)
				{
					case NEXT_SENSOR_VALUE:
						nextMoreSensorValue();
						
						break;
					case PREVIOUS_SENSOR_VALUE:
						previousMoreSensorValue();
						
						break;
					case AUTO_CHANGE:
						/*
						System.out.println("sensorPanel " + sensorPanel.getAutoProperty().get());
						if(sensorPanel.getAutoProperty().get())
						{
							//wechsle automatisch die Einstellung immer nach vorwärts
							startAutoThread();
							
						}
						else
						{
							//schieße Thread ab.
							stopAutoThread();
						}
						*/
						break;
				}
				
				
			}
        	
        });
		masonryPane.getJFXMasonryPane().getChildren().add(moreValueComponent);
		
		
		mixedVlaueComponent.setPrefWidth(120);
		mixedVlaueComponent.setPrefHeight(140);
		
		mixedVlaueComponent.getCommandProperty().addListener(new ChangeListener<Command>()
        {

			@Override
			public void changed(ObservableValue<? extends Command> observable, Command oldValue, Command newValue) {
				
				switch(newValue)
				{
					case NEXT_SENSOR_VALUE:
						nextMixedSensorValue();
						
						break;
					case PREVIOUS_SENSOR_VALUE:
						previousMixedSensorValue();
						
						break;
					case AUTO_CHANGE:
						/*
						System.out.println("sensorPanel " + sensorPanel.getAutoProperty().get());
						if(sensorPanel.getAutoProperty().get())
						{
							//wechsle automatisch die Einstellung immer nach vorwärts
							startAutoThread();
							
						}
						else
						{
							//schieße Thread ab.
							stopAutoThread();
						}
						*/
						break;
				}
				
				
			}
        	
        });
		
		helperClassMixedValue.setCurrentSensorToShow(0);
		drawTheValues();
	
		masonryPane.getJFXMasonryPane().getChildren().add(mixedVlaueComponent);
		
	}
	
	protected void previousMoreSensorValue() 
	{
		
		int nextValueForMiddleCanvas = helperClassMoreValueComponent.getCurrentSensorToShow() - 1;
		if(nextValueForMiddleCanvas < 0)
			nextValueForMiddleCanvas = 2;
		helperClassMoreValueComponent.setCurrentSensorToShow(nextValueForMiddleCanvas);
		drawMoreValuesComponent();
		
		
	}
	
	/**
	 * Show nextValue in the middle
	 */
	protected void nextMoreSensorValue() 
	{
		int nextValueForMiddleCanvas = helperClassMoreValueComponent.getCurrentSensorToShow() + 1;
		if(nextValueForMiddleCanvas > 2)
			nextValueForMiddleCanvas = 0;
		helperClassMoreValueComponent.setCurrentSensorToShow(nextValueForMiddleCanvas);
		drawMoreValuesComponent();
	}
	
	private void drawMoreValuesComponent() {
		moreValueComponent.getValueProperty().set(new Value_Color_Component(""));
		moreValueComponent.getTopValueProperty().set(new Value_Color_Component(""));
		moreValueComponent.getBottomValueProperty().set(new Value_Color_Component(""));
		
		
		
		Value_Color_Component valueComponent = new Value_Color_Component(helperClassMoreValueComponent.getSelectedSensorValue().getCurrentValue() + "" + helperClassMoreValueComponent.getSelectedSensorValue().getMeasurementUnit());
		valueComponent.setColor(helperClassMoreValueComponent.getSelectedSensorValue().preferedColor());
		
		Value_Color_Component topValueComponent = new Value_Color_Component(helperClassMoreValueComponent.getTopValue().getCurrentValue() + "" + helperClassMoreValueComponent.getTopValue().getMeasurementUnit());
		topValueComponent.setColor(helperClassMoreValueComponent.getTopValue().preferedColor());
		
		Value_Color_Component bottomValueComponent = new Value_Color_Component(helperClassMoreValueComponent.getBottomValue().getCurrentValue() + "" + helperClassMoreValueComponent.getBottomValue().getMeasurementUnit());
		bottomValueComponent.setColor(helperClassMoreValueComponent.getBottomValue().preferedColor());
		
		
		moreValueComponent.getValueProperty().set(valueComponent);
		moreValueComponent.getTopValueProperty().set(topValueComponent);
		moreValueComponent.getBottomValueProperty().set(bottomValueComponent);
		
	}


	public static void main(String[] args) {
        Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage)
	{
		
		
		BorderPane mainContent = new BorderPane();
		mainContent.setPadding(new Insets(15 , 15, 15, 15));
		mainContent.setCenter(masonryPane);
		
		initUI();
		
		
		
        Scene scene = new Scene(mainContent, 800, 600);
		 
		
		primaryStage.setTitle("Masonry glass panel");
	    primaryStage.setScene(scene);
	    primaryStage.show();
	    
	    previousSingleValue();
		
	}
	
	protected void previousSingleValue() {
    	helperClassSingleValueImage.subCurrentSensorToShow();
		
		if(helperClassSingleValueImage.getCurrentSensorToShow() < 0)
			helperClassSingleValueImage.setCurrentSensorToShow(helperClassSingleValueImage.getMapSize()-1);
		
		SensorValue sensorValue = helperClassSingleValueImage.getSelectedSensorValue(); 
		
		Random random = new Random();
		double newCurrentValue = random.nextDouble()*(sensorValue.getBis()-sensorValue.getVon());
		double roundedValue = Math.round(newCurrentValue*100)/100;
		sensorValue.setCurrentValue(roundedValue);
		singleValueImageComponent.getImageProperty().set(sensorValue.getImageBezeichnung());
		singleValueImageComponent.getValueProperty().set(sensorValue.getCurrentValue() + "" + sensorValue.getMeasurementUnit());
		
		
	}

	protected void nextSingleValue() {

		helperClassSingleValueImage.addCurrentSensorToShow();
		if(helperClassSingleValueImage.getCurrentSensorToShow() >= helperClassSingleValueImage.getMapSize())
			helperClassSingleValueImage.setCurrentSensorToShow(0);
		
		SensorValue sensorValue = helperClassSingleValueImage.getSelectedSensorValue(); 
		
		Random random = new Random();
		double newCurrentValue = random.nextDouble()*(sensorValue.getBis()-sensorValue.getVon());
		double roundedValue = Math.round(newCurrentValue*100)/100;
		sensorValue.setCurrentValue(roundedValue);
		singleValueImageComponent.getImageProperty().set(sensorValue.getImageBezeichnung());
		singleValueImageComponent.getValueProperty().set(sensorValue.getCurrentValue() + "" + sensorValue.getMeasurementUnit());
		
		
	}
	
	
	/**
	 * roll to previous value for the middle canvas
	 */
	protected void previousMixedSensorValue() 
	{
		
		int nextValueForMiddleCanvas = helperClassMixedValue.getCurrentSensorToShow() - 1;
		if(nextValueForMiddleCanvas < 0)
			nextValueForMiddleCanvas = 2;
		helperClassMixedValue.setCurrentSensorToShow(nextValueForMiddleCanvas);
		drawTheValues();
		
		
	}

	private void drawTheValues() {
		
		//Temp und BRIGHTNESS sind values
		
		//motion ist bild
		
		Value_Color_Component valueTempComponet = new Value_Color_Component(helperClassMixedValue.getSensorValue(HelperClassMixedValues.TEMPERATURE).getCurrentValue() + "" + helperClassMixedValue.getSensorValue(HelperClassMixedValues.TEMPERATURE).getMeasurementUnit());
		valueTempComponet.setColor(helperClassMixedValue.getSensorValue(HelperClassMixedValues.TEMPERATURE).preferedColor());
	
		Value_Color_Component valueBrightnessComponet = new Value_Color_Component(helperClassMixedValue.getSensorValue(HelperClassMixedValues.BRIGHTNESS).getCurrentValue() + "" + helperClassMixedValue.getSensorValue(HelperClassMixedValues.BRIGHTNESS).getMeasurementUnit());
		valueBrightnessComponet.setColor(helperClassMixedValue.getSensorValue(HelperClassMixedValues.BRIGHTNESS).preferedColor());
		
		
		Image_Color_Component valueMotionComponent = null;
		
		if(helperClassMixedValue.getSensorValue(HelperClassMixedValues.MOTION).getCurrentValue() == 1D)
			valueMotionComponent = new Image_Color_Component("hi_motion.png");
		else
			valueMotionComponent = new Image_Color_Component("hi_no_motion.png");
		
		valueMotionComponent.setColor(helperClassMixedValue.getSensorValue(HelperClassMixedValues.MOTION).preferedColor());
		
		//which component will be draw in the middle?
		switch(helperClassMixedValue.getCurrentSensorToShow())
		{
			case HelperClassMixedValues.TEMPERATURE:
				
				mixedVlaueComponent.getValueProperty().set(valueTempComponet);
				mixedVlaueComponent.getTopValueProperty().set(valueBrightnessComponet);
				mixedVlaueComponent.getBottomValueProperty().set(valueMotionComponent);
				break;
			
			case HelperClassMixedValues.BRIGHTNESS:
				mixedVlaueComponent.getValueProperty().set(valueBrightnessComponet);
				mixedVlaueComponent.getTopValueProperty().set(valueMotionComponent);
				mixedVlaueComponent.getBottomValueProperty().set(valueTempComponet);
				break;
			case HelperClassMixedValues.MOTION:
				
				mixedVlaueComponent.getValueProperty().set(valueMotionComponent);
				mixedVlaueComponent.getTopValueProperty().set(valueTempComponet);
				mixedVlaueComponent.getBottomValueProperty().set(valueBrightnessComponet);
				break;
				
			
		}
	}

	/**
	 * Show nextValue in the middle
	 */
	protected void nextMixedSensorValue() 
	{
		int nextValueForMiddleCanvas = helperClassMixedValue.getCurrentSensorToShow() + 1;
		if(nextValueForMiddleCanvas > 2)
			nextValueForMiddleCanvas = 0;
		helperClassMixedValue.setCurrentSensorToShow(nextValueForMiddleCanvas);
		drawTheValues();
	}
	
	
	
	


}
