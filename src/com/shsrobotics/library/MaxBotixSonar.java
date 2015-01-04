/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shsrobotics.library;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.SensorBase;

/**
 *
 * @author RoboTotes Team 2412
 */
public class MaxBotixSonar extends SensorBase {
	
	// supplied voltage = 4.978 ; 4.91 (b)
	
	public static final double DEFAULT_SCALING_FACTOR = 4.978 / 512;
	private double mVperInch = DEFAULT_SCALING_FACTOR;
	private AnalogInput channel;
	
	public MaxBotixSonar(int channel) {
		this.channel = new AnalogInput(channel);
	}

	
	public double getDistanceInFeet() {
		double measuredVoltage = channel.getVoltage();
        double range = (measuredVoltage / mVperInch) / 12.0; // feet
		return range; 
	}
    
    public double getDistance() {
		double measuredVoltage = channel.getVoltage();
        double range = (measuredVoltage / mVperInch); // inches
		return range; 
	}
    
    public double getRawVoltage() {
        return channel.getVoltage();
    }
	
	public void setSensitivity(double scale) {
		mVperInch = scale;
	}
	
	public void free() {
		channel.free();
	}
}
