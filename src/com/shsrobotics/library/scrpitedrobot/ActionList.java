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
class ActionList {
    
    Vector actionList;
    
    public ActionList() {
        actionList = new Vector();
    }
    
    public Action getAction(int i) {
        return (Action)actionList.get(i);
    }
    
    public void putAction(Action a) {
        actionList.add(a);
    }
    
}
