/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shsrobotics.library.scrpitedrobot.actions.math;

import com.shsrobotics.library.util.Queue;
import com.shsrobotics.library.scrpitedrobot.actions.math.Number;

/**
 *
 * @author Max
 */
public class MathStack {
    
    private TokenStack stack;
    private Queue out;
    
    public MathStack() {
        stack = new TokenStack();
        out = new Queue();
    }
    
    public void reset() {
        stack.clear();
        out.clear();
    }
    
    public String getStringRepresentation() {
        return out.toString();
    }
    
    public void push(Token token) {
        if ( token instanceof PerenthesesClose ) {
            while ( !(stack.peek() instanceof PerenthesesOpen) )
                out.add(stack.pop());
            stack.pop();
        }
        else if ( token instanceof PerenthesesOpen ) {
            stack.push(token);
        }
        else if ( token instanceof Number ) {
            out.add(token);
        }
        else {
            fancyPush(token);
        }
    }
    
    private void fancyPush(Token t) {
        
        while ( !stack.isEmpty() && stack.peek().getPresedence() <= t.getPresedence() )
            out.add(stack.pop());
        stack.push(t);
        
    }
    
    public Token queueNext() {
        return (Token)out.remove();
    }
    
    public boolean isQueueEmpty() {
        return out.isEmpty();
    }
    
}
