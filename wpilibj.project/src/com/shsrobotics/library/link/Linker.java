package com.shsrobotics.library.link;

import java.util.Vector;

/**
 * Handles links, inputs, and triggers.
 * @author Team 2412 <first.robototes.com, github.com/robototes>
 */
public class Linker {
	
	private Vector links;
	private Vector actions;
	
	private Vector linkPriorities;
	private Vector actionPriorities;
	
	private int sizeL = 0;
	private int sizeA = 0;
	
	private Linker() { }
	
	public void update() {
		int priorityL = ((Integer) linkPriorities.elementAt(sizeL - 1)).intValue() + 1; // lowest priority
		for (int i = 0; i < sizeL; i++) {
			Link l = (Link) links.elementAt(i);
			if (l.priority > priorityL) break;
			
			if (l.get()) {
				l.link();
				priorityL = l.priority; // stop after this priority is done with
			}
		}
		
		int priorityA = ((Integer) actionPriorities.elementAt(sizeA - 1)).intValue() + 1; // lowest priority
		for (int i = 0; i < sizeA; i++) {
			Action a = (Action) actions.elementAt(i);
			if (a.priority > priorityA) break;
			
			if (a.trigger()) {
				a.action();
				priorityA = a.priority; // stop after this priority is done with
			}
		}
	}
	
	/**
	 * Add a link to the list of links.
	 * @param link the link.
	 */
	public void addLink(Link link) {
		Integer priority = new Integer(link.priority);
		int index = linkPriorities.lastIndexOf(priority);
		if (index > 0) {
			linkPriorities.insertElementAt(priority, index);
			links.insertElementAt(link, index);
		} else {
			linkPriorities.addElement(priority);
			linkPriorities.addElement(link);
		}
		
		sizeL++;
	}
	
	/**
	 * Add an action to the list of actions.
	 * @param action the action.
	 */
	public void addAction(Action action) {
		Integer priority = new Integer(action.priority);
		int index = actionPriorities.lastIndexOf(priority);
		if (index > 0) {
			actionPriorities.insertElementAt(priority, index);
			actions.insertElementAt(action, index);
		} else {
			actionPriorities.addElement(priority);
			actions.addElement(action);
		}
		
		sizeA++;
	}
	
	/**
	 * Get an instance of the Linker.
	 * @return the instance.
	 */
	public static Linker getInstance() {
		return LinkerHolder.INSTANCE;
	}
	
	private static class LinkerHolder {
		private static final Linker INSTANCE = new Linker();
	}
}
