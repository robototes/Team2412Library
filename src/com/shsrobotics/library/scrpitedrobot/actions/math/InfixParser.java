/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shsrobotics.library.scrpitedrobot.actions.math;

import com.shsrobotics.library.scrpitedrobot.actions.math.Number;



/**
 *
 * @author Max
 */
public class InfixParser {
    public static Number parse(String arg, StringWrapper post) {
        Number res = null;
        try {
            MathStack ms = Parser.parse(arg);
            post.str = ms.getStringRepresentation();
            res = Evaluator.evaluate(ms);
        }
        catch (EvaluationException e) {
            e.printStackTrace();
        }
        return res;
    }
    
    public static Number parse(String arg) {
        Number res = null;
        StringWrapper post = new StringWrapper("");
        try {
            MathStack ms = Parser.parse(arg);
            post.str = ms.getStringRepresentation();
            res = Evaluator.evaluate(ms);
        }
        catch (EvaluationException e) {
            e.printStackTrace();
        }
        return res;
    }
    
}
