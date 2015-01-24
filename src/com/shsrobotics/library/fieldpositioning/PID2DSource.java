/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.shsrobotics.library.fieldpositioning;

/**
 * Supplies the PID2D with positions and velocities.  
 * These are relative to the robot.
 * @author Max
 */
public interface PID2DSource {
    public abstract double getX();
    public abstract double getY();
    public abstract double getZ();
    
}
