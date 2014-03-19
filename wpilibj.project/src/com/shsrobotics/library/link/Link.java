package com.shsrobotics.library.link;

/**
 * A link between an control input and a hardware output.
 * @author Team 2412 <first.robototes.com, github.com/robototes>
 */
public class Link {
	private final ContinuousInput inputC;
	private final DiscreteInput inputD;
	private final Output output;
	
	private boolean continuous = false;
	
	public final int priority;
	
	/**
	 * Create a link.
	 * @param input a control input object.
	 * @param output a hardware output object.
	 * @param priority the lower, the more important that these values take precedence.
	 */
	public Link(Input input, Output output, int priority) {
		continuous = input instanceof ContinuousInput;
		
		this.inputC = (ContinuousInput) (continuous ? input : null);
		this.inputD = (DiscreteInput) (continuous ? null : input);
		this.output = output;
		
		this.priority = priority;
	}
	
	/**
	 * Get whether value should be sent.
	 * @return true if value should be sent/updated.
	 */
	public boolean get() {
		boolean yes;
		
		if (continuous) {
			yes = inputC.get() != 0; 
		} else { // discrete
			yes = inputD.get();
		}
		
		return yes;
	}
	
	/**
	 * Copy input values to output.
	 */
	public void link() {
		if (continuous) {
			output.set(inputC.get());
		} else { // discrete
			output.set(inputD.get());
		}
	}
}
