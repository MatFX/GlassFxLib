package eu.matfx.tools;

/**
 * Command enums; To interacte with components from outside 
 * @author m.goerlich
 *
 */
public enum Command
{
	/**
	 * switch to the previous value
	 */
	PREVIOUS_SENSOR_VALUE, 
	
	/**
	 * switch to the next value
	 */
	NEXT_SENSOR_VALUE, 
	
	/**
	 * switches the values in the auto mode
	 */
	AUTO_CHANGE,
	

	/**
	 * You need the reset as a "acknowledge" from outside.
	 */
	RESET_COMMAND;
}

