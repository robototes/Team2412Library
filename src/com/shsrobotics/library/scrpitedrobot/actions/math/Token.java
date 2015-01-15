package com.shsrobotics.library.scrpitedrobot.actions.math;

import java.util.regex.Matcher;
import com.shsrobotics.library.scrpitedrobot.actions.math.Number;

/**
 * 
 * @author Max
 */
public abstract class Token {
    
    
    /*
    
    NUMBER(-1, "^[-]?[0-9]*[.]?[0-9]*"),
    TERMINATE(-3, "^$"),
    POPEN(curr(), "^[(]"),
    PCLOSE(-2, "^[)]"),
    EXP(next(), "^[^]"),
    MULT(next(), "^[*]"),
    DIV(curr(), "^[/]"),
    ADD(next(), "^[+]"),
    SUB(curr(), "^[-]");
    */
    
    public static final int
            EXP = next(),
            MULT = next(),
            DIV = curr(),
            SUB = next(),
            ADD = curr(),
            NUMBER = next(),
            PCLOSE = next(),
            POPEN = next(),
            TERMINATE = next();
    
    private static int currPrecedence = 0;
    
    private static int next() {
        currPrecedence++;
        return currPrecedence;
    }
    
    private static int next2() {
        return currPrecedence--;
    }
    
    private static int curr() {
        return currPrecedence;
    }
    
    
    public int getPresedence() {
        throw new UnsupportedOperationException();
    }
    
    public static Matcher getMatcher(CharSequence seq) {
        throw new UnsupportedOperationException();
    }
    
    public abstract Number evaluate(Number top, Number underTop);
    
}
