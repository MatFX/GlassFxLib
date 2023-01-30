package eu.matfx.tools;

/**
 * Command enums; To interacte with components from outside 
 * @author m.goerlich
 *
 */
public enum Command
{
	PREVIOUS_SENSOR_VALUE, NEXT_SENSOR_VALUE, AUTO_CHANGE,
	

	/**
	 * <br>you need the reset as a "acknowledge" from outside.
	 */
	RESET_COMMAND;
}

