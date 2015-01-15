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
public interface RCallback<T> {
    public T callback();
}
