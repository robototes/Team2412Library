/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shsrobotics.library.scrpitedrobot.actions.math;

import com.shsrobotics.library.util.Stack;

/**
 *
 * @author Max
 */
public class TokenStack {
    Stack stack;
    
    public TokenStack() {
        stack = new Stack();
    }
    
    public Token pop() {
        return (Token)stack.pop();
    }
    
    public Token peek() {
        return (Token)stack.peek();
    }
    
    public void push(Token data) {
        stack.push(data);
    }
    
    public boolean isEmpty() {
        return stack.peek() == null;
    }
    
    public void clear() {
        stack.clear();
    }
    
}
