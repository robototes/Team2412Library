package com.shsrobotics.library.scrpitedrobot;

import com.shsrobotics.library.util.File;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import edu.wpi.first.wpilibj.Relay;
import java.util.StringTokenizer;

/**
 *
 * @author Max
 */
public class ScriptReader {
    
    private static InputStream ris;
    private static InputStream sis;
    
    /**
     * Sets the input stream for the resource file.
     * @param instream should be a stream to a file. If not, an error will be thrown in the VR when the script is compiled.
     */
    public static void setResourceInputStream(InputStream instream) {
        if ( instream != null )
            ris = instream;
    }
    
    /**
     * Sets the input stream for the script file.
     * @param instream should be a stream to a file. If not, an error will be thrown in the VR when the script is compiled.
     */
    public static void setScriptInputStream(InputStream instream) {
        if ( instream != null )
            sis = instream;
    }
    
    // throw a compile error
    private static void throwCompileError(String msg, int line) {
        try {
            VirtualRobot.getOutputStream().write((msg + "\n").getBytes());
        }
        catch (IOException ioe) {
            throw new Error(ioe);
        }
    }
    
    //initializes the hardware.
    private static void readResources(String path) throws FileNotFoundException {
        InputStreamReader isr;
        if ( ris == null ) {
            File file = new File(path + "robot.res");
            isr = new InputStreamReader(file.getInputStream());
        }
        else
            isr = new InputStreamReader(ris);
        
        BufferedReader br = new BufferedReader(isr);
        try {
            String line;
            int lineNum = 0;
            while ( ((line = br.readLine()) != null && line.length() > 0)) { // c != EOF or end of transmission
                lineNum++;
                if ( line.charAt(0) == '#' ) {
                    String rtype = line.substring(1);
                    rtype = rtype.trim();
                    StringTokenizer st = new StringTokenizer(rtype, " ");
                    if ( st.countTokens() > 3 || st.countTokens() < 2 )
                        throwCompileError("Bad hardware definition.", lineNum);
                    else {
                        String[] tokens = new String[3];
                        tokens[0] = st.nextToken();
                        tokens[1] = st.nextToken();
                        if ( st.hasMoreTokens() )
                            tokens[2] = st.nextToken();
                        else
                            tokens[2] = "0";
                        Object o;
                        if ( tokens[0].equals("FLOAT") ) {
                            // <editor-fold defaultstate="collapsed" desc="float">
                            float v = 0;
                            try {
                                v = Float.parseFloat(tokens[2]);
                            }
                            catch (NumberFormatException nfe) {
                                throwCompileError("Non-numerical value assigned to float: " + tokens[2] + ", defaulting to 0.", lineNum);
                            }
                            VirtualRobot.initHardware(new Float(v), tokens[1]);
                            // </editor-fold>
                        }
                        else if ( tokens[0].equals("INT") ) {
                            // <editor-fold defaultstate="collapsed" desc="int">
                            int v = 0;
                            try {
                                v = Integer.parseInt(tokens[2]);
                            }
                            catch (NumberFormatException nfe) {
                                throwCompileError("Non-numerical value assigned to int: " + tokens[2] + ", defaulting to 0.", lineNum);
                            }
                            VirtualRobot.initHardware(new Integer(v), tokens[1]);
                            // </editor-fold>
                        }
                        else if ( tokens[0].equals("VAR") ) {
                            // <editor-fold defaultstate="collapsed" desc="var">
                            int vi = 0;
                            double vd = 0;
                            try {
                                vi = Integer.parseInt(tokens[2]);
                                VirtualRobot.initHardware(new Integer(vi), tokens[1]);
                            }
                            catch (NumberFormatException nfe) {
                                try {
                                    vd = Double.parseDouble(tokens[2]);
                                    VirtualRobot.initHardware(new Double(vd), tokens[1]);
                                }
                                catch(NumberFormatException nfe2) {
                                    throwCompileError("Non-numerical value assigned to VAR: " + tokens[2] + ", defaulting to 0.", lineNum);
                                }
                            }
                            // </editor-fold>
                        }
                        else if ( tokens[0].equals("RELAY")) {
                            // <editor-fold defaultstate="collapsed" desc="relay">
                            try {
                                o = new Relay(Integer.parseInt(tokens[2]));
                                VirtualRobot.initHardware(o, tokens[1]);
                            }
                            catch ( NumberFormatException nfe) {
                                throwCompileError("Invalid port number: " + tokens[2] + ",\nSkipping to next defintion.", lineNum);
                                o = new VirtualSensor();
                                VirtualRobot.initHardware(o, tokens[1]);
                            }
                            // </editor-fold>
                        }
                        else
                            throwCompileError("Invalid hardware type: " + tokens[0], lineNum);
                    }
                }
            }
            if ( lineNum == 0 ) {
                throwCompileError("No hardware defintions.", 0);
            }
        }
        catch (IOException ioe) {
            throw new Error(ioe);
        }
    }
    
    // compiles the script to an ActionList.
    private static ActionList readActionList(String path) throws FileNotFoundException {
        InputStreamReader isr;
        if ( ris == null ) {
            File file = new File(path + "robot.res");
            isr = new InputStreamReader(file.getInputStream());
        }
        else
            isr = new InputStreamReader(ris);
        ActionList alist = new ActionList();
        
        return alist;
    }
    
    /**
     * Tests whatever is within the test() function.
     * @throws Exception 
     */
    public static void test() throws Exception {
        readResources(RobotConstants.SCRIPT_PATH);
    }
}
