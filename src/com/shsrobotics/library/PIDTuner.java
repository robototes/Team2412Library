package com.shsrobotics.library;

import java.util.Scanner;

/**
 * Tunes a PID controller.
 * @author Cory McCartan
 */
public class PIDTuner {

	/**
	 * Tune the PID loop.
	 */
	public static void run() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Porportional coefficient at maximum gain: ");
		double P = scanner.nextDouble();
		System.out.print("Period of oscillation, in seconds: ");
		double T = scanner.nextDouble();
		
		P /= 5;
		double I = 2 * P / T;
		double D = P * T / 3;
		
		System.out.println();
		System.out.println();
		System.out.println("====CALCULATED PARAMETERS===");
		System.out.println("  P = " +  P);
		System.out.println("  I = " +  I);
		System.out.println("  D = " +  D);
		System.out.println("============================");
	}
}
