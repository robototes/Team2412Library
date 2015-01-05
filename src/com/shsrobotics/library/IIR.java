package com.shsrobotics.library;

import edu.wpi.first.wpilibj.Joystick;

/**
 * IIR filters joystick values and smooths driving.
 *
 * @author RoboTotes Team 2412
 */
public class IIR extends Joystick {

    private double smoothingX;
    private double smoothingY;
    private double smoothingZ;

    private double accumulatorX = 0;
    private double accumulatorY = 0;
    private double accumulatorZ = 0;

    public IIR(int joystick) {
        super(joystick);
        setSmoothing(new Smoothing(20, 20, 20));
    }

    public IIR(int joystick, Smoothing smoothing) {
        super(joystick);
        setSmoothing(smoothing);
    }

    public void setSmoothing(Smoothing smoothing) {
        smoothingX = smoothing.x;
        smoothingY = smoothing.y;
        smoothingZ = smoothing.z;
    }

    public double outputX() {
        double X = this.getX();
        if (X < accumulatorX) {
            accumulatorX = (accumulatorX * smoothingX + X) / (1 + smoothingX);
        } else {
            accumulatorX = X;
        }
        return accumulatorX;
    }

    public double outputZ() {
        double Z = this.getZ();
        if (Z < accumulatorZ) {
            accumulatorZ = (accumulatorZ * smoothingZ + Z) / (1 + smoothingZ);
        } else {
            accumulatorZ = Z;
        }
        return accumulatorZ;
    }

    public double outputY() {
        double Y = this.getY();
        if (Y < accumulatorY) {
            accumulatorY = (accumulatorY * smoothingY + Y) / (1 + smoothingY);
        } else {
            accumulatorY = Y;
        }
        return accumulatorY;
    }

    public static class Smoothing {

        public double x;
        public double y;
        public double z;

        public Smoothing(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

}