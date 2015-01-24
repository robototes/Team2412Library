/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package orth.frc.fieldpositioning;

/**
 * Supplies the PID2D with position and velocity.
 * @author Max
 */
public interface PID2DSource {
    public double getX();
    public double getY();
    public double getZ();
    
    public double get_dx();
    public double get_dy();
    public double get_dz();
}
