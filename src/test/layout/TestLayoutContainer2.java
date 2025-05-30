package test.layout;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import eu.matfx.component.layout.ScrollJFXMasonryPane;
import eu.matfx.component.sensor.MixedValueComponent;
import javafx.application.Application;
import javafx.scene.Scene;
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
		
		sensorenMap = HelperClassBuildMap.getBuildedMap();
	
		Random rand = new Random();
		for(Entry<String, List<SensorValue>> entry : sensorenMap.entrySet())
		{
			MixedValueComponent sensorPanel = new MixedValueComponent();
			int prefWidth = rand.nextInt(71) + 80;
			int prefHeight = rand.nextInt(70) + 100;
			
			sensorPanel.setPrefWidth(prefWidth);
		    sensorPanel.setPrefHeight(prefHeight);
		    sensorPanel.setMaxWidth(prefWidth);
		    sensorPanel.setMinHeight(prefHeight);
	        masonryPane.getJFXMasonryPane().getChildren().add(sensorPanel);
		}
		
        Scene scene = new Scene(masonryPane, 800, 600);
		 
		
		primaryStage.setTitle("Sensor Panel");
	    primaryStage.setScene(scene);
	    primaryStage.show();
		
	}

}
