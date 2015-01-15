
package com.shsrobotics.library.scrpitedrobot.actions.math;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.shsrobotics.library.scrpitedrobot.actions.math.Token;
import com.shsrobotics.library.scrpitedrobot.actions.math.Number;

/**
 *
 * @author Max
 */
public class Exponent extends Token {
    
    static Pattern pattern = Pattern.compile("^[\\^]");
    static int presedence = Token.EXP;
    
    public int getPresedence() {
        return presedence;
    }
    
    public static Matcher getMatcher(CharSequence seq) {
        return pattern.matcher(seq);
    }
    
    public String toString() {
        return "^";
    }
    
    public Exponent() {
    }
    
    public Number evaluate(Number top, Number underTop) {
        return new Number(Math.pow(underTop.n.doubleValue(), top.n.doubleValue()));
    }
}
