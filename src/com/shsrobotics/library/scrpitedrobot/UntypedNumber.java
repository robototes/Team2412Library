/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shsrobotics.library.scrpitedrobot;

/**
 * Represents a dynamically typed number.  The possible types are long and double or int and float.
 * @author Max
 */
public class UntypedNumber extends Number {
    long lval;
    double dval;
    
    
    public UntypedNumber(int v) {
        lval = v;
        dval = (float)v;
    }
    
    public UntypedNumber(float v) {
        lval = (int)v;
        dval = v;
    }
    
    public void setValue(long v) {
        lval = v;
        dval = (double)v;
    }
    
    public void setValue(int v) {
        lval = v;
        dval = (double)v;
    }
    
    public void setValue(double v) {
        lval = (long)v;
        dval = v;
    }
    
    public void setValue(float v) {
        lval = (long)v;
        dval = (double)v;
    }
    
    public int intValue() {
        return (int)lval;
    }
    
    public long longValue() {
        return (long)lval;
    }

    public float floatValue() {
        return (float)dval;
    }

    public double doubleValue() {
        return dval;
    }
    
}
