package com.shsrobotics.library;

/**
 * More flexible PID setup.
 * @author Team 2412 <first.robototes.com, github.com/robototes>
 */
public class PIDController extends edu.wpi.first.wpilibj.PIDController {
	/**
	 * Create a PID controller.
	 * @param P the proportional gain.
	 * @param I the integral gain.
	 * @param D the derivative gain.
	 * @param frequency the frequency to take measurements (Hertz).
	 * @param hardware the hardware object, which holds the read and write methods.
	 */
	public PIDController(double P, double I, double D, double frequency, PIDHardware hardware) {
		super(P, I, D, hardware.source, hardware.output, 1 / frequency);
	}
	
	/**
	 * Create a PID controller.
	 * @param P the proportional gain.
	 * @param I the integral gain.
	 * @param D the derivative gain.
	 * @param hardware the hardware object, which holds the read and write methods.
	 */
	public PIDController(double P, double I, double D, PIDHardware hardware) {
		super(P, I, D, hardware.source, hardware.output, 0.05);
	}
}
