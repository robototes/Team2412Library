package com.shsrobotics.library;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author RoboTotes Team 2412
 */
public class ArduinoRGBLightController {
    
    private ArduinoRGBLightController () {
        
    }
    
    public static void setColor(double r, double g, double b) {
        SmartDashboard.putNumber("Arduino Red double", r);
        SmartDashboard.putNumber("Arduino Green double", g);
        SmartDashboard.putNumber("Arduino Blue double", b);
    }
    
    public static void setColor(boolean r, boolean g, boolean b) {
        SmartDashboard.putBoolean("Arduino Red boolean", r);
        SmartDashboard.putBoolean("Arduino Green boolean", g);
        SmartDashboard.putBoolean("Arduino Blue boolean", b);
    }
    
//    public static void setColor(Color color) {
//        
//    }
}
