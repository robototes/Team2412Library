package com.shsrobotics.library.link;

/**
 * A hardware output.
 * @author Team 2412 <first.robototes.com, github.com/robototes>
 */
public interface Output {
	/**
	 * Set the hardware value.
	 * @param value the from input or code.
	 */
	public void set(boolean value);
	/**
	 * Set the hardware value.
	 * @param value the from input or code.
	 */
	public void set(double value);
}
