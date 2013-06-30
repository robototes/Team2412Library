package com.shsrobotics.library;

/**
 * Easy threading for FIRST robots
 * @author Team 2412 <first.robototes.com, github.com/robototes>
 */
public abstract class Task extends Thread {

	@Override
	public void run() {
		initialize();
		
		do {
			execute();
		} while (!isFinished());
		
		end();
	}
	
	protected abstract void initialize();

	protected abstract void execute();
	
	protected abstract boolean isFinished();
	
	protected abstract void end();
}