package test.stuff;

import javafx.beans.property.SimpleDoubleProperty;

/**
 * helper class to store and change values
 * @author m.goerlich
 *
 */
public class SensorValue
{
	private double von;
	
	private double bis;
	
	private SimpleDoubleProperty currentValue;
	
	private String measurementUnit;

	/**
	 * ist nur interresant bei Veränderung durch Anwender
	 */
	private double stepping = 0.5;
	
	/**
	 * Zusätzliches Feld für die Bildbezeichnung die auch zur Anzeige kommmt
	 */
	private String imageName = "";
	
	private double[] presetValues = null;
	
	private String description;
	
	public SensorValue(double currentValue, double von, double bis, String measurementUnit, String imageName, double[] presetValues)
	{
		this(currentValue, von, bis, measurementUnit, imageName);
		this.presetValues = presetValues;
	}
	
	
	
	public SensorValue(double currentValue, double von, double bis, String measurementUnit, String imageName)
	{
		this.currentValue = new SimpleDoubleProperty(currentValue);
		this.von = von;
		this.bis = bis;
		this.measurementUnit = measurementUnit;
		this.imageName = imageName;
	}
	
	public SensorValue(double currentValue, double von, double bis, String measurementUnit, String imageName, String description)
	{
		this(currentValue, von, bis, measurementUnit, imageName);
		this.description = description;
	}

	public double getVon() {
		return von;
	}

	public void setVon(double von) {
		this.von = von;
	}

	public double getBis() {
		return bis;
	}

	public void setBis(double bis) {
		this.bis = bis;
	}

	public double getCurrentValue() {
		return currentValue.doubleValue();
	}

	public void setCurrentValue(double currentValue) {
		this.currentValue.set(currentValue);;
	}
	
	/**
	 * to add a changelistener
	 * @return
	 */
	public SimpleDoubleProperty getCurrentValueProperty()
	{
		return this.currentValue;
	}

	public String getMeasurementUnit() {
		return measurementUnit;
	}

	public void setMeasurementUnit(String measurementUnit) {
		this.measurementUnit = measurementUnit;
	}
	
	public String getImageBezeichnung()
	{
		return this.imageName;
	}
	
	public double getStepValue()
	{
		return stepping;
	}
	
	public boolean hasPresetValues()
	{
		if(presetValues != null && presetValues.length > 0)
			return true;
		else
			return false;
	}
	
	public double getPresetValueFrom(int index)
	{
		if(presetValues != null && presetValues.length > 0)
			return presetValues[index];
		else
			return 0;
	}



	public double[] getPresetValues()
	{
		return presetValues;
	}
	
	public String getDescription()
	{
		return description;
	}
}
