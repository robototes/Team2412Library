package com.shsrobotics.library;

import edu.wpi.first.wpilibj.Timer;

/**
 * Easy threading for FIRST robots.
 * @author Team 2412 <first.robototes.com, github.com/robototes>
 */
public abstract class Task {	// TODO boolean done = false, gets set to true after end() is called in thread.

	private boolean forceStop = false;
	private int timeout = -1; // can't ever timeout.
	private final Timer timer = new Timer();
	
	public final Task stop() {
		forceStop = true;
		
		return this;
	}
	
	public final Task start() {
		new TaskThread().start();
		
		return this;
	}
	
	public final boolean running() {
		return forceStop;
	}
	
	/**
	 * Called once before task runs.
	 */
	protected abstract void initialize();

	/**
	 * Called repeatedly while task is running.  Guaranteed to run at least once.
	 */
	protected abstract void execute();
	
	/**
	 * Called repeatedly to check whether task is done.
	 * @return {@code true} if done, {@code false} if not.
	 */
	protected abstract boolean isFinished();
	
	/**
	 * Called when interrupted or when done.
	 */
	protected abstract void end();
	
	/**
	 * Set the task to time-out after a set number of milliseconds.
	 * @param milliseconds the number of milliseconds to run the task for.
	 */
	protected void setTimeout(int milliseconds) {
		timeout = milliseconds * 1000; // in microseconds
	}
	
	/**
	 * Checks to see if the task has timed out.
	 * @return {@code true} if timed out.
	 */
	private boolean isTimedOut() {
		return timer.get() > timeout;
	}
	
	
	class TaskThread extends Thread {
		public final void run() {
			timer.start();
			initialize();
			
			do {
				execute();
			} while (!isFinished() && !forceStop && !isTimedOut());
			
			end();
		}
	}
}