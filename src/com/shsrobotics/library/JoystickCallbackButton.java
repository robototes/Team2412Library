package com.shsrobotics.library;

import com.shsrobotics.library.util.functions.VoidCallback;

import edu.wpi.first.wpilibj.Joystick;

/**
 * 
 * 
 * @author s-orthm
 *
 */

public class JoystickCallbackButton extends Button {
	
	private VoidCallback callback;
	private Joystick joystick;
	int button;
	
	public JoystickCallbackButton(Joystick joystick, int button, VoidCallback func) {
		this.joystick = joystick;
		callback = func;
		this.button = button;
	}

	@Override
	public boolean get() {
		return joystick.getRawButton(button);
	}

	@Override
	public void doFunc() {
		callback.callback();
	}
	
}
