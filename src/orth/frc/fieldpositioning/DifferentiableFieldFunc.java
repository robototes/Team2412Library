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
public interface DifferentiableFieldFunc extends FieldFunc {
    /**
     * 
     * @param t
     * @return array containing a lot of stuff.
     */
    public double[] calc(double t);
}
