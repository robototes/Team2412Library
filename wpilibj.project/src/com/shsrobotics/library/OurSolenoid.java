/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj;

/**
 *
 * @author RoboTotes Team 2412
 */
public class OurSolenoid extends Solenoid implements NamedSendable {

    public OurSolenoid(int channel) {
        super(channel);
    }
    
    public OurSolenoid(int module, int channel) {
        super(module, channel);
    }

    public String getName() {
        return "Solenoid";
    }
    
}
