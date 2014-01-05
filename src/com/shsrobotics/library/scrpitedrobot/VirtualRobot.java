/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shsrobotics.library.scrpitedrobot;

import edu.wpi.first.wpilibj.SensorBase;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

/**
 * A VirtualRobot has a reference to every possible IO port.  This robot is 
 * controlled by a script file, whose syntax and usage is defined elsewhere.
 * @author Max
 */
public class VirtualRobot implements RobotConstants {
    protected static final Hashtable SENSOR_ARRAY = new Hashtable();
    protected static final Hashtable VARIABLE_ARRAY = new Hashtable();
    protected static final Hashtable NON_STATIC_VAR_ARRAY = new Hashtable();
    private static BufferedOutputStream outStream = new BufferedOutputStream(System.out);
    
    //static
    static {
        try {
            ScriptReader.setResourceInputStream(new FileInputStream(SCRIPT_PATH+"robot.res"));
        }
        catch (FileNotFoundException fnfe) {
            VirtualRobot.throwError(SCRIPT_PATH + " does not exist; Consider checking where %appdata% actually leads to.");
            throw new Error(SCRIPT_PATH + " does not exist.", fnfe);
        }
    }
    
    public static void setOutputStream(OutputStream os) {
        if ( os != null )
            outStream = new BufferedOutputStream(os);
    }
    
    static void initHardware(Object h, String name) {
        if ( h instanceof Number ) {
            VARIABLE_ARRAY.put(name, h);
            throwMessage("Initialized a number");
            System.out.println("Initialized a number " + ((Number)h).floatValue());
        }
        else if ( h instanceof VirtualSensor ) {
            SENSOR_ARRAY.put(h, name);
        }
        else if ( h instanceof SensorBase ) {
            SENSOR_ARRAY.put(h, name);
            System.out.println("Initialized a sensor");
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
    
    public static boolean hasLocalVariable(String key) {
        return NON_STATIC_VAR_ARRAY.containsKey(key);
    }
    
    public static Number getLocalVariable(String key) {
        return (Number)NON_STATIC_VAR_ARRAY.get(key);
    }
    
    public static void setLocalVariable(String key, Number n) {
        if ( hasLocalVariable(key) ) {
            NON_STATIC_VAR_ARRAY.remove(key);
            NON_STATIC_VAR_ARRAY.put(key, n);
        }
    }
    
    public static void throwError(String msg) {
        try {
            outStream.write((msg + "\n").getBytes());
        }
        catch (IOException ioe) {
            throw new Error(ioe);
        }
    }
    
    public static void throwMessage(String msg) {
        try {
            outStream.write((msg + "\n").getBytes());
        }
        catch (IOException ioe) {
            throw new Error(ioe);
        }
    }
    
    protected static BufferedOutputStream getOutputStream() {
        return outStream;
    }
    
}
