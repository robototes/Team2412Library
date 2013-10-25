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
    }
}
