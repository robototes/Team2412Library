package com.shsrobotics.library;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Joystick button, de-bounced.
 * @author Cory McCartan
 */
public class JoystickButton extends Button {

	private final Joystick joystick;
	private final int button;
	
	public JoystickButton(Joystick joystick, int button) {
		this.joystick = joystick;
		this.button = button;
	}
	
	public boolean get() {
		return joystick.getRawButton(button);
	}
	
}