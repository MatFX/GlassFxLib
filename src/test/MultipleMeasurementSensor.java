package test;

import eu.matfx.component.sensor.MoreValueComponent;
import eu.matfx.tools.Command;
import eu.matfx.tools.Value_Color_Component;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import test.stuff.HelperClass;

public class MultipleMeasurementSensor extends Application
{
	private HelperClass helperClass = new HelperClass();
	
	/**
	 * more values on the view
	 */
	private MoreValueComponent sensorPanel = new MoreValueComponent();
	
	public static void main(String[] args) {
        Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		BorderPane pane = new BorderPane();
		helperClass.setCurrentSensorToShow(1);
		drawTheValues();
	
		sensorPanel.getCommandProperty().addListener(new ChangeListener<Command>()
	        {

				@Override
				public void changed(ObservableValue<? extends Command> observable, Command oldValue, Command newValue) {
					
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
		
		BorderPane.setMargin(sensorPanel, new Insets(15, 15, 15, 15));
        sensorPanel.setPrefWidth(120);
        sensorPanel.setPrefHeight(140);
        pane.setCenter(sensorPanel);
        
        
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
    
	    
        VBox vBoxControl = new VBox(10);
        vBoxControl.setBackground(new Background(new BackgroundFill(Color.DARKSLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        vBoxControl.setPadding(new Insets(5,5,5,5));
        vBoxControl.getChildren().addAll(labelBaseColor, colorPicker);

        ScrollPane scrollRight = new ScrollPane();
        scrollRight.setContent(vBoxControl); 
        pane.setRight(scrollRight);
        
        
//        Label labelTransparenceBorder = new Label("Transparence border: ");
//        labelTransparenceBorder.setTextFill(Color.web("#FFFFFF80"));
        
        HBox checkButtonBox = new HBox(5);
        
        
        Label labelButtonDisable = new Label("Disable Buttons:");
        labelButtonDisable.setTextFill(Color.web("#FFFFFF80"));
        
        CheckBox checkBoxDisableButton = new CheckBox();
        checkBoxDisableButton.setSelected(false);
        checkBoxDisableButton.setOnAction(new EventHandler<ActionEvent>() 
        {

			@Override
			public void handle(ActionEvent arg0) {
				
				sensorPanel.getButton_Down_DisableProperty().set(checkBoxDisableButton.isSelected());
				sensorPanel.getButton_Up_DisableProperty().set(checkBoxDisableButton.isSelected());
			}
        	
        });
        checkButtonBox.getChildren().addAll(labelButtonDisable, checkBoxDisableButton);
        vBoxControl.getChildren().addAll(checkButtonBox);
        
        HBox checkHBox = new HBox(5);
      // checkHBox.setPadding(new Insets(5,5,5,5));
        
        Label checkLabel = new Label("Transparence border:");
        checkLabel.setTextFill(Color.web("#FFFFFF80"));
        
        CheckBox transparenceCheckBox = new CheckBox();
        transparenceCheckBox.setOnAction(new EventHandler<ActionEvent>()
        {

			@Override
			public void handle(ActionEvent arg0) 
			{
				if(transparenceCheckBox.isSelected())
				{
					sensorPanel.getTransparenceCheckBoxProperty().set(true);
				}
				else
				{
					sensorPanel.getTransparenceCheckBoxProperty().set(false);
				}
			}
        	
        });
        checkHBox.getChildren().addAll(checkLabel, transparenceCheckBox);
        
        
        
        vBoxControl.getChildren().addAll(checkHBox);
        
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
        
        
        VBox vBoxBackgroundControl = new VBox(10);
        vBoxBackgroundControl.setPadding(new Insets(5,5,5,5));
        
         
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
       
        primaryStage.setTitle("Sensor Panel");
        primaryStage.setScene(scene);
        primaryStage.show();
		
	}

	/**
	 * roll to previous value for the middle canvas
	 */
	protected void previousSensorValue() 
	{
		
		int nextValueForMiddleCanvas = helperClass.getCurrentSensorToShow() - 1;
		if(nextValueForMiddleCanvas < 0)
			nextValueForMiddleCanvas = 2;
		helperClass.setCurrentSensorToShow(nextValueForMiddleCanvas);
		drawTheValues();
		
		
	}

	private void drawTheValues() {
		sensorPanel.getValueProperty().set(new Value_Color_Component(""));
		sensorPanel.getTopValueProperty().set(new Value_Color_Component(""));
		sensorPanel.getBottomValueProperty().set(new Value_Color_Component(""));
		
		
		
		Value_Color_Component valueComponent = new Value_Color_Component(helperClass.getSelectedSensorValue().getCurrentValue() + "" + helperClass.getSelectedSensorValue().getMeasurementUnit());
		valueComponent.setColor(helperClass.getSelectedSensorValue().preferedColor());
		
		Value_Color_Component topValueComponent = new Value_Color_Component(helperClass.getTopValue().getCurrentValue() + "" + helperClass.getTopValue().getMeasurementUnit());
		topValueComponent.setColor(helperClass.getTopValue().preferedColor());
		
		Value_Color_Component bottomValueComponent = new Value_Color_Component(helperClass.getBottomValue().getCurrentValue() + "" + helperClass.getBottomValue().getMeasurementUnit());
		bottomValueComponent.setColor(helperClass.getBottomValue().preferedColor());
		
		
		sensorPanel.getValueProperty().set(valueComponent);
		sensorPanel.getTopValueProperty().set(topValueComponent);
		sensorPanel.getBottomValueProperty().set(bottomValueComponent);
		
	}

	/**
	 * Show nextValue in the middle
	 */
	protected void nextSensorValue() 
	{
		int nextValueForMiddleCanvas = helperClass.getCurrentSensorToShow() + 1;
		if(nextValueForMiddleCanvas > 2)
			nextValueForMiddleCanvas = 0;
		helperClass.setCurrentSensorToShow(nextValueForMiddleCanvas);
		drawTheValues();
	}

}
