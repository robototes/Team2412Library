/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shsrobotics.library.scrpitedrobot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Hashtable;
import java.util.Vector;

/**
 * A VirtualRobot has a reference to every possible IO port.  This robot is 
 * controlled by a script file, whose syntax and usage is defined elsewhere.
 * @author Max
 */
public class VirtualRobot implements RobotConstants {
    protected static final Vector SENSOR_ARRAY = new Vector();
    protected static final Hashtable VARIABLE_ARRAY = new Hashtable();
    
    static {
        try {
            ScriptReader.setInputStream(new FileInputStream(SCRIPT_PATH));
        }
        catch (FileNotFoundException fnfe) {
            VirtualRobot.throwError(SCRIPT_PATH + " does not exist; Consider checking where %appdata% actually leads to.");
            throw new Error(SCRIPT_PATH + " does not exist; Consider checking where %appdata% actually leads to.", fnfe);
        }
    }
    
    private VirtualRobot() {
    }
    
    protected static void executePeriodic() {
        
    }
    
    public static void putVariable(String key, Object value) {
        VARIABLE_ARRAY.put(key, value);
    }
    
    public static Object getVariable(String key) {
        return VARIABLE_ARRAY.get(key);
    }
    
    public static boolean hasVariable(String key) {
        return VARIABLE_ARRAY.containsKey(key);
    }
    
    public static void throwError(String msg) {
        
    }
    
}
