package com.shsrobotics.library.link;

/**
 * A continuous real-world input.
 * @author Team 2412 <first.robototes.com, github.com/robototes>
 */
public interface ContinuousInput extends Input {
	/**
	 * Get the value of the input.
	 * @return the value.
	 */
	public double get();
}
