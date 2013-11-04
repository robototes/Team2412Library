/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shsrobotics.library.scrpitedrobot;

import java.util.Vector;

/**
 *
 * @author Max
 */
public class ActionList {
    /**
     * Stores {@link Action}s.  Needs to be optimized to something that is not array-based?
     */
    private Vector actionList;
    
    /**
     * We can optimize these numbers based on the file size, in lines, since one line is usually 1 or 2 actions.
     * @see ActionList#ActionList(int, int)
     */
    public ActionList() {
        this(20, 5);
    }
    
    /**
     * {@code initial} is the number of lines in the file.
     * {@code increment} should be the average number of actions per line.
     * <blockquote>Since these numbers only affect compilation time, it is not critical to optimize these parameters.</blockquote>
     * @param initial The number of actions to start with.
     * @param increment The number of slots the ActionList adds to itself when it needs to resize.
     */
    public ActionList(int initial, int increment) {
        actionList = new Vector(initial, increment);
    }
    
    public Action getAction(int i) {
        return (Action)actionList.get(i);
    }
    
    /**
     * Note that once an action is in the list, it is impossible to remove it.
     * In this way it could be considered immutable.
     * @param a 
     */
    public void putAction(Action a) {
        actionList.add(a);
    }
    
    /**
     * To be called by the VR every time teleopPeriodic() is called.
     * Actions in actionList are called starting at index 0 and continuing down (a queue).
     */
    public void execute() {
        for ( int i = 0; i < actionList.size(); i++ ) {
            ((Action)actionList.get(i)).performAction();
        }
    }
    
}
