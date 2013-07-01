package com.shsrobotics.library;

/**
 * General useful constants.
 * @author Team 2412 <first.robototes.com, github.com/robototes>
 */
public class GLOBAL {
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
	
	/** Converts seconds to milliseconds */
	public static int getMillesecondsFromSeconds(double seconds) {
		return (int) (seconds * 1000);
	}
	
	/** Converts seconds to milliseconds */
	public static int getMicrosecondsFromSeconds(double seconds) {
		return (int) (seconds * 1000000);
	}
	
	/** Converts seconds to milliseconds */
	public static int getMicrosecondsFromMilliseconds(double seconds) {
		return (int) (seconds * 1000);
	} 
}
