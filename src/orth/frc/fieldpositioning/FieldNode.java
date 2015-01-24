/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package orth.frc.fieldpositioning;

import java.util.HashMap;

/**
 *
 * @author Max
 */
public class FieldNode {
    /**
     * In clockwise order
     */
    protected FieldNode[] connections;
    protected MovableFieldObject occupied;
    protected double x, y;
    protected int visitIndex = 0;
    protected FieldNode pathFrom = null;
    /**
     * @param x X coordinate on the field.
     * @param y Y coordinate on the field.
     * @param numConnections length of the connection array.  
     */
    protected FieldNode(double x, double y, int numConnections) {
        this.x = x;
        this.y = y;
        connections = new FieldNode[numConnections];
    }
    
    /**
     * 
     * @param from
     * @param to
     * @param i index in the array for the correct direction.
     */
    protected static void setConnection(FieldNode from, FieldNode to, int i) {
        from.connections[i] = to;
    }
    
}
