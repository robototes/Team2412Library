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
public class PerenthesesOpen extends Token {
    
    static Pattern pattern = Pattern.compile("^[(]");
    static int presedence = Token.POPEN;
    
    public int getPresedence() {
        return presedence;
    }
    
    public static Matcher getMatcher(CharSequence seq) {
        return pattern.matcher(seq);
    }
    
    public String toString() {
        return "(";
    }
    
    public PerenthesesOpen() {
    }
    
    public Number evaluate(Number a, Number b) {
        throw new EvaluationException("Perentheses should not be in the evaluation stack."); //To change body of generated methods, choose Tools | Templates.
    }
}
