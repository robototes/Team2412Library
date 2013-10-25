/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shsrobotics.library.util;

/**
 *
 * @author Max
 */
public class LinkedList {
    
    int size;
    Node start;
    Node end;
    
    public LinkedList() {
        size = 0;
        start = null;
        end = null;
    }
    
    public void append(Object value) {
        if ( start == null )
            start = new Node(value, null);
        else {
            Node curr = start;
            while ( curr.next != null ) {
                curr = curr.next;
            }
            curr.next = new Node(value, curr);
            size++;
        }
    }
    
    public Object get(int i) {
        if ( !(i < size && i >= 0 ) )
            throw new IndexOutOfBoundsException(i + " is out of range.");
        
        Node curr = start;
        for ( ; i >= 0; i-- ) {
            curr = curr.next;
        }
        return curr.data;
    }
    
    public Object remove(int i) {
        if ( !(i < size && i >= 0 ) )
            throw new IndexOutOfBoundsException(i + " is out of range.");
        
        Node curr = start;
        for ( ; i >= 0; i-- ) {
            curr = curr.next;
        }
        Object data = curr.data;
        //Node temp =  curr.previous;
        curr.previous.next = curr.next;
        curr.next.previous = curr.previous;
        return data;
    }
    
    private class Node {
        Object data;
        Node previous;
        Node next;
        private Node(Object value, Node prev, Node next) {
            data = value;
            previous = prev;
            this.next = next;
        }
        
        private Node(Object value, Node prev) {
            this.data = value;
            this.previous = prev;
            this.next = null;
        }
    }
    
}
