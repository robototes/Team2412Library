/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package orth.frc.fieldpositioning;

/**
 *
 * @author Max
 */
public interface PID2DOutput {
    /**
     * +x is left
     * @param dx 
     */
    public void setLocal_dX(double dx);
    /**
     * +y is forward
     * @param dy 
     */
    public void setLocal_dY(double dy);
    /**
     * +z is clockwise
     * @param dz 
     */
    public void setLocal_dZ(double dz);
}
