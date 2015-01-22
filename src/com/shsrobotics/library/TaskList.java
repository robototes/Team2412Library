package com.shsrobotics.library;

import java.util.LinkedList;

public abstract class TaskList extends Thread {
	private LinkedList<Task> list;
	
	private RunType runType;
	private int pTaskCount = 0;
	final Object lock = new Object();
	private boolean runOnce = false;
	public TaskList() {
		runType = RunType.SEQUENTIAL;
	}

	/**
	 * starts the task list.
	 */
	@Override
	public synchronized void start() {
		if ( !runOnce ) {
			runOnce = true;
			super.start();
		}
		else {
			new IllegalThreadStateException("Tried to run a TaskList more than once.  Can only run a thread once!").printStackTrace();
		}
	}
	
	public final void run() {
		if ( runOnce ) {
			return;
		}
		runTasks();
	}
	
	/**
	 * You might experience some difficulties with t.  Just don't try to stop t with stop() or start with start(), because t is not running.
	 * @param t
	 * @return The wrapped task.  If in sequential mode, this is probably useless.
	 * @throws InterruptedException if the task is started on sequential mode and this thread is interrupted.  
	 */
	protected Task begin(final Task t) throws InterruptedException {	// TODO put waitForThreads at beginning of begin() (for sequential)
		t.stop();
		Task w;
		w = new Task() {
			@Override
			protected void initialize() {
				t.initialize();
			}
			@Override
			protected void execute() {
				t.execute();
			}
			@Override
			protected boolean isFinished() {
				return t.isFinished();
			}
			@Override
			protected void end() {
				t.end();
				synchronized(lock) {
					lock.notifyAll();
				}
			}
		};
		if ( runType == RunType.PARALLEL ) {
			pTaskCount++;
		}
		else if ( runType == RunType.SEQUENTIAL ) {
			waitForThreads();
		}
		w.start();
		return w;
	}
	
	/**
	 * Waits for all threads executed since the last call to wait to finish.
	 * @throws InterruptedException
	 */
	protected void waitForThreads() throws InterruptedException {
		while ( pTaskCount != 0 ) {
			synchronized(lock) {
				lock.wait();
				pTaskCount--;
			}
		}
	}
	
	protected void runSequential() {
		runType = RunType.SEQUENTIAL;
	}
	
	protected void runParallel() {
		runType = RunType.PARALLEL;
	}
	
	protected abstract void runTasks();
	
	
	private enum RunType {
		PARALLEL, SEQUENTIAL;
	}
	
}
