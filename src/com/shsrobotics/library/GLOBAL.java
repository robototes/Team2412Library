package com.shsrobotics.library;


import edu.wpi.first.wpilibj.DoubleSolenoid;
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
		USB_0 = 0,
		USB_1 = 1,
		USB_2 = 2,
		USB_3 = 3,
		USB_4 = 4;
	
	/**	 PWM Ports.	 */
	public static final int
		PWM_0 = 0,
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
	
	/** CAN Device Numbers. */
	public static final int
		CAN_0 = 0,
		CAN_1 = 1,
		CAN_2 = 2,
		CAN_3 = 3,
		CAN_4 = 4,
		CAN_5 = 5,
		CAN_6 = 6,
		CAN_7 = 7,
		CAN_8 = 8;
	
	/**	 Analog inputs.	 */
	public static final int
		ANALOG_0 = 0,
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
		DIGITAL_IO_0 = 0,
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
}
