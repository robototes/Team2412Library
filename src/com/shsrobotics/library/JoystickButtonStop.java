package com.shsrobotics.library;

import edu.wpi.first.wpilibj.Joystick;

/**
 * 
 * @author s-orthm
 *
 */
public class JoystickButtonStop extends Button {

	private final Joystick joystick;
	private final int button;
	Task task;
	
	public JoystickButtonStop(Joystick joystick, int button, Task t) {
		this.joystick = joystick;
		this.button = button;
		this.task = t;
	}
	
	
	public boolean get() {
		return joystick.getRawButton(button);
	}
	
	public void doFunc() {
		task.stop();
	}

}
