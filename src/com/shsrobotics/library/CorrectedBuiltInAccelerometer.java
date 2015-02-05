package com.shsrobotics.library;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;

public class CorrectedBuildInAccelerometer extends BuiltInAccelerometer {
	
	public static final double BUILT_IN_2015_X_CORRECTION = -.0256351;
	public static final double BUILT_IN_2015_Y_CORRECTION = .012205527;
	
	private double xcorrection = 0;
	private double ycorrection = 0;
	
	public static BuiltInAccelerometer getCorrected2015() {
		CorrectedBuildInAccelerometer cba = new CorrectedBuildInAccelerometer();
		cba.setDriftCorrection(BUILT_IN_2015_X_CORRECTION, BUILT_IN_2015_Y_CORRECTION);
		return cba;
	}
	
	public void setDriftCorrection(double x, double y) {
		xcorrection = x;
		ycorrection = y;
	}
	
	@Override
	public double getX() {
		return super.getX() - xcorrection;
	}
	
	@Override
	public double getY() {
		return super.getY() - ycorrection;
	}
	
	public double getXFeet() {
		return getX() * 32.185;
	}
	
	public double getYFeet() {
		return getY() * 32.185;
	}
}
