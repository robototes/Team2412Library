/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.shsrobotics.library.fieldpositioning;

import java.util.Iterator;

/**
 *
 * @author Max
 */
public class Field implements Iterable<FieldNode>{
    //<editor-fold defaultstate="collapsed" desc="static variables">
    /**
     * Tells the edge finder to circle the Field in a clockwise direction.
     */
    public static final int EDGE_DIR_CLK = 1;
    /**
     * Tells the edge finder to circle the Field in a counter-clockwise direction.
     */
    public static final int EDGE_DIR_NCLK = 2;
    
    /**
     * It repeats the first so that you can check 15-0
     */
    private static final int[] CON_16_CLK = {0, 8, 4, 9, 1, 10, 5, 11, 2, 12, 6, 13, 3, 14, 7, 15 };
    /**
     * It repeats the first so that you can check 7-0
     */
    private static final int[] CON_8_CLK = { 0, 4, 1, 5, 2, 6, 3, 7 };
    /**
     * It repeats the first so that you can check 3-0
     */
    private static final int[] CON_4_CLK = {0, 1, 2, 3 };
    
    /**
     * Connects nodes in the 4 cardinal directions.
     */
    public static final int CONNECTIVITY_4 = 1;
    /**
     * Connects nodes in the 4 cardinal directions, and 
     * the 4 diagonal directions.
     */
    public static final int CONNECTIVITY_8 = 3;
    /**
     * Connects nodes in the 4 cardinal directions, the 4 diagonal directions,
     * and 8 in-between directions.
     */
    public static final int CONNECTIVITY_16 = 7;
    /**
     * Base level is at y=0.  
     */
    public static final int ORIENT_BASE_0Y = 1;
    /**
     * Base level is at max y of the field.  Opposite of ORIENT_BASE_0Y
     */
    public static final int ORIENT_BASE_MAXY = 2;
    /**
     * Orients the base on the left, or where x=0.  
     * Opposite of ORIENT_LEG_RIGHT
     */
    public static final int ORIENT_LEG_LEFT = 4;
    /**
     * Orients the base on the right, or on the side where x is greatest.  
     * Opposite of ORIENT_LEG_LEFT
     */
    public static final int ORIENT_LEG_RIGHT = 8;
    
    //</editor-fold>
    
    public final double x, y;
    public final int xDiv, yDiv;
    private int searchCount;

    protected FieldNode[][] nodes;
    
    /**
     * Creates a right triangle with the leg on the x=0 side and 
     * base at y=0 side.  Use Field.rotate(angle) to rotate the field after this
     * to orient it properly.
     * @throws UnsupportedOperationException Don't use this.
     * @param base
     * @param leg
     * @param dbase
     * @param dleg
     * @param connectivity
     * @return 
     */
    public static Field rightTriangle(double base, double leg, int dbase, int dleg, int connectivity) {
        throw new UnsupportedOperationException();
        /*
        Field res = new Field(base, leg, dbase, dleg, connectivity);
         
        if ( baseOrient == ORIENT_BASE_MAXY )
        
        
        return res;
        */
    }
    
    /**
     * 
     * Only have one of these active at a time otherwise their action will overlap. 
     * They create an internal list of nodes on construction.  
     * This is not thread safe.  
     * @return 
     */
    @Override
    public Iterator<FieldNode> iterator() {
        return new FieldNodeIterator(this, ++searchCount);
    }
    
    public Iterator<FieldNode> iterator(int i, int j) {
        if ( nodes[i][j] != null ) {
            return new FieldNodeIterator(nodes[i][j], ++searchCount);
        }
        return new FieldNodeIterator(this, ++searchCount);
    } 
    
    protected FieldNode getNonNull() {
        FieldNode n = nodes[0][0];
        
        for ( int i = 0; i < nodes.length && n != null; i++ ) {
            for ( int j = 0; j < nodes[i].length && n != null; j++ ) {
                n = nodes[i][j];
            }
        }
        
        return n;
    }
    
