package test.layout;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import eu.matfx.component.sensor.MixedValueComponent;
import eu.matfx.tools.Value_Color_Component;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import test.stuff.HelperClassBuildMap;
import test.stuff.SensorValue;

public class LayoutContainer extends Application
{
	private OwnLayoutPane layoutPane = new OwnLayoutPane();
	
	private final int SENSOR_COUNT_START = 7;
	
	public static void main(String[] args) {
        Application.launch(args);
	}
	
	@Override
	public void start(Stage primaryStage)
	{
		layoutPane.setStyle("-fx-background-color: #784545");
	
		
		BorderPane borderPane = new BorderPane();
		borderPane.setStyle("-fx-background-color: #787845");
		
	
		for(int i = 0; i < SENSOR_COUNT_START; i++)
		{
		    layoutPane.getChildren().add(getBuildedMixedComponent());
		}
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setFitToWidth(true);
		
		scrollPane.setContent(layoutPane);
		
		borderPane.setCenter(scrollPane);
		
		Scene scene = new Scene(borderPane, 800, 600);
		scene.getStylesheets().add("style.css");
		
		primaryStage.setTitle("Sensor Panel");
	    primaryStage.setScene(scene);
	    primaryStage.show();
	    

		Button button = new Button("add UI Component");
		button.setOnAction(new EventHandler<ActionEvent> ()
		{

			@Override
			public void handle(ActionEvent p0) {
				
			    layoutPane.getChildren().add(getBuildedMixedComponent());
				
			}

		});
		
		
		
		VBox vBoxBackgroundControl = new VBox(10);
	    vBoxBackgroundControl.setPadding(new Insets(5,5,5,5));
	    vBoxBackgroundControl.getChildren().add(button);
	    borderPane.setLeft(vBoxBackgroundControl);
		
	}
	

	private Node getBuildedMixedComponent() {
		Random rand = new Random();
		MixedValueComponent sensorPanel = new MixedValueComponent();
		int prefWidth = rand.nextInt(71) + 80;
		int prefHeight = rand.nextInt(70) + 100;
		
		sensorPanel.setPrefWidth(prefWidth);
	    sensorPanel.setPrefHeight(prefHeight);
	    sensorPanel.setMaxWidth(prefWidth);
	    sensorPanel.setMinHeight(prefHeight);
	    sensorPanel.boundsInParentProperty().addListener(new ChangeListener<Bounds>(){

			@Override
			public void changed(ObservableValue<? extends Bounds> p0, Bounds p1, Bounds newBounds) {
				
				if(!newBounds.isEmpty())
				{
					double widthComponent = Math.round(newBounds.getWidth() * 10.0) / 10.0;
					double heightComponent = Math.round(newBounds.getHeight()* 10.0) / 10.0;
					
					DecimalFormat df = new DecimalFormat("0.0");
					Value_Color_Component valueComponent = new Value_Color_Component(df.format(widthComponent) + " x " + df.format(heightComponent));
					   valueComponent.setColor(Color.web("#d6d6c2"));
					sensorPanel.getValueProperty().set(valueComponent);
				}
			}
	    	
	    });
		return sensorPanel;
	}
	

}
