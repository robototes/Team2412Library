/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shsrobotics.library.scrpitedrobot.actions;

import com.shsrobotics.library.scrpitedrobot.Action;
import com.shsrobotics.library.scrpitedrobot.VirtualRobot;
import java.util.Stack;

/**
 * 
 * @author Max
 */
public class MathStackAction implements Action {
    private static Stack varStack = new Stack();
    private static Stack opStack = new Stack();
    
    public static final int START_NEW_STACK = 0;
    public static final int PUSH_VALUE = 1;
    public static final int EVALUEATE = 2;
    
    private final int type;
    private final Object number;
    private final Op op;
    /**
     * Make sure that the number passed as {@code number} is reassigned with
     * {@code new} before it is used again, since it is only remembered as a
     * reference in this class and changing it elsewhere will change it here too.
     * @param number
     * @param op
     * @param type 
     */
    public MathStackAction(Object number, MathStackAction.Op op, int type) {
        this.type = type;
        this.number = number;
        this.op = op;
    }
    
    public void performAction() {
        switch(type) {
            case START_NEW_STACK:
                MathStackAction.start(number);
                break;
            case PUSH_VALUE:
                MathStackAction.push(number, op);
                break;
            case EVALUEATE:
                evaluate();
                break;
            default:
                throw new Error("Invalid MathStackAction action type entered.");
        }
    }
    
    /**
     * Starts the MathStack with the specified number.
     * Clears the number stack and value stack before doing anything.
     * @param firstNumber The first number in this stack.
     */
    public static void start(Object firstNumber) {
        varStack.clear();
        opStack.clear();
        if ( !(firstNumber instanceof Float || firstNumber instanceof Integer ))
            throw new Error("Invalid data type was put in to a MathStackAction:" + firstNumber.getClass().getCanonicalName());
        varStack.push(firstNumber);
    }
    
    /**
     * Pushes {@code value} and {@code op} onto the stack.
     * @param value The number pushed onto the stack.
     * @param op The operation pushed onto the stack.
     */
    public static void push(Object value, MathStackAction.Op op) {
        if ( !(value instanceof Float || value instanceof Integer ))
            throw new Error("Invalid data type was put in to a MathStackAction:" + value.getClass().getCanonicalName());
        varStack.push(value);
        opStack.push(op);
    }
    
    /**
     * Evaluates the MathStack and returns the final value.
     * @return The value resulting from evaluating the MathStack.
     */
    public static Object evaluate() {
        eval();
        return varStack.peek();
    }
    
    public static void eval() {
        while ( !opStack.empty() ) {
            ((Op)opStack.pop()).execute();
        }
    }
    
    public abstract static class Op {
        private Op() {
        }
        
        abstract void execute();
        
        /**
         * Multiplies the top two numbers on the stack, popping them and pushing
         * the result on to the stack.
         */
        public final static Op MULT = new Op() {
            void execute() {
                Object v1 = varStack.pop();
                Object v2 = varStack.pop();
                Object res = null;
                
                if ( v1 instanceof Float ) {
                    if ( v2 instanceof Float ) {
                        res = new Float( ((Float)v1).floatValue() * ((Float)v2).floatValue() );
                    }
                    else {
                        res = new Float( ((Float)v1).floatValue() * ((Integer)v2).intValue() );
                    }
                }
                else if ( v1 instanceof Integer ) {
                    if ( v2 instanceof Float ) {
                        res = new Float( ((Float)v2).floatValue() * ((Integer)v1).intValue() );
                    }
                    else {
                        res = new Integer( ((Integer)v1).intValue() * ((Integer)v2).intValue() );
                    }
                }
                varStack.push(res);
            }
        };
        
