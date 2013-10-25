package com.shsrobotics.library.scrpitedrobot;

import com.shsrobotics.library.util.File;
import static com.shsrobotics.library.scrpitedrobot.ScriptReader.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import sun.util.BuddhistCalendar;

/**
 *
 * @author Max
 */
public class ScriptReader {
    
    private static void readResources(String path) throws FileNotFoundException {
        File file = new File(path+".res");
        InputStreamReader isr = new InputStreamReader(file.getInputStream());
        java.io.BufferedReader br = new BufferedReader(isr);
        int c;
        try {
            String line;
            while ( ((line = br.readLine()) != null )) { // c != EOF or end of transmission
                if ( line.charAt(0) == '#' ) {
                    String rtype = line.substring(1);
                    
                }
            }
        }
        catch (IOException ioe) {
            
        }
    }
    
    private static ActionList readActionList(String path) throws FileNotFoundException {
        ActionList alist = null;
        File file = new File(path+".act");
        InputStreamReader isr = new InputStreamReader(file.getInputStream());
        
        
        return alist;
    }

    
}
