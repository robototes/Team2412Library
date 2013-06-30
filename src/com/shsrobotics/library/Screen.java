package com.shsrobotics.library;


import edu.wpi.first.wpilibj.DriverStationLCD;

/** 
 * Shorthand variables for lines and columns of Driver Station Screen.
 */
public class Screen {
	// lines on the driver station, each 21 char. in length, from top to bottom.
	public static final DriverStationLCD.Line line1 = DriverStationLCD.Line.kUser1;
	public static final DriverStationLCD.Line line2 = DriverStationLCD.Line.kUser2;
	public static final DriverStationLCD.Line line3 = DriverStationLCD.Line.kUser3;
	public static final DriverStationLCD.Line line4 = DriverStationLCD.Line.kUser4;
	public static final DriverStationLCD.Line line5 = DriverStationLCD.Line.kUser5;
	public static final DriverStationLCD.Line line6 = DriverStationLCD.Line.kUser6;

	// each column represents one tab character
	public static final int column1 = 1;
	public static final int column2 = 5;
	public static final int column3 = 9;
	public static final int column4 = 13;
}