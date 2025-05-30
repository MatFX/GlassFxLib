package test.layout;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import eu.matfx.component.sensor.MixedValueComponent;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import test.stuff.HelperClassBuildMap;
import test.stuff.SensorValue;

public class TestLayoutContainer extends Application
{
	private HashMap<String, List<SensorValue>> sensorenMap = new HashMap<String, List<SensorValue>>();
	
	
	public static void main(String[] args) {
        Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage)
	{
		
		sensorenMap = HelperClassBuildMap.getBuildedMap();
		
		
		GridPane gridPane = new GridPane();
		//gridPane.setGridLinesVisible(true);
		
		int columnIndex = 0;
		int rowIndex = 0;
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
		    
			
			gridPane.add(sensorPanel, columnIndex, rowIndex);
			
			columnIndex++;
			if(columnIndex == 3)
			{
				columnIndex = 0;
				rowIndex++;
			}
				
		}
		
		
		//gridPane.add(child, columnIndex, rowIndex);
		
		
		
		
		
		
		
		Scene scene = new Scene(gridPane);
		 
		
		primaryStage.setTitle("Sensor Panel");
	    primaryStage.setScene(scene);
	    primaryStage.show();
		
	}

}
