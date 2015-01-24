/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.shsrobotics.library.fieldpositioning;

import java.util.HashMap;

/**
 *
 * @author Max
 */
public class FieldNode {
    
    public static FieldNode create(Point p, int connectivity) {
        FieldNode fn;
        if ( connectivity == Field.CONNECTIVITY_16 )
            fn = new FieldNode(p.x,p.y, 16);
        else if ( connectivity == Field.CONNECTIVITY_4 )
            fn = new FieldNode(p.x, p.y, 4);
        else    // defaults to 8
            fn = new FieldNode(p.x, p.y, 8);
        return fn;
    }
    
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
