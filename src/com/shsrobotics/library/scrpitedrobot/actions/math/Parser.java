/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shsrobotics.library.scrpitedrobot.actions.math;

import com.shsrobotics.library.scrpitedrobot.VirtualRobot;
import java.util.regex.Matcher;
import com.shsrobotics.library.scrpitedrobot.actions.math.Number;
import java.util.regex.Pattern;

/**
 *
 * @author Max
 */
public class Parser {
    
    
    public static MathStack parse(String expression) {
        expression = expression.replaceAll("\\s+", "");
        StringBuilder builder = new StringBuilder(expression);
        MathStack ms = new MathStack();
        Matcher m;
        Token previous = new Multiply();
        while ( builder.length() > 0 ) {
            Token current = null;
            if ( previous instanceof Number ) {
                if ( Subtract.getMatcher(builder).lookingAt() ) {
                    System.out.println();
                    current = new Subtract();
                    ms.push(current);
                    builder.deleteCharAt(0);
                    
                }
                else if ( Add.getMatcher(builder).lookingAt() ) {
                    current = new Add();
                    ms.push(current);
                    builder.deleteCharAt(0);
                }
                else if ( Multiply.getMatcher(builder).lookingAt() ) {
                    current = new Multiply();
                    ms.push(current);
                    builder.deleteCharAt(0);
                }
                else if ( Divide.getMatcher(builder).lookingAt() ) {
                    current = new Divide();
                    ms.push(current);
                    builder.deleteCharAt(0);
                }
                else if ( Exponent.getMatcher(builder).lookingAt() ) {
                    current = new Exponent();
                    ms.push(current);
                    builder.deleteCharAt(0);
                }
                else if ( PerenthesesClose.getMatcher(builder).lookingAt() ) {
                    // if pclose, set prev to a number
                    current = new Number(0);
                    ms.push(new PerenthesesClose());
                    builder.deleteCharAt(0);
                }
                else
                    throw new EvaluationException("Bad Expression 1: '" + builder.toString() + "'");
            }
            else {
                if ( PerenthesesOpen.getMatcher(builder).lookingAt() ) {
                    current = new PerenthesesOpen();
                    ms.push(current);
                    builder.deleteCharAt(0);
                }
                else if ( Number.matchDoubleAndSave(builder) ) {
                    current = new Number(Number.getMatchedDouble());
                    ms.push(current);
                    builder.delete(0, Number.getMatchedDoubleLength());
                }
                else if ( Number.matchIntAndSave(builder) ) {
                    current = new Number(Number.getMatchedInt());
                    ms.push(current);
                    builder.delete(0, Number.getMatchedIntLength());
                }
                else if ( VariableName.matchAndSave(builder) ) {
                    current = new Number((java.lang.Number)VirtualRobot.getLocalVariable(VariableName.saved.toString()));
                }
                else
                    throw new EvaluationException("Bad Expression 2");
            }
            
            previous = current;
        }
        
        ms.push(new Terminate());
        
        return ms;
    }
    
}
