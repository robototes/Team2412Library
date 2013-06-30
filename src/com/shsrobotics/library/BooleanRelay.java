package com.shsrobotics.library;

import edu.wpi.first.wpilibj.Relay;

/**
 * Relay that operates using true/false values.
 * @author Cory McCartan
 */
public class BooleanRelay extends Relay {
	/**
     * Relay constructor given the module and the channel.
     * @param moduleNumber The number of the digital module to use.
     * @param channel The channel number within the module for this relay.
     */
    public BooleanRelay(final int moduleNumber, final int channel) {
       super(moduleNumber, channel);
    }

	/**
     * Relay constructor given the channel, assuming the default module.
     * @param channel The channel number within the module for this relay.
     */
    public BooleanRelay(final int channel) {
        super(channel);
    }
	
	/**
	 * Get the state of the relay.
	 * @return {@code true} if {@code Relay.Value.kForward}, {@code false} if {@code Relay.Value.kOff}.  {@code Relay.Value.kReverse} also returns  {@code false}.
	 */
	public boolean getState() {
		return (super.get() == BooleanRelay.Value.kForward);
	}
	
	/**
	 * Set the relay.
	 * @param on whether to set the relay to be on or off.
	 */
	public void set(boolean on) {
		super.set(on ? Relay.Value.kForward : Relay.Value.kOff);
	}

}
