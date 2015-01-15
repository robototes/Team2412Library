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
public class Subtract extends Token {
    
    static Pattern pattern = Pattern.compile("^[-]");
    static int presedence = Token.SUB;
    
    public int getPresedence() {
        return presedence;
    }
    
    public static Matcher getMatcher(CharSequence seq) {
        return pattern.matcher(seq);
    }
    
    public String toString() {
        return "-";
    }
    
    public Subtract() {
    }
    
    public Number evaluate(Number a, Number b) {
        Number res = null;
        if ( (a.n instanceof Double) ) {
            if ( (b.n instanceof Double) ) {
                res = new Number( b.n.doubleValue() - a.n.doubleValue() );
            }
            else {
                res = new Number( b.n.intValue() - a.n.doubleValue() );
            }
        }
        else if ( (a.n instanceof Integer) ) {
            if ( (b.n instanceof Double) ) {
                res = new Number( b.n.doubleValue() - a.n.intValue() );
            }
            else {
                res = new Number( b.n.intValue() - a.n.intValue() );
            }
        }
        return res;
    }
}
