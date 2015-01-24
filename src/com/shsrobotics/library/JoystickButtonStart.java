package com.shsrobotics.library;

import com.shsrobotics.library.util.functions.VoidCallback;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Starts tasks when an event occurs.
 * @author Cory McCartan.  Revised by Max Orth
 */
public class JoystickButtonStart extends Button {

	private final Joystick joystick;
	private final int button;
	Task task;
	
	public JoystickButtonStart(Joystick joystick, int button, Task t) {
		this.joystick = joystick;
		this.button = button;
		this.task = t;
	}
	
	
	public boolean get() {
		return joystick.getRawButton(button);
	}
	
	public void doFunc() {
		task.start();
	}
	
}