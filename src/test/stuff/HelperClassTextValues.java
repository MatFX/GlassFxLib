package test.stuff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.scene.paint.Color;



/**
 * test data to visulize
 */
public class HelperClassTextValues
{

	public static final int TEMPERATURE = 0;
	public static final int HUMIDITY = 1;
	public static final int BRIGHTNESS = 2;
	
	
	
	private HashMap<Integer, List<SensorValue>> sensorMap = new HashMap<Integer, List<SensorValue>>();

	private int currentSensorToShow = 0;

	private List<SensorValue> sensorList;
	
	public HelperClassTextValues()
	{
		sensorList = new ArrayList<SensorValue>();
		
		SensorValue tempValue = new SensorValue(23.5, -40, 60, "°C", "hi_temp", "Temperatur:");
		tempValue.setPreferedColor(Color.BLUE);
		sensorList.add(tempValue);
		sensorMap.put(TEMPERATURE, sensorList);
		
		sensorList = new ArrayList<SensorValue>();
		sensorList.add(new SensorValue(64.0, 0, 100, "%", "", "Feuchtigkeit:"));
		sensorMap.put(HUMIDITY, sensorList);
		
		sensorList = new ArrayList<SensorValue>();
		sensorList.add(new SensorValue(7500, 0, 100000, "Lux", "hi_brightness", "Helligkeit:"));
		sensorMap.put(BRIGHTNESS, sensorList);
		
	}
	
	

	public int getCurrentSensorToShow() {
		return currentSensorToShow;
	}

	public void addCurrentSensorToShow() {
		currentSensorToShow ++;
		
	}

	public void setCurrentSensorToShow(int val) {
		currentSensorToShow = val;
	}

	public int getMapSize() 
	{
		return sensorMap.size();
	}

	public SensorValue getBottomValue()
	{
		int bottomIndex = currentSensorToShow + 1;
		if(bottomIndex > 2)
			bottomIndex = 0;
		
		return sensorMap.get(bottomIndex).get(0);
	}
	
	public SensorValue getTopValue()
	{
		int topIndex = currentSensorToShow - 1;
		if(topIndex < 0)
			topIndex = 2;
		
		return sensorMap.get(topIndex).get(0);
	}
	
	
	public SensorValue getSelectedSensorValue() 
	{
		return sensorMap.get(currentSensorToShow).get(0);
	}

	public void subCurrentSensorToShow() {
		currentSensorToShow --;
		
	}
	
}
