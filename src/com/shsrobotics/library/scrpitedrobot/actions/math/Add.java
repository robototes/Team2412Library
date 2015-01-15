/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shsrobotics.library.scrpitedrobot.actions.math;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.shsrobotics.library.scrpitedrobot.actions.math.Number;

/**
 *
 * @author Max
 */
public class Add extends Token {
    
    static Pattern pattern = Pattern.compile("^[+]");
    static int presedence = Token.ADD;
    
    public int getPresedence() {
        return presedence;
    }
    
    public static Matcher getMatcher(CharSequence seq) {
        return pattern.matcher(seq);
    }
    
    public String toString() {
        return "+";
    }
    
    public Add() {
    }

    public Number evaluate(Number v1, Number v2) {
        Number res = null;
        if ( !(v1.n instanceof Integer) ) {
            if ( !(v2.n instanceof Integer) ) {
                res = new Number( v2.n.doubleValue() + v1.n.doubleValue() );
            }
            else {
                res = new Number( v2.n.intValue() + v1.n.doubleValue() );
            }
        }
        else if ( !(v1.n instanceof Double) ) {
            if ( !(v2.n instanceof Integer) ) {
                res = new Number( v2.n.doubleValue() + v1.n.intValue() );
            }
            else {
                res = new Number( v2.n.intValue() + v1.n.intValue() );
            }
        }
        return res;
    }
}
