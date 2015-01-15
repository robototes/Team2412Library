/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shsrobotics.library.scrpitedrobot.actions.math;

/**
 *
 * @author Max
 */
public class HeapNumber extends Number {
    
    String name;
    
    private HeapNumber(double n) {
        super(n);
    }
    
    private HeapNumber(int n) {
        super(n);
    }
    
    public HeapNumber(java.lang.Number ref) {
        super(ref);
    }
    
}
