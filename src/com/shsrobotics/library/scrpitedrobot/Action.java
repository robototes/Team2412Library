package com.shsrobotics.library.scrpitedrobot;

/**
 * An action is anything that the {@link VirtualRobot} can do, including
 * controlling the robot and pushing variables into the variable list.
 * To add actions, one should implement this interface and add corresponding
 * script conversions to the {@code ScriptReader }.
 * 
 * An action can have any number of constructors, as the only thing that the VR cares about is the {@code performAction() } method.
 * @author Max
 */
public interface Action {
    public void performAction();
}
