/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package orth.frc.fieldpositioning.function;

/**
 *
 * @author Max
 */
@FunctionalInterface
public interface Callback2<T,E> {
    public void callback(T t, E e);
}
