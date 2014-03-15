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
public class OurJaguar extends Jaguar implements NamedSendable {

    public OurJaguar(int channel) {
        super(channel);
    }
    
    public OurJaguar(final int slot, final int channel) {
        super(slot, channel);
    }

    public String getName() {
        return "Jaguar";
    }
    
    
    
}
