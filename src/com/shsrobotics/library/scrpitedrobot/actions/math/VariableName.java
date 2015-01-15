/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shsrobotics.library.scrpitedrobot.actions.math;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Max
 */
public class VariableName {
    
    static CharSequence saved;
    
    private static final String VARIABLE_NAME_REGEXP = "^[a-zA-Z][a-zA-Z0-9]*";
    private static final Pattern varregexp = Pattern.compile(VARIABLE_NAME_REGEXP);
    
    private VariableName() {
        
    }
    
    public static boolean matchAndSave(CharSequence seq) {
        Matcher m = varregexp.matcher(seq);
        
        if ( m.lookingAt() ) {
            saved = m.group();
            return true;
        }
        return false;
    }
    
}
