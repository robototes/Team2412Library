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
public class Number extends Token {
    protected java.lang.Number n;
    
    static Pattern patternInteger = Pattern.compile("^[-]?[0-9]+");
    static Pattern patternDouble = Pattern.compile("^[-]?[0-9]*[.][0-9]*");
    static int presedence = Token.NUMBER;
    
    public int getPresedence() {
        return presedence;
    }
    
    public String toString() {
        return n instanceof Double ? n.doubleValue()+"d" : n.intValue() + "i";
    }
    
    private static Matcher matcherInt;
    private static Matcher matcherDouble;
    private static String matchedInt;
    private static String matchedDouble;
    
    public Number(int value) {
        setNumber(new Integer(value));
    }
    
    public Number(double value) {
        setNumber(new Double(value));
    }
    
    public Number(java.lang.Number num) {
        n = num;
    }
    
    public double getd() {
        return getNumber().doubleValue();
    }
    
    public int geti() {
        return getNumber().intValue();
    }
    
    public java.lang.Number getNumber() {
        return n;
    }
    
    void setNumber(java.lang.Number n) {
        this.n = n;
    }
    
    public static Matcher getIntMatcher(CharSequence seq) {
        return patternInteger.matcher(seq);
    }
    
    public static boolean matchIntAndSave(CharSequence seq) {
        matcherInt = patternInteger.matcher(seq);
        if ( matcherInt.lookingAt() ) {
            matchedInt = matcherInt.group();
            return true;
        }
        matchedInt = null;
        return false;
    }
    
    public static boolean matchDoubleAndSave(CharSequence seq) {
        matcherDouble = patternDouble.matcher(seq);
        if ( matcherDouble.lookingAt() ) {
            matchedDouble = matcherDouble.group();
            return true;
        }
        matchedDouble = null;
        return false;
    }
    
    public static int getMatchedIntLength() {
        return matchedInt.length();
    }
    
    public static int getMatchedInt() {
        return Integer.parseInt(matchedInt);
    }
    
    public static double getMatchedDouble() {
        return Double.parseDouble(matchedDouble);
    }
    
    public static int getMatchedDoubleLength() {
        return matchedDouble.length();
    }

    public Number evaluate(Number a, Number b) {
        throw new UnsupportedOperationException("A Number cannot evaluate numbers.");
    }
    
    
}
