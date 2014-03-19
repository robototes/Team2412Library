package com.shsrobotics.library;

import com.shsrobotics.library.link.DiscreteInput;
import edu.wpi.first.wpilibj.Joystick;

/**
 * Joystick button, de-bounced.
 * @author Cory McCartan
 */
public class JoystickButton extends Button implements DiscreteInput {

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