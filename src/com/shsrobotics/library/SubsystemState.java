package com.shsrobotics.library;

public enum SubsystemState {
	ESTOP("estop"), RUNNING("running"), IDLE("idle"), ERROR("error"), DISABLED("disabled");
	
	private String name;
	
	private SubsystemState(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
	
}
