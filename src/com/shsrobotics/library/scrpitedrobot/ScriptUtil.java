/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shsrobotics.library.scrpitedrobot;

/**
 *
 * @author Max
 */
public class ScriptUtil {
    private ScriptUtil() {
    }
    
    public static Number parseNumber(String s, int l) {
        Number n = null;
        
        try {
            int vi = Integer.parseInt(s);
            n = new Integer(vi);
        }
        catch (NumberFormatException nfe) {
            try {
                double vf = Double.parseDouble(s);
                n = new Double(vf);
            }
            catch (NumberFormatException nfe2) {
                ScriptReader.throwCompileError("Illegal number format, defaulting to 0.", l);
            }
        }
        return n;
    }
    
    
}
