package com.shsrobotics.library.scrpitedrobot;

import com.shsrobotics.library.util.File;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import edu.wpi.first.wpilibj.Relay;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.StringTokenizer;

/**
 * regex used:
 * [a-zA-Z][a-zA-Z0-9]*     variable name
 * [\s]*?[a-zA-Z][a-zA-Z0-9]*[\s]*?[=][\s]*?[=][0-9.]*    varName = number
 * 
 * 
 * @author Max
 */
public class ScriptReader {
    
    private static InputStream ris;
    private static InputStream sis;
    
    public static Pattern VAR_EQUALS_VALUE = Pattern.compile("[\\s]*?[a-zA-Z][a-zA-Z0-9]*[\\s]*?[=][\\s]*?[=][0-9.]*[\\s]*"); // anchor the line
    
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
    protected static void throwCompileError(String msg, int line) {
        try {
            VirtualRobot.getOutputStream().write((msg + "\n").getBytes());
        }
        catch (IOException ioe) {
            throw new Error(ioe);
        }
    }
    
    //initializes the hardware.
    private static void readResources(String path) throws FileNotFoundException, IOException {
        InputStreamReader isr;
        if ( ris == null ) {
            File file = new File(path + "robot.res");
            isr = new InputStreamReader(file.getInputStream());
        }
        else
            isr = new InputStreamReader(ris);
        
        BufferedReader br = new BufferedReader(isr);
            String line;
            int lineNum = 0;
            while ( ((line = br.readLine()) != null )) { // c != EOF or end of transmission
                lineNum++;
                if ( line.length() > 0 && line.charAt(0) == '#' ) {
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
    
    // compiles the script to an ActionList.
    private static ActionList readScript(String path) throws FileNotFoundException, IOException {
        InputStreamReader isr;
        if ( ris == null ) {
            File file = new File(path + "robot.scr");
            isr = new InputStreamReader(file.getInputStream());
        }
        else
            isr = new InputStreamReader(sis);
        
        BufferedReader br = new BufferedReader(isr);
        
        ActionList alist = new ActionList();
        // this is manipulated if parentheses are used in math expressions, so we use a StringBuilder ?
        int line = 0;
        String cline;
        Matcher matcher;
        while ( ( cline = br.readLine()) != null ) {
            line++;
            matcher = VAR_EQUALS_VALUE.matcher(cline);
            if ( matcher.matches() ) {
                StringTokenizer st = new StringTokenizer(cline, "=");
                if (st.countTokens() == 2 ) {
                    String varName = st.nextToken().trim();
                    String value = st.nextToken().trim();
                    if ( VirtualRobot.hasLocalVariable(varName) ) {
                        VirtualRobot.setLocalVariable(varName, ScriptUtil.parseNumber(value, line));
                    }
                }
            }
            else {
                
            }
        }
        
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
