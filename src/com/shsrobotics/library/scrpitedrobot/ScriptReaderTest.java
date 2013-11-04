/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shsrobotics.library.scrpitedrobot;

import java.io.FileInputStream;

/**
 *
 * @author Max
 */
public class ScriptReaderTest {
    public static void _main(String[] args) throws Exception {
        ScriptReader.setResourceInputStream(new FileInputStream(RobotConstants.SCRIPT_PATH + "\\robot.res"));
        ScriptReader.test();
    }
}
