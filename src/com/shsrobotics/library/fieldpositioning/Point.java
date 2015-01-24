/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.shsrobotics.library.fieldpositioning;

/**
 * When Z is NaN, it means disregard rotation.  Cause rotation is Z axis.
 * @author Max
 */
public class Point {
    public double x, y, z;
    public Point(double sx, double sy) {
        x = sx;
        y = sy;
        z = Double.NaN;
    }
    public Point(double sx, double sy, double sz) {
        x = sx;
        y = sy;
        z = sz;
    }
    
    public void clearZ() {
        z = Double.NaN;
    }
    
}
