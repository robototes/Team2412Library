package com.shsrobotics.library;

import edu.wpi.first.wpilibj.Timer;

/**
 * Easy threading for FIRST robots.
 * @author Team 2412 <first.robototes.com, github.com/robototes>
 */
public abstract class Task {

	private boolean forceStop = false;
	private int timeout = -1; // can't ever timeout.
	private Timer timer = new Timer();
	private RunningAverage averageLoopTime = new RunningAverage();
	private double lastTime = 0.0;
	
	public final Task stop() {
		forceStop = true;
		
		return this;
	}
	
	public final Task start() {
		new TaskThread().start();
		
		return this;
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
		if (timeout == -1) {
			return false;
		}
		
		int difference = (int) Math.abs(timer.get() - timeout);
		
		if (difference < averageLoopTime.get()) { // so we don't overshoot accidentaly.
			return true;
		} else {
			return false;
		}
	}
	
	
	class TaskThread extends Thread {
		public final void run() {
			timer.start();
			initialize();
		
			do {
				execute();
				// update averages
				averageLoopTime.update(timer.get() - lastTime);
				lastTime = timer.get();
			} while (!isFinished() && !forceStop && !isTimedOut());

			end();
		}
	}
	
	private class RunningAverage {
		private double average = 0;
		private int count = 0;
		
		public double update(double x) {
			average += (x - average) / ++count;
			return get();
		};
		
		public double get() {
			return average;
		}
	}
}