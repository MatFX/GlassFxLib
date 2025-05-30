package test.stuff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.scene.paint.Color;

public class HelperClassBuildMap 
{
	/**
	 * Key ist die ID als String
	 * @return
	 */
	public static HashMap<String, List<SensorValue>> getBuildedMap() 
	{
		HashMap<String, List<SensorValue>> returnMap = new HashMap<String, List<SensorValue>>();
		
		
		List<SensorValue> tempList = new ArrayList<SensorValue>();
		SensorValue tempValue = new SensorValue(23.5, -40, 60, "°C", "hi_temp", "Temperatur:");
		tempValue.setPreferedColor(Color.BLUE);
		tempList.add(tempValue);
		tempList.add(new SensorValue(1.0, 0, 1, "", "", "Bewegung:"));
		tempList.add(new SensorValue(7500, 0, 100000, "Lux", "hi_brightness", "Helligkeit:"));
		
		returnMap.put("4711", tempList);
		
		tempList = new ArrayList<SensorValue>();
		
		SensorValue newTempValue = new SensorValue(23.5, -40, 60, "°C", "hi_temp", "Temperatur:");
		newTempValue.setPreferedColor(Color.BLUE);
		tempList.add(newTempValue);
		tempList.add(new SensorValue(64.0, 0, 100, "%", "", "Feuchtigkeit:"));
		tempList.add(new SensorValue(7500, 0, 100000, "Lux", "hi_brightness", "Helligkeit:"));
		returnMap.put("4712", tempList);
		
		
		
		tempList = new ArrayList<SensorValue>();
		
		SensorValue thirdTempValue = new SensorValue(20.5, -40, 60, "°C", "hi_temp", "Temperatur:");
		thirdTempValue.setPreferedColor(Color.BLUE);
		tempList.add(thirdTempValue);
		
		returnMap.put("4713", tempList);
		
		
		tempList = new ArrayList<SensorValue>();
		SensorValue fourthTempValue = new SensorValue(20.5, -40, 60, "°C", "hi_temp", "Temperatur:");
		fourthTempValue.setPreferedColor(Color.BLUE);
		tempList.add(fourthTempValue);
		returnMap.put("4714", tempList);
		
		tempList = new ArrayList<SensorValue>();
		SensorValue fifthTempValue = new SensorValue(20.5, -40, 60, "°C", "hi_temp", "Temperatur:");
		fifthTempValue.setPreferedColor(Color.BLUE);
		tempList.add(fifthTempValue);
		returnMap.put("4715", tempList);
		
		
		
		return returnMap;
	}
	
	
	

}
