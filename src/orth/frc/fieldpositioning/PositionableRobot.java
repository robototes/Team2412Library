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
public interface PositionableRobot {
    public static final int 
            PATH_ABSOLUTE = 1,
            PATH_RELATIVE = 2,
            PATH_JUMP_TO_START = 4;
    
    public double getX();
    public double getY();
    public double getCollisionRadius();
    
    /**
     * TODO fix return type
     * @return PIDController
     * 
     */
    public PID2D getPID();
    
    public static MovableFieldObject getMovableFieldObject(PositionableRobot pr) {
        return new MovableFieldObject(pr.getX(),pr.getY(),pr.getCollisionRadius());
    }
    
}
