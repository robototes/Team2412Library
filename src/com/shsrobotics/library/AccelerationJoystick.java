/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shsrobotics.library;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Y axis is the acceleration
 * X axis is rotation
 * @author Max
 */
public class AccelerationJoystick extends Joystick {
    private double accumulator;
    private static final double increment = 0.01;
    
    public AccelerationJoystick(int a, int b, int c) {
        super(a, b, c);
    }
    
    public AccelerationJoystick(int port) {
        super(port);
    }
    
    /**
     * This needs to be called every loop otherwise nothing will be accumulated.
     * @return The accumulated velocity.
     */
    
    public double getAccumulatedVelocity() {
        accumulator *= .7;
        accumulator += getY() * increment;
        if ( accumulator > 1 )
            accumulator = 1;
        else if ( accumulator < -1 )
            accumulator = -1;
        return accumulator;
    }
}
