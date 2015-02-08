package com.shsrobotics.library;

import java.util.Map;

public interface Subsystem {
	/**
	 * Should stop all processes going on in this subsystem and prevent any
	 * further processes or actions from starting;
	 */
	public void estop();
	
	public SubsystemState getState();
	
	/**
	 * Add key value pairs, where the key is a unique string representing the 
	 * part and the value is the value.  Valid types are all types that can be put into a network table.
	 * @param keyvalue
	 */
	public void returnState(Map<String,Object> keyvalue);
	
}
