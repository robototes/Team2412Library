/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package orth.frc.fieldpositioning;

import orth.frc.fieldpositioning.function.Callback;
import orth.frc.fieldpositioning.function.Callback2;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * This does implement fail-fast.
 * @author Max
 */
public class FieldNodeIterator implements Iterator<FieldNode> {
    
    public static final boolean SEARCH_DEPTH = false, SEARCH_BREADTH = true;
    
    FieldNode initial;
    final int searchIndex;
    
    /**
     * This is called on a node each time a node is discovered.  It filters out nodes that should not participate in the search. 
     */
    public java.util.function.Predicate<FieldNode> filter;
    
    LinkedList<FieldNode> nodes = new LinkedList<>();
    private Callback2<FieldNode,LinkedList<FieldNode>> addTo = FieldNodeIterator.addToQueue;
    private static final Callback2<FieldNode,LinkedList<FieldNode>> addToQueue = (node,list)->list.addLast(node);
    private static final Callback2<FieldNode,LinkedList<FieldNode>> addToStack = (node,list)->list.addFirst(node);
    
    
    protected FieldNodeIterator(Field f, int search) {
        initial = f.nodes[0][0];
        searchIndex = search;
    }
    
    
    protected FieldNodeIterator(FieldNode n, int search) {
        if ( n != null ) {
            initial = n;
        }
        else {
            throw new NullPointerException("FieldNode n in constructor");
        }
        searchIndex = search;
    }
    
    /**
     * Performs a breadth-first search of the graph.  
     * The start node is specified on creation.
     * @param end Ending node.
     * @return Array containing the path to take. This array does not contain the starting node and does contain the end node.
     */
    public FieldNode[] shortestPath(FieldNode end) {
        FieldNode res = next();
        res.pathFrom = null;
        LinkedList<FieldNode> path = new LinkedList<>();
        while (hasNext() && res != end) {
            FieldNode n = next();
            n.pathFrom = res;
        }
        if ( res != end ) throw new RuntimeException("start and end nodes are not part of the same graph or are unreachable.  Has next() been called?");
        while ( res.pathFrom != null ) {
            path.addLast(res);
            res = res.pathFrom;
        }
        return (FieldNode[])path.toArray();
    }
    
    /**
     * How nodes are iterated through.  Acceptable values are SEAARCH_DEPTH, and SEARCH_BREADTH.
     * @param type 
     */
    public void setSearchType(boolean type) {
        if ( type ) {
            addTo = FieldNodeIterator.addToQueue;
        }
        else {
            addTo = FieldNodeIterator.addToStack;
        }
    }
    
    @Override
    public boolean hasNext() {
        return !nodes.isEmpty();
    }

    @Override
    public FieldNode next() {
        FieldNode n = nodes.pollLast();
        
        for (FieldNode connection : n.connections) {
            if ( connection != null && connection.visitIndex != searchIndex ) {
                connection.visitIndex = searchIndex;
                connection.pathFrom = connection;
                if ( filter.test(connection) ) {
                    addTo.callback(connection, nodes);
                }
            }
        }
        
        return n;
    }
    
    
    
}
