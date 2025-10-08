package test.layout;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import eu.matfx.component.layout.ScrollJFXMasonryPane;
import eu.matfx.component.sensor.MixedValueComponent;
import eu.matfx.tools.Value_Color_Component;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import test.stuff.HelperClassBuildMap;
import test.stuff.SensorValue;

public class TestLayoutContainer2 extends Application
{
	private HashMap<String, List<SensorValue>> sensorenMap = new HashMap<String, List<SensorValue>>();

	private ScrollJFXMasonryPane masonryPane = new ScrollJFXMasonryPane();
	
	
	public static void main(String[] args) {
        Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage)
	{
		BorderPane borderPane = new BorderPane();
		//borderPane.setStyle("-fx-background-color: #787845");
		
		sensorenMap = HelperClassBuildMap.getBuildedMap();
	
		Random rand = new Random();
		for(Entry<String, List<SensorValue>> entry : sensorenMap.entrySet())
		{
			MixedValueComponent sensorPanel = new MixedValueComponent();
			int prefWidth = rand.nextInt(71) + 80;
			int prefHeight = rand.nextInt(70) + 120;
			
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
						   valueComponent.setColor(Color.web("#669999"));
						sensorPanel.getValueProperty().set(valueComponent);
					}
				}
		    	
		    });
		    masonryPane.getJFXMasonryPane().getChildren().add(sensorPanel);
		}
		
		masonryPane.setFitToWidth(true);
		borderPane.setCenter(masonryPane);
		
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
				MixedValueComponent sensorPanel = new MixedValueComponent();
				int prefWidth = rand.nextInt(71) + 80;
				int prefHeight = rand.nextInt(70) + 100;
				
				sensorPanel.setPrefWidth(prefWidth);
			    sensorPanel.setPrefHeight(prefHeight);
			    sensorPanel.setMaxWidth(prefWidth);
			    sensorPanel.setMinHeight(prefHeight);
			    masonryPane.getJFXMasonryPane().getChildren().add(sensorPanel);
			    //showBoundings( masonryPane.getChildren());
				// Beim nächsten Layout-Zyklus wird die BoundingBox korrekt sein
				Platform.runLater(() -> {
				    Bounds b = sensorPanel.getBoundsInParent();
				    System.out.println("Neue BoundingBox: " + b);
				});
			}
			
		});
		
		
		
		VBox vBoxBackgroundControl = new VBox(10);
	    vBoxBackgroundControl.setPadding(new Insets(5,5,5,5));
	    vBoxBackgroundControl.getChildren().add(button);
	    borderPane.setLeft(vBoxBackgroundControl);
		
		
		
	}

}
