package com.shsrobotics.library;

import com.shsrobotics.library.util.functions.VoidCallback;

/**
 * Button, with de-bouncing included.
 * @author Cory McCartan.  Revised by Max Orth
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
	
	public abstract void doFunc();
	
	/**
	 * Start something when pressed.
	 * @param task the task to start.
	 */
	public void whenPressed() {
		if ( pressed() ) {
			doFunc();
		}
	}
	
	/**
	 * What to do when stopped.
	 * @param task the task to start.
	 */
	public void whenReleased() {
		if ( lastHeld && !get() )
			doFunc();
	}
}
