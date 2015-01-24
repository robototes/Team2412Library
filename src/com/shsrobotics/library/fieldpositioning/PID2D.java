/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.shsrobotics.library.fieldpositioning;

import com.shsrobotics.library.util.functions.RCallback;
import com.shsrobotics.library.util.functions.UpdateFunction;
import com.shsrobotics.library.util.wrappers.DoubleWrap;

/**
 * Note that x, y, and z axis are not arbitrary.  z is rotation, x is 
 * lateral, y is forward/backward.
 * @author Max
 */
public class PID2D {    // TODO finish.  convert pidoutput to robot coordinates.
    
    /**
     * This asks for the next setpoint.  This is called as soon as 
     * the current setpoint is within tolerance*tscale of the current position.
     */
    public RCallback<Point> nextSetpoint;
    private PIDC x, y, z;
    public DoubleWrap ex, ey, ez;
    PID2DOutput drive;
    public PID2DSource in;
    private Point setpoint;
    public double tolerance;
    public double tscale = 7;
    private boolean straifDriving = false;
    private RobotPosition robot;
    
    private double[] worldVecToRobot(double x, double y) {
        double[] res = new double[2];
        double head = -robot.getHeading();
        res[0] = x*Math.cos(head) - y*Math.sin(head);
        res[1] = x*Math.sin(head) + y*Math.cos(head);
        return res;
    }
    
    /**
     * 0 pointing to setpoint
     * 1 driving to setpoint
     * 2 pointing to setpoint direction, if any.
     */
    private int driveState = 0;
    private final UpdateFunction pidFunc = new UpdateFunction(()->{
        if ( straifDriving ) {
            double cx = x.calc(in.getX());
            double cy = y.calc(in.getY());
            double cz = z.calc(in.getZ());
            double[] rxy = worldVecToRobot(cx, cy);
            double rz = cz-robot.getHeading();
            drive.drive(rxy[0],rxy[1],rz);
        }
        else {
            switch(driveState) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                default: 
                    driveState = 0; 
                    break;
            }
        }
        return withinTolerance(setpoint, new Point(in.getX(), in.getY(), in.getZ()), false);
    });
    
    private boolean withinTolerance(Point loc, Point sp, boolean z) {
        return Math.abs(loc.x - sp.x) < tolerance && Math.abs(loc.y - sp.y) < tolerance && (z ? Math.abs(loc.z - sp.z) < tolerance : true);
    }
    
    public PID2D(PID2DOutput drive, RobotPosition robot) {
        this.drive = drive;
        x = new PIDC();
        y = new PIDC();
        z = new PIDC();
        this.robot = robot;
    }
    
    public void setPIDX(double p, double i, double d) {
        x.p = p;
        x.i = i;
        x.d = d;
    }
    
    public void setPIDY(double p, double i, double d) {
        y.p = p;
        y.i = i;
        y.d = d;
    }
    
    public void setPIDZ(double p, double i, double d) {
        z.p = p;
        z.i = i;
        z.d = d;
    }
    
    public void setSetpointAndEnable(Point p) {
        if ( drive == null || in == null )
            throw new NullPointerException("Set Input and Outputs before this.");
        pidFunc.interupt();
        setpoint = p;
        
        UpdateFunction.run(pidFunc);
    }
    
    public void enableStraifDriving() {
        straifDriving = true;
        if ( pidFunc.isAlive() ) {
            System.out.println("WARNING: 2DPID controller was active when its drive type was changed to straif.");
        }
    }
    
    public void disableStraifDriving() {
        straifDriving = false;
        if ( pidFunc.isAlive() ) {
            System.out.println("WARNING: 2DPID controller was active when its drive type was changed to strait.");
        }
    }
    
    private class PIDC {
        private double p, i, d;
        private double de, ie;  // last error, error accumulator
        private double set;     // setpoint
        long last;
        private PIDC() {
            de = 0;
            ie = 0;
            p = 0;
            i = 0;
            d = 0;
        }
        private void set(double s) {
            set = s;
        }
        private double calc(double curr) {
            long t = System.currentTimeMillis();
            long diff = t - last;
            last = t;
            ie += (set-curr)*diff;
            de = (de-curr+set)/(last-t);
            return p*(set-curr) + i*ie + d*de;
        }
        
    }
    
}
