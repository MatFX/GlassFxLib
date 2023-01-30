package test;

import eu.matfx.component.sensor.SingleValueImageComponent;
import eu.matfx.tools.Command;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import test.stuff.HelperClass;
import test.stuff.SensorValue;


/**
 * test application for the first sensor
 * @author m.goerlich
 *
 */
public class SingleMeasurementSensor extends Application {
	
	private SingleValueImageComponent sensorPanel = new SingleValueImageComponent();
	
	private Thread animThread;
	
	private boolean isAnimation;
	
	private Thread autoThread;
	
	private HelperClass helperClass = new HelperClass();
	
	
	public static void main(String[] args) {
        Application.launch(args);
	}
	
	@Override 
    public void start(Stage stage) 
    {
		BorderPane pane = new BorderPane();
		
	
		
		
		  
        sensorPanel.getCommandProperty().addListener(new ChangeListener<Command>()
        {

			@Override
			public void changed(ObservableValue<? extends Command> observable, Command oldValue, Command newValue) {
				
				System.out.println("newValue test " + newValue);
				switch(newValue)
				{
					case NEXT_SENSOR_VALUE:
						nextSensorValue();
						
						break;
					case PREVIOUS_SENSOR_VALUE:
						previousSensorValue();
						
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
        this.previousSensorValue();
		
		  ToggleButton test = new ToggleButton("Start");
		  test.setOnAction(new EventHandler<ActionEvent>(){

				@Override
				public void handle(ActionEvent event) 
				{
					if(test.isSelected())
					{
						
						startAnimation();
						test.setText("End");
					}
					else
					{
						stopAnimation();
						test.setText("Start");
					}
				
				}

				private void stopAnimation() {
					isAnimation = false;
					if(animThread != null)
						animThread.stop();
					
				}

				private void startAnimation() 
				{
					if(animThread != null && animThread.isAlive())
						animThread.stop();
				
					isAnimation = true;
					
					
					Runnable runnable = new Runnable()
					{
						public void run()
						{
							while(isAnimation)
							{
								
								helperClass.addCurrentSensorToShow();
								
								if(helperClass.getCurrentSensorToShow() > 2)
									helperClass.setCurrentSensorToShow(0);
							
								SensorValue sensorValue = helperClass.getSelectedSensorValue();
								sensorPanel.getImageProperty().set(sensorValue.getImageBezeichnung());
								sensorPanel.getValueProperty().set(sensorValue.getCurrentValue() + "" + sensorValue.getMeasurementUnit());
								
								try 
								{
									Thread.sleep(2500);
								}
								catch (InterruptedException e) 
								{
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							
						}
						
					};
					animThread = new Thread(runnable);
					animThread.start();
					
				}
	        	
	        });
     
		BorderPane.setMargin(sensorPanel, new Insets(15, 15, 15, 15));
        sensorPanel.setPrefWidth(120);
        sensorPanel.setPrefHeight(140);
        pane.setCenter(sensorPanel);
         
        
        VBox vBoxControl = new VBox(10);
        vBoxControl.setPadding(new Insets(5,5,5,5));
        vBoxControl.getChildren().add(test);
        
        
        Label labelBaseColor = new Label("Base color:");
        labelBaseColor.setTextFill(Color.web("#FFFFFF80"));
        
        ColorPicker colorPicker = new ColorPicker();
	    colorPicker.setValue(sensorPanel.getBaseColor());
		colorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e)
            {
            	Color colorSelected = colorPicker.getValue();
            	sensorPanel.setBaseColor(colorSelected);
            	
            	
            }
        });
    
        
        
        vBoxControl.getChildren().addAll(labelBaseColor, colorPicker);
        
        
        
        Label label = new Label("Blur radius slider:");
        label.setTextFill(Color.web("#FFFFFF80"));
        
        Slider slider = new Slider(sensorPanel.getMinBlurRadiusValue(), sensorPanel.getMaxBlurRadiusValue(), 1);
        slider.setMajorTickUnit(1);
        slider.setBlockIncrement(1);
        slider.setMinorTickCount(0);
        slider.setValue(sensorPanel.getBlurRadiusProperty().get());
        slider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) 
			{
				if(newValue != null)
				{
					sensorPanel.getBlurRadiusProperty().set((int)Math.round(newValue.doubleValue()));
				}
				
			}});
      
        
        vBoxControl.getChildren().addAll(label, slider);
        
        Label labelTransparenz = new Label("Background transparence:");
        labelTransparenz.setTextFill(Color.web("#FFFFFF80"));
        
        Slider sliderTransparence = new Slider(sensorPanel.getMinGradientAlphaChannel(), sensorPanel.getMaxGradientAlphaChannel(), 1);
        sliderTransparence.setMajorTickUnit(1);
        sliderTransparence.setBlockIncrement(1);
        sliderTransparence.setMinorTickCount(0);
        sliderTransparence.setValue(sensorPanel.getAlphaChannelProperty().get());
        sliderTransparence.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) 
			{
				if(newValue != null)
				{
					sensorPanel.getAlphaChannelProperty().set((int)Math.round(newValue.doubleValue()));
				}
				
			}});
        vBoxControl.getChildren().addAll(labelTransparenz, sliderTransparence);
        
        Separator seperator = new Separator();
        seperator.setOrientation(Orientation.HORIZONTAL);
     
        
    
        
        vBoxControl.setBackground(new Background(new BackgroundFill(Color.DARKSLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        ScrollPane scrollRight = new ScrollPane();
        scrollRight.setContent(vBoxControl); 
        pane.setRight(scrollRight);
        
        VBox vBoxBackgroundControl = new VBox(10);
        vBoxBackgroundControl.setPadding(new Insets(5,5,5,5));
        vBoxBackgroundControl.getChildren().add(test);
        
        
         
        Label labelBackground = new Label("Background color");
        labelBackground.setTextFill(Color.web("#FFFFFF80"));
         
        ColorPicker colorPickerBackground = new ColorPicker();
        colorPickerBackground.setValue(Color.DARKSLATEGRAY);
        colorPickerBackground.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e)
            {
            	Color colorSelected = colorPickerBackground.getValue();
            	pane.setBackground(new Background(new BackgroundFill(colorSelected, CornerRadii.EMPTY, Insets.EMPTY)));
            	vBoxControl.setBackground(new Background(new BackgroundFill(colorSelected, CornerRadii.EMPTY, Insets.EMPTY)));
             	
            }
        });
        vBoxBackgroundControl.getChildren().addAll(labelBackground, colorPickerBackground);
        pane.setLeft(vBoxBackgroundControl);
        
        pane.setBackground(new Background(new BackgroundFill(Color.DARKSLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(pane);
       
        stage.setTitle("Sensor Panel");
        stage.setScene(scene);
        stage.show();
		
       
		
    }
	
	 protected void previousSensorValue() {
	    	helperClass.subCurrentSensorToShow();
			
			if(helperClass.getCurrentSensorToShow() < 0)
				helperClass.setCurrentSensorToShow(helperClass.getMapSize()-1);
			
			SensorValue sensorValue = helperClass.getSelectedSensorValue(); 
			sensorPanel.getImageProperty().set(sensorValue.getImageBezeichnung());
			sensorPanel.getValueProperty().set(sensorValue.getCurrentValue() + "" + sensorValue.getMeasurementUnit());
			
			
		}

		protected void nextSensorValue() {

			helperClass.addCurrentSensorToShow();
			if(helperClass.getCurrentSensorToShow() >= helperClass.getMapSize())
				helperClass.setCurrentSensorToShow(0);
			
			SensorValue sensorValue = helperClass.getSelectedSensorValue(); 
			sensorPanel.getImageProperty().set(sensorValue.getImageBezeichnung());
			sensorPanel.getValueProperty().set(sensorValue.getCurrentValue() + "" + sensorValue.getMeasurementUnit());
			
			
		}
	
	
	
}
