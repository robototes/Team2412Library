/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shsrobotics.library.scrpitedrobot.actions.math;

import com.shsrobotics.library.scrpitedrobot.VirtualRobot;
import com.shsrobotics.library.scrpitedrobot.actions.math.Number;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Max
 */
public class Divide extends Token {
    
    static Pattern pattern = Pattern.compile("^[/]");
    static int presedence = Token.DIV;
    
    public int getPresedence() {
        return presedence;
    }
    
    public static Matcher getMatcher(CharSequence seq) {
        return pattern.matcher(seq);
    }
    
    public String toString() {
        return "/";
    }
    
    public Divide() {
    }

    public Number evaluate(Number vd, Number vn) {
        Number res = null;
        if ( (vd.n instanceof Double) ) {
            if ( vd.n.doubleValue() == 0 ) VirtualRobot.throwError("Divide by Zero error.");
            else if ( (vn.n instanceof Double) ) {
                res = new Number( vn.n.doubleValue() / vd.n.doubleValue() );
            }
            else {
                res = new Number( vn.n.intValue() / vd.n.doubleValue() );
            }
        }
        else if ( (vd.n instanceof Integer) ) {
            if ( vd.n.intValue() == 0 ) VirtualRobot.throwError("Divide by Zero error.");
            else if ( (vn.n instanceof Double) ) {
                res = new Number( vn.n.doubleValue() / vd.n.intValue() );
            }
            else {
                res = new Number( vn.n.intValue() / vd.n.intValue() );
            }
        }
        return res;
    }

}
