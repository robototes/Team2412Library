/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shsrobotics.library.util;

/**
 *
 * @author Max
 */
public class Stack {
    Node head;
    
    public void push(Object data) {
        if ( head == null )
            head = new Node(data);
        else {
            Node tmp = new Node(data);
            tmp.next = head;
            head = tmp;
        }
    }
    
    public Object peek() {
        if ( head == null )
            return null;
        else
            return head.data;
    }
    
    public Object pop() {
        if ( head == null )
            return null;
        else {
            Object tmp = head.data;
            head = head.next;
            return tmp;
        }
    }
    
    public boolean isEmpty() {
        return head == null;
    }
    
    public void clear() {
        head = null;
    }
    
    private class Node {
        Node next = null;;
        Object data;
        public Node(Object data) {
            this.data = data;
        }
    }
    
}
