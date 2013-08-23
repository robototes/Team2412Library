package com.shsrobotics.library;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Relay;

/**
 * General useful constants.
 * @author Team 2412 <first.robototes.com, github.com/robototes>
 */
public interface GLOBAL {
	/** For robots with no gyroscope. */
	public static final double noGyroscope = 0.0;
	
	/**	 USB ports.	 */
	public static final int 
		USB_1 = 1,
		USB_2 = 2,
		USB_3 = 3,
		USB_4 = 4;
	
	/**	 PWM Ports.	 */
	public static final int
		PWM_1 = 1,
		PWM_2 = 2,
		PWM_3 = 3,
		PWM_4 = 4,
		PWM_5 = 5,
		PWM_6 = 6,
		PWM_7 = 7,
		PWM_8 = 8,
		PWM_9 = 9,
		PWM_10 = 10;
	
	/**	 Analog inputs.	 */
	public static final int
		ANALOG_1 = 1,
		ANALOG_2 = 2,
		ANALOG_3 = 3,
		ANALOG_4 = 4,
		ANALOG_5 = 5,
		ANALOG_6 = 6,
		ANALOG_7 = 7,
		ANALOG_8 = 8;
	
	/**	 Solenoid outputs.	 */
	public static final int
		SOLENOID_1 = 1,
		SOLENOID_2 = 2,
		SOLENOID_3 = 3,
		SOLENOID_4 = 4,
		SOLENOID_5 = 5,
		SOLENOID_6 = 6,
		SOLENOID_7 = 7,
		SOLENOID_8 = 8;
	
	/**	 Relay outputs.	 */
	public static final int
		RELAY_1 = 1,
		RELAY_2 = 2,
		RELAY_3 = 3,
		RELAY_4 = 4,
		RELAY_5 = 5,
		RELAY_6 = 6,
		RELAY_7 = 7,
		RELAY_8 = 8;
	
	/**	 Digital inputs/outputs.	 */
	public static final int
		DIGITAL_IO_1 = 1,
		DIGITAL_IO_2 = 2,
		DIGITAL_IO_3 = 3,
		DIGITAL_IO_4 = 4,
		DIGITAL_IO_5 = 5,
		DIGITAL_IO_6 = 6,
		DIGITAL_IO_7 = 7,
		DIGITAL_IO_8 = 8,
		DIGITAL_IO_9 = 9,
		DIGITAL_IO_10 = 10,
		DIGITAL_IO_11 = 11,
		DIGITAL_IO_12 = 12,
		DIGITAL_IO_13 = 13,
		DIGITAL_IO_14 = 14;
	
	/**	 Constants for relays.	 */
	public static final Relay.Value 
		ON = Relay.Value.kForward, 
		OFF = Relay.Value.kOff, 
		OPEN = Relay.Value.kForward,
		CLOSED = Relay.Value.kOff,
		FORWARD = Relay.Value.kForward,
		REVERSE = Relay.Value.kReverse;
	
	/**	 Constants for pneumatics.	 */
	public static final DoubleSolenoid.Value
		EXTENDED = DoubleSolenoid.Value.kForward,
		RETRACTED = DoubleSolenoid.Value.kReverse;
	
	public static DriverStationLCD screen = DriverStationLCD.getInstance();
	
	/**  Shorthand variables for lines and columns of Driver Station Screen. */
	public class Screen {
		/**	 Driver Station screen lines.	*/
		public static final DriverStationLCD.Line
			line1 = DriverStationLCD.Line.kUser1,
			line2 = DriverStationLCD.Line.kUser2,
			line3 = DriverStationLCD.Line.kUser3,
			line4 = DriverStationLCD.Line.kUser4,
			line5 = DriverStationLCD.Line.kUser5,
			line6 = DriverStationLCD.Line.kUser6;

		/**	 Driver Station tabs.	*/
		public static final int
			tab1 = 1,
			tab2 = 5,
			tab3 = 9,
			tab4 = 13;
	}
}