    /**
     * Creates a 2d graph that represents the field.
     * Note that this may take a while to initialize since this creates the backing graph.
     * @param x actual width
     * @param y actual length
     * @param dx number of divisions, or nodes, in the graph widthwise
     * @param dy number of divisions lengthwise
     * @param connectivity How connected it is to nodes around it.  see {@link Field.CONNECTIVITY_4}
     */
    public Field( double x, double y, int dx, int dy, int connectivity) {
        
        
        int con;
        if ( connectivity == Field.CONNECTIVITY_16 )
            con = 16;
        else if ( connectivity == Field.CONNECTIVITY_4 )
            con = 4;
        else    // defaults to 8
            con = 8;
        
        this.x = x;
        this.y = y;
        this.xDiv = dx;
        this.yDiv = dy;
        
        double sx = x / xDiv;
        double sxd2 = sx/2;
        double sy = y / yDiv;
        double syd2 = sy/2;
        
        nodes = new FieldNode[xDiv][yDiv];
        for ( int i = 0; i < nodes.length; i++ ) {
            for ( int j = 0; j < nodes[i].length; i++ ) {
                nodes[i][j] = new FieldNode(sx * i + sxd2, sy * j + syd2, con);
            }
        }
        unify(connectivity);
    }
    
    
    private void unify(int connectivity) {
        int nm1 = nodes.length-1;
        switch(connectivity) {
            case CONNECTIVITY_16:
                int nm2 = nm1-1;
                for ( int i = 0; i < nodes.length; i++ ) {
                    for ( int j = 0; j < nodes.length; i++ ) {
                        FieldNode current = nodes[i][j];
                        if ( current != null ) {
                            // right half
                            if ( j < nodes[i].length-2 && i < nm1 && nodes[i+1][j+2] != null) FieldNode.setConnection(current, nodes[i+1][j+2], 8);
                            if ( i < nm2 && j < nodes[i].length-1 && nodes[i+2][j+1] != null) FieldNode.setConnection(current, nodes[i+2][j+1], 9);
                            if ( j > 0 && i < nm2 && nodes[i+2][j-1] != null) FieldNode.setConnection(current, nodes[i+2][j-1], 10);
                            if ( i < nm1 && j > 1 && nodes[i+1][j-2] != null) FieldNode.setConnection(current, nodes[i+1][j-2], 11);
                            // left half
                            if ( j > 1 && i > 0 && nodes[i-1][j-2] != null) FieldNode.setConnection(current, nodes[i-1][j-2], 12);
                            if ( i > 1 && j > 0 && nodes[i-2][j-1] != null) FieldNode.setConnection(current, nodes[i-2][j-1], 13);
                            if ( j < nodes[i].length-1 && i > 1 && nodes[i-2][j+1] != null) FieldNode.setConnection(current, nodes[i-2][j+1], 14);
                            if ( i > 0 && j < nodes[i].length-2 && nodes[i-1][j+2] != null) FieldNode.setConnection(current, nodes[i-1][j+2], 15);
                        }
                    }
                }
            case CONNECTIVITY_8:
                
                for ( int i = 0; i < nodes.length; i++ ) {
                    for ( int j = 0; j < nodes.length; i++ ) {
                        FieldNode current = nodes[i][j];
                        if ( j < nodes[i].length-1 && i < nm1 && nodes[i+1][j+1] != null) FieldNode.setConnection(current, nodes[i+1][j+1], 4);
                        if ( i < nm1 && j > 0 && nodes[i+1][j-1] != null) FieldNode.setConnection(current, nodes[i+1][j-1], 5);
                        if ( j > 0 && i > 0 && nodes[i-1][j-1] != null) FieldNode.setConnection(current, nodes[i-1][j-1], 6);
                        if ( i > 0 && j < nodes[i].length-1 && nodes[i-1][j+1] != null) FieldNode.setConnection(current, nodes[i-1][j+1], 7);
                    }
                }
                
            case CONNECTIVITY_4:
            default:
                for ( int i = 0; i < nodes.length; i++ ) {
                    for ( int j = 0; j < nodes.length; i++ ) {
                        FieldNode current = nodes[i][j];
                        if ( j < nodes[i].length-1 && nodes[i][j+1] != null) FieldNode.setConnection(current, nodes[i][j+1], 0);
                        if ( i < nm1 && nodes[i+1][j] != null) FieldNode.setConnection(current, nodes[i+1][j], 1);
                        if ( j > 0 && nodes[i][j-1] != null) FieldNode.setConnection(current, nodes[i][j-1], 2);
                        if ( i > 0 && nodes[i-1][j] != null ) FieldNode.setConnection(current, nodes[i-1][j], 3);
                    }
                }
                
                break;
        }
    }
    
