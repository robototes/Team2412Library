/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.shsrobotics.library.util.functions;

/**
 * A callback functional interface.  Use like this, for example:
 * <code>
 * Field f = new Field(2,2,2,2,Field.CONNECTIVITY_4);
 * FieldIterator fi = (FieldIterator)f.iterator();
 * fi.callback = (node)-> System.out.println(node.toString());
 * </code>
 * @author Max
 */
@FunctionalInterface
public interface Callback<T> {
    public void callback(T t);
}
