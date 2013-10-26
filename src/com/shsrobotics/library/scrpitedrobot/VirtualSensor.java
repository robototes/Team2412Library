/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shsrobotics.library.scrpitedrobot;

/**
 * When an attempted registration of hardware with an invalid port number
 * occurs, this class is registered instead of the intended sensor.
 * @author Max
 */
public class VirtualSensor {

    public VirtualSensor() {
        VirtualRobot.throwMessage("A virtual sensor was used to replace an attempted registration of an invalid sensor.");
    }
    
}
