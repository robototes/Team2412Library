/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package orth.frc.fieldpositioning;

import orth.frc.fieldpositioning.function.Callback;
import orth.frc.fieldpositioning.function.RCallback;

/**
 *
 * @author Max
 */
public class PID2D {    // TODO finish
    
    /**
     * the Double parameter is how many radians off the robot is from 
     * facing the direction this controller wishes to go.  Leave it null 
     * if the robot can strafe.
     */
    public Callback<Double> orient;
    /**
     * This asks for the next setpoint.  Note that this is called as soon as 
     * the current setpoint is within tolerance*3 of the current position.
     */
    public RCallback<Point> nextSetpoint;
    public double p, i, d;
    public PID2DOutput out;
    public PID2DSource in;
    private double setX, setY, setZ;
    public double tolerance;
    
    public PID2D(double P, double I, double D, Callback<Double> orient) {
        p = P;
        d = D;
        i = I;
        this.orient = orient;
    }
    
    public void setSetpointAndEnable(double x, double y) {
        setX = x;
        setY = y;
        if ( orient != null ) {
            
        }
    }
    
}
