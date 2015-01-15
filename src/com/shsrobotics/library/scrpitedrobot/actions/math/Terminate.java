/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shsrobotics.library.scrpitedrobot.actions.math;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Max
 */
public class Terminate extends Token {
    
    static Pattern pattern = Pattern.compile("^$");
    static int presedence = Token.TERMINATE;
    
    public int getPresedence() {
        return presedence;
    }
    
    public static Matcher getMatcher(CharSequence seq) {
        return pattern.matcher(seq);
    }
    
    public String toString() {
        return "T";
    }
    
    public Terminate() {
        
    }
    
    public Number evaluate(Number a, Number b) {
        throw new UnsupportedOperationException("Terminate cannot evaluate numbers."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
