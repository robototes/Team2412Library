/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.shsrobotics.library.fieldpositioning;

import java.util.Iterator;

import com.shsrobotics.library.util.functions.RCallback;
import com.shsrobotics.library.util.functions.UpdateFunction;
import com.shsrobotics.library.util.wrappers.DoubleWrap;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * Note that x, y, and z axis are not arbitrary.  z is rotation, x is 
 * lateral, y is forward/backward.
 * @author Max
 */
public class PID2D {    // TODO finish.  convert pidoutput to robot coordinates.
    
    private Controller x, y, z;
    private PIDC rx, ry, rz;
    public DoubleWrap ex, ey, ez;
    PID2DOutput drive;
    public PID2DSource in;
    private Point setpoint;
    private FieldNode[] setPath;
    Iterator<FieldNode> pathIterator;
    public double tolerance;
    public double tscale = 7;
    private boolean straifDriving = false;
    private RobotPosition robot;
    private int driveType;
    private static final int DRIVE_FIELD_PATH = 1;
    private static final int DRIVE_SETPOINT = 0;
    /**
     * 0 pointing to setpoint
     * 1 driving to setpoint
     * 2 pointing to setpoint direction, if any.
     */
    private int driveState = 0;
    private PIDUpdateFunc pidFunc;
    
    // Simple rotation
    private double[] worldVecToRobot(double x, double y) {
        double[] res = new double[2];
        // negates heading, since heading is an angle from the vertical (or straight ahead).  We then rotate the vector using the negated angle.
        double head = -robot.getHeading();
        res[0] = x*Math.cos(head) - y*Math.sin(head);
        res[1] = x*Math.sin(head) + y*Math.cos(head);
        return res;
    }
    
    
    private boolean withinTolerance(Point loc, Point sp, boolean z) {
        return Math.abs(loc.x - sp.x) < tolerance && Math.abs(loc.y - sp.y) < tolerance && (z ? Math.abs(loc.z - sp.z) < tolerance : true);
    }
    
    public PID2D(PID2DOutput drive, RobotPosition robot) {
        this.drive = drive;
        
        x = new Controller(ry,rx);
        y = new Controller(rx,ry);
        z = new Controller(rz,rz);
        this.robot = robot;
    }
    
    /**
     * Sets robot pid constants in the X direction
     * @param p
     * @param i
     * @param d
     */
    public void setPIDX(double p, double i, double d) {
        rx.p = p;
        rx.i = i;
        rx.d = d;
    }
    
    /**
     * Sets robot pid constants in the Y direction
     * @param p
     * @param i
     * @param d
     */
    public void setPIDY(double p, double i, double d) {
        ry.p = p;
        ry.i = i;
        ry.d = d;
    }
    
    /**
     * Sets robot pid constants for the Z axis.
     * @param p
     * @param i
     * @param d
     */
    public void setPIDZ(double p, double i, double d) {
        rz.p = p;
        rz.i = i;
        rz.d = d;
    }
    
    public void setSetpointAndEnable(Point p) {
        if ( drive == null || in == null )
            throw new NullPointerException("Set Input and Outputs before this.");
        pidFunc.interupt();
        setpoint = p;
        driveType = DRIVE_SETPOINT;
        pidFunc = new PIDUpdateFunc(this);
        UpdateFunction.run(pidFunc);
    }
    
    public void setSetpointAndEnable(FieldNode[] path) {
    	if ( drive == null || in == null )
            throw new NullPointerException("Set Input and Outputs before this.");
    	pidFunc.interupt();
    	setPath = path;
    	
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
            System.out.println("WARNING: 2DPID controller was active when its drive type was changed to straight.");
        }
    }
    
    private class PIDC {
    	private double p, i, d;
    }
    
    
    /**
     * Custom PID controller that averages 
     * @author s-orthm
     *
     */
    private class Controller {
        private PIDC sin, cos;
        private double de, ie;  // last error, error accumulator
        private double set;     // setpoint
        long last;
        
        /**
         * 
         * @param sin parameters for the axis that is perpendicular to the robot, when the robot is in starting position.
         * @param cos parameters for the axis that is parallel to the robot, with the robot is in starting position.
         */
        private Controller(PIDC sin, PIDC cos) {
        	this.sin = sin;
        	this.cos = cos;
            de = 0;
            ie = 0;
        }
        private void set(double s) {
            set = s;
        }
        
        /**
         * Uses the trig sin^2+cos^2 = 1 to scale pid constants
         * @param curr
         * @return
         */
        private double calc(double curr) {
        	double s = Math.sin(robot.getHeading());
        	s = s*s;
        	double c = Math.cos(robot.getHeading());
        	c = c*c;
            long t = System.currentTimeMillis();
            long diff = t - last;
            last = t;
            ie += (set-curr)*diff;
            de = (de-curr+set)/(last-t);
            return (sin.p*s + cos.p*c)*(set - curr) + (sin.i*s + cos.i*c)*ie + (sin.d*s + cos.d*c)*de;
        }
        
    }
    
    private class PIDUpdateFunc extends UpdateFunction {
		private PIDUpdateFunc(PID2D instance) {
			super(()->{
		        if ( instance.straifDriving ) {
		        	// calculate new x, y, z world velocities from robot world coordinates.
		            double cx = instance.x.calc(instance.in.getX());
		            double cy = instance.y.calc(instance.in.getY());
		            double cz = instance.z.calc(instance.in.getZ());
		            // turns new pid x, y velocities into a new heading and converts it to robot coordinates.  
		            double[] rxy = instance.worldVecToRobot(cx, cy);
		            // convert robot heading from world to robot angle off the vertical
		            double rz = cz-instance.robot.getHeading();
		            // drives with the pid values converted to robot space.  
		            instance.drive.drive(rxy[0],rxy[1],rz);
		        }
		        else {
		            switch(instance.driveState) {	// TODO non straif drive pid
		                case 0:
		                    break;
		                case 1:
		                    break;
		                case 2:
		                    break;
		                default: 
		                	instance.driveState = 0; 
		                    break;
		            }
		        }
		        
		        boolean wt = instance.withinTolerance(instance.setpoint, new Point(instance.in.getX(), instance.in.getY(), instance.in.getZ()), false);
				if ( !wt )	// if not on setpoint keep going
					return false;
				else if ( instance.driveType == DRIVE_SETPOINT )	// if on setpoint and setpoint driving
					return true;
				else if ( instance.driveType == DRIVE_FIELD_PATH ) {	// if on setpoint and path driving( then set the next setpoint )
					if ( instance.pathIterator.hasNext() ) {	// if hasNext set next, else exit
						FieldNode node = instance.pathIterator.next();
						instance.setpoint = new Point(node.x, node.y);
						return true;
					}
					else
						return false;
				}
				else {	// unknown case exit.
					DriverStation.reportError("unknown PID continue case", false);
					return false;
				}
		    });
		}
		
		private boolean t() {
			boolean wt = withinTolerance(setpoint, new Point(in.getX(), in.getY(), in.getZ()), false);
			if ( !wt )
				return false;
			else if ( driveType == DRIVE_SETPOINT )
				return true;
			else if ( driveType == DRIVE_FIELD_PATH ) {
				if ( pathIterator.hasNext() ) {
					FieldNode node = pathIterator.next();
					setpoint = new Point(node.x, node.y);
					return true;
				}
				else
					return false;
			}
			else
				return false;
			
		}
		
		private boolean isStraif() {
			return straifDriving;
		}
    	
    }
    
}
