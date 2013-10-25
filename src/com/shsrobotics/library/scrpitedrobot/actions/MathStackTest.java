/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shsrobotics.library.scrpitedrobot.actions;

/**
 *
 * @author Max
 */
public class MathStackTest {
    public static void main(String[] args) {
        /*
         *  Math being evaluated:
         *  (1 + 3) / 3 % 4
         */
        MathStackAction.start(new Float(1));
        MathStackAction.push(new Float(4), MathStackAction.Op.MULT);
        MathStackAction.push(new Float(3), MathStackAction.Op.DIV);
        MathStackAction.push(new Float(3), MathStackAction.Op.ADD);
        System.out.println(((Float)MathStackAction.evaluate()).floatValue());
        
        /*
         * Math evaluated:
         * 4 % 3 * 5
         */
        MathStackAction.start(new Integer(36));
        MathStackAction.push(new Integer(3), MathStackAction.Op.MOD);
        MathStackAction.push(new Integer(5), MathStackAction.Op.MULT);
        Integer res = (Integer)MathStackAction.evaluate();
        System.out.println(res.intValue());
        
        /*
         * 3 / 5 - 7 =>  0 - 7 + 3/5?
         */
        
        MathStackAction.start(new Integer(7));
        MathStackAction.push(new Float(3), MathStackAction.Op.SUB);
        MathStackAction.push(new Integer(5), MathStackAction.Op.DIV);
        System.out.println(((Float)MathStackAction.evaluate()).floatValue());
    }
}
