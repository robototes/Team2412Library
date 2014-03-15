package com.shsrobotics.library;

/**
 * Button, with de-bouncing included.
 * @author Cory McCartan
 */
public abstract class Button {
	private boolean lastHeld = false;
	
	public abstract boolean get();
	
	/**
	 * Check if the button is being held.
	 * @return {@code true} if being held currently.
	 */
	public boolean held() {
		return get();
	}
	
	/**
	 * Check of the button has been pressed.
	 * @return {@code true} if it has been newly pressed.
	 */
	public boolean pressed() {
		boolean state = get();
		
		if (state) {
			if (!lastHeld) { // new press
				lastHeld = true;
				return true;
			}
		} else {
			lastHeld = false;
		}
		
		return false;
	}
	
	/**
	 * Start a task when pressed.
	 * @param task the task to start.
	 */
	public void whenPressed(Task task) {
		if (pressed()) {
			task.start();
		}
	}
	
	/**
	 * Start a task when pressed, stop it when released.
	 * @param task the task to start.
	 */
	public void whileHeld(Task task) {
		if (pressed()) {
			task.start();
		}
		if (!held()) {
			task.end();
			task.stop();
		}
	}
}
