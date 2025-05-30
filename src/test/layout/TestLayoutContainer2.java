package test.layout;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import com.jfoenix.controls.JFXMasonryPane;

import eu.matfx.component.sensor.MixedValueComponent;
import eu.matfx.layout.MasonryPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import test.stuff.HelperClassBuildMap;
import test.stuff.SensorValue;

public class TestLayoutContainer2 extends Application
{
	private HashMap<String, List<SensorValue>> sensorenMap = new HashMap<String, List<SensorValue>>();
	
	private JFXMasonryPane masonryPane = new JFXMasonryPane();
	
	public static void main(String[] args) {
        Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage)
	{
		
		sensorenMap = HelperClassBuildMap.getBuildedMap();
	
		Random rand = new Random();
		for(Entry<String, List<SensorValue>> entry : sensorenMap.entrySet())
		{
			MixedValueComponent sensorPanel = new MixedValueComponent();
			int prefWidth = rand.nextInt(71) + 80;
			int prefHeight = rand.nextInt(70) + 100;
			System.out.println("prefWidth " + prefWidth + " x " + prefHeight);
			sensorPanel.setPrefWidth(prefWidth);
		    sensorPanel.setPrefHeight(prefHeight);
		    
		    sensorPanel.setMaxWidth(prefWidth);
		    sensorPanel.setMinHeight(prefHeight);
	        masonryPane.getChildren().add(sensorPanel);

				
		}
		
		
		
		ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(masonryPane);
        scrollPane.setFitToWidth(true);  // passt die Breite an
        scrollPane.setPannable(true);    // erlaubt Ziehen mit Maus
		
		
		
		
        Scene scene = new Scene(scrollPane, 800, 600);
		 
		
		primaryStage.setTitle("Sensor Panel");
	    primaryStage.setScene(scene);
	    primaryStage.show();
		
	}

}