        /**
         * Divides the second value popped by the first value popped, or the first
         * value pushed by the second value pushed, and pushes the result.
         * If the denominator is 0, it tells the VR to (virtually)throw an error.
         */
        public final static Op DIV = new Op() {
            void execute() {
                Object vd = varStack.pop();
                Object vn = varStack.pop();
                Object res = null;
                
                if ( vd instanceof Float ) {
                    if ( ((Float)vd).floatValue() == 0 ) VirtualRobot.throwError("Divide by Zero");
                    else if ( vn instanceof Float ) {
                        res = new Float( ((Float)vn).floatValue() / ((Float)vd).floatValue() );
                    }
                    else {
                        res = new Float( ((Integer)vn).intValue() / ((Float)vd).floatValue() );
                    }
                }
                else if ( vd instanceof Integer ) {
                    if ( ((Integer)vd).intValue() == 0 ) VirtualRobot.throwError("Divide by Zero");
                    else if ( vn instanceof Float ) {
                        res = new Float( ((Float)vn).floatValue() / ((Integer)vd).intValue() );
                    }
                    else {
                        res = new Integer( ((Integer)vn).intValue() / ((Integer)vd).intValue() );
                    }
                }
                
                varStack.push(res);
            }
            
        };
        
        /**
         * Pops the top two values off the stack, adds them, and pushes the result.
         */
        public final static Op ADD = new Op() {
            void execute() {
                Object v1 = varStack.pop();
                Object v2 = varStack.pop();
                Object res = null;
                
                if ( v1 instanceof Float ) {
                    if ( v2 instanceof Float ) {
                        res = new Float( ((Float)v2).floatValue() + ((Float)v1).floatValue() );
                    }
                    else {
                        res = new Float( ((Integer)v2).intValue() + ((Float)v1).floatValue() );
                    }
                }
                else if ( v1 instanceof Integer ) {
                    if ( v2 instanceof Float ) {
                        res = new Float( ((Float)v2).floatValue() + ((Integer)v1).intValue() );
                    }
                    else {
                        res = new Integer( ((Integer)v2).intValue() + ((Integer)v1).intValue() );
                    }
                }
                
                varStack.push(res);
            }
        };
        
        /**
         * Subtracts the second value popped from the first value popped,
         * or subtracts the first value pushed from the second value pushed,
         * and pushes the result.
         */
        public final static Op SUB = new Op() {
            void execute() {
                Object v2 = varStack.pop();
                Object v1 = varStack.pop();
                Object res = null;
                
                if ( v1 instanceof Float ) {
                    if ( v2 instanceof Float ) {
                        res = new Float( ((Float)v2).floatValue() - ((Float)v1).floatValue() );
                    }
                    else {
                        res = new Float( ((Integer)v2).intValue() - ((Float)v1).floatValue() );
                    }
                }
                else if ( v1 instanceof Integer ) {
                    if ( v2 instanceof Float ) {
                        res = new Float( ((Float)v2).floatValue() - ((Integer)v1).intValue() );
                    }
                    else {
                        res = new Float( ((Integer)v2).floatValue() - ((Integer)v1).intValue() );
                    }
                }
                
                varStack.push(res);
            }
        };
        
        /**
         * Performs modulus operation like such, with more type safety:
         * <code>
         * var v1 = varStack.pop();
         * var v2 = varStack.pop();
         * var res = v2 % v1;
         * push res;
         * </code>
         */
        public final static Op MOD = new Op() {
             void execute() {
                Object v1 = varStack.pop();
                Object v2 = varStack.pop();
                Object res = null;
                
                if ( v1 instanceof Float ) {
                    if ( v2 instanceof Float ) {
                        res = new Float( ((Float)v2).floatValue() % ((Float)v1).floatValue() );
                    }
                    else {
                        res = new Float( ((Integer)v2).intValue() % ((Float)v1).floatValue() );
                    }
                }
                else if ( v1 instanceof Integer ) {
                    if ( v2 instanceof Float ) {
                        res = new Float( ((Float)v2).floatValue() % ((Integer)v1).intValue() );
                    }
                    else {
                        res = new Integer( ((Integer)v2).intValue() % ((Integer)v1).intValue() );
                    }
                }
                
                varStack.push(res);
            }
        };
        
        // finish bitwise operators.
    }
    
}
