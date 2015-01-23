package com.shsrobotics.library;

public interface Subsystem {
	/**
	 * Should stop all processes going on in this subsystem and prevent any
	 * further processes or actions from starting;
	 */
	public void estop();
	
	public SubsystemState getState();
}
