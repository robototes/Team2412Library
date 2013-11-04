/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shsrobotics.library.scrpitedrobot.actions;

import com.shsrobotics.library.scrpitedrobot.Action;
import com.shsrobotics.library.scrpitedrobot.VirtualRobot;

/**
 *
 * @author Max
 */
public class RegisterFloatAction implements Action {
        String name;
        float value;
        public RegisterFloatAction(String name) {
            this.name = name;
            value = 0;
        }
        
        public RegisterFloatAction(String name, float var) {
            value = var;
        }
        
        public void performAction() {
            VirtualRobot.putVariable(name, value);
        }
    }
