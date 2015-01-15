/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shsrobotics.library.scrpitedrobot.actions.math;

import com.shsrobotics.library.scrpitedrobot.actions.math.Number;
import java.util.Stack;
import java.util.EmptyStackException;

/**
 *
 * @author Max
 */
public class Evaluator {
    Stack stack;
    MathStack ms;
    
    private Evaluator(MathStack sms) {
        ms = sms;
        stack = new Stack();
    }
    
    public static Number evaluate(MathStack ms) {
        return new Evaluator(ms).evaluate();
    }
    
    private Number evaluate() {
        Number output;
        try {
            while (!ms.isQueueEmpty()) {
                Token current = ms.queueNext();
                if ( current instanceof Number )
                    stack.push(current);
                else
                    stack.push(current.evaluate((Number)stack.pop(), (Number)stack.pop()));
            }
            while ( stack.size() > 3 ) {
                Token poped = (Token)stack.pop();
                if ( poped instanceof Number )
                    throw new RuntimeException("Number was on top of the evaluation stack");
                
                stack.push(((Number)poped).evaluate((Number)stack.pop(), (Number)stack.pop()));
            }
            output = (Number)stack.pop();
        }
        catch (EmptyStackException ese) {
            output = null;
        }
        return output;
    }
    
}
