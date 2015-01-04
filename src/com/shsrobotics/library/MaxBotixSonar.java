/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shsrobotics.library;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.parsing.ISensor;

/**
 *
 * @author RoboTotes Team 2412
 */
public class MaxBotixSonar extends SensorBase implements ISensor {
	
	// supplied voltage = 4.978 ; 4.91 (b)
	
	public static final double DEFAULT_SCALING_FACTOR = 4.978 / 512;
	private double mVperInch = DEFAULT_SCALING_FACTOR;
	private AnalogChannel channel;
	
	public MaxBotixSonar(int channel) {
		this.channel = new AnalogChannel(channel);
	}
	
	public MaxBotixSonar(int slot, int channel) {
		this.channel = new AnalogChannel(slot, channel);
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
