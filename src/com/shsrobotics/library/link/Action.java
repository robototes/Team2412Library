package com.shsrobotics.library.link;

/**
 * An multi-step action that can be triggered.
 * @author Team 2412 <first.robototes.com, github.com/robototes>
 */
public abstract class Action {
	public final int priority;
	
	/**
	 * Create an action.
	 * @param priority action's priority, lower is higher priority.
	 */
	public Action(int priority) {
		this.priority = priority;
	}
	
	/**
	 * Called to check if trigger condition is true.
	 * @return boolean true if action should fire.
	 */
	public abstract boolean trigger();
	
	/**
	 * Called to fire action.
	 */
	public abstract void action();
}