    /**
     * Creates a FieldNode[] that represents the edge of a field.  
     * If length > perimeter, then the result will loop back around and 
     * repeat nodes.  If (x,y) is not on an edge, then it returns null.
     * 
     * <b>This case needs to be tested, since I think it is false.  If it 
     * turns out to be true then watch out for 1-node-thick Fields.</b>
     * Let p,q be 2 distinct Fields.  Then let n be a vertex of p, and 
     * let n connect to q anywhere.  Now we call 
     * <code>p.edge(n's coordinates)</code>
     * This will have undefined behavior, since there are multiple edges 
     * this node is associated with.  It cannot be guaranteed that the edge 
     * returned does not cross over n from one side of p (or q) to the other side of p (or q).
     * 
     * @param x starting node's x coordinate.
     * @param y starting node's y coordinate.
     * @param length amount of edge to find.
     * @param direction clockwise or counterclockwise.
     * @return 
     */
    public FieldNode[] edge(int x, int y, int length, int direction) {
        FieldNode[] res = new FieldNode[length];
        res[0] = nodes[x][y];
        
        // for each node in res, find the next node to put into res.  It then uses that node.
        int jmem = 0;   // we start looking where the edge stopped.  Stops cross over
        for ( int i = 1; i < length; i++ ) {
            FieldNode nextNode = null;
            int[] cons;
            if (res[i-1].connections.length == 4 ) {
                cons = CON_4_CLK;
            }
            else if (res[i-1].connections.length == 8 ) {
                cons = CON_8_CLK;
            }
            else if (res[i-1].connections.length == 16 ) {
                cons = CON_16_CLK;
            }
            else {
                // this should never happen.  
                throw new RuntimeException("Strange connection list size in FieldNode: " + res[i-1].connections.length+" ");
            }
            FieldNode leftEdge = null, rightEdge = null;
            for ( int j = jmem; j != (jmem + res[i-1].connections.length); j++ ) {
                if ( res[i-1].connections[cons[j%res[i-1].connections.length]] != null && res[i].connections[cons[(j+1)%res[i-1].connections.length]] == null && leftEdge  != null  ) {
                    leftEdge = res[i-1].connections[cons[j%res[i-1].connections.length]];
                    jmem = j % res[i-1].connections.length;
                }
                else if ( res[i-1].connections[cons[j%res[i-1].connections.length]] == null && res[i].connections[cons[(j+1)%res[i-1].connections.length]] != null && rightEdge != null ) {
                    rightEdge = res[i-1].connections[cons[j+1]];
                    jmem = j % res[i-1].connections.length;
                }
            }
            if ( direction == EDGE_DIR_CLK && rightEdge != null ) {
                res[i] = rightEdge;
            }
            else if ( direction == EDGE_DIR_NCLK && leftEdge != null ) {
                res[i] = leftEdge;
            }
            else {
                if ( direction != EDGE_DIR_CLK || direction != EDGE_DIR_NCLK ) {
                    throw new RuntimeException("Invalid direction flag for direction parameter.");
                }
                else {
                    throw new RuntimeException("Something happened in the algorithm!\nMaby the node specified was not on an edge?\n");
                }
            }
        }
        
        return res;
    }
    
    /**
     * Rotates the field.  This also changes the (x,y) coordinates of each node.
     * @throws UnsupportedOperationException
     * @param angle angle to rotate in radians.
     * @param x point to rotate around.
     * @param y point to rotate around.
     */
    public void rotate(double angle, int x, int y) {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Joins 2 fields together.  
     * This is useful to make non-regularly shaped fields, 
     * This only joins them using 4-connectivity.  For more than 4-connectivity, use
     * joinRect
     * This will attempt to fill non-used connections before using default 
     * 4-connectivity pathways.
     * 
     * For example an L shape could be made like this:
     * <code>
     * Field f = new Field(2,4,2,4,Field.CONNECTIVITY_4);
     * Field q = new Field(3,2,3,2,Field.CONNECTIVITY_8);
     * Field.join(f.edge(1,0,2,Field.COUNTERCLOCKWISE), q.edge(0,0,2,Field.COUNTERCLOCKWISE));
     * </code>
     * A F would be like this:
     * <code>
     * Field f = new Field(2,6,2,6,Field.CONNECTIVITY_4);
     * Field q = new Field(3,2,3,2,Field.CONNECTIVITY_8);
     * Field.join(f.edge(1,5,2,Field.EDGE_DIR_CLK), q.edge(0,0,2,Field.EDGE_DIR_NCLK));
     * Field t = new Field(3,2,3,2,Field,CONNECTIVITY_4);
     * Field.join(f.edge(1,0,2,Field.EDGE_DIR_CLK), t.edge(0,0,2,EDGE_DIR_NCLK), CONNECTIVITY_4);
     * </code>
     * @param f 
     * @param q 
     */
    public static void join(FieldNode[] f, FieldNode[] q) {
        throw new UnsupportedOperationException();  // TODO join
    }
        
    
    
    
}