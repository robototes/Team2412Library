package com.shsrobotics.library;

import edu.wpi.first.wpilibj.Relay;

/**
 * General useful constants.
 * @author Team 2412 <first.robototes.com, github.com/robototes>
 */
public interface GLOBAL {
	/** USB port 1.		*/
	public static final int USB_1 = 1;
	
	/** USB port 2.		*/
	public static final int USB_2 = 2;
	
	/** USB port 3.		*/
	public static final int USB_3 = 3;
	
	/** USB port 4.		*/
	public static final int USB_4 = 4;
	
	/** For robots with no gyroscope. */
	public static final double noGyroscopeAngle = 0.0;
	
	//universal constants
	public static final Relay.Value 
		ON = Relay.Value.kForward, 
		OFF = Relay.Value.kOff, 
		OPEN = Relay.Value.kForward,
		CLOSED = Relay.Value.kOff,
		FORWARD = Relay.Value.kForward,
		REVERSE = Relay.Value.kReverse;
}
