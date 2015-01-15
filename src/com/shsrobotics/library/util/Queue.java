/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shsrobotics.library.util;

/**
 * A hastily constructed queue that uses a linked list as its backing data structure.
 * @author Max
 */
public class Queue {
    Node head;
    Node tail;
    
    public Queue() {
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        Node curr = head;
        while ( curr != null ) {
            sb.append(curr.data.toString());
            curr = curr.next;
        }
        return sb.toString();
    }
    
    public void add(Object data) {
        if ( head == null ) {
            head = new Node(data);
            tail = head;
        }
        else {
            Node n = new Node(data);
            tail.next = n;
            tail = n;
        }
    }
    
    public Object poll() {
        return head == null ? null : head.data;
    }
    
    public void clear() {
        head = null;
        tail = null;
    }
    
    public boolean isEmpty() {
        return head == null;
    }
    
    public Object remove() {
        if ( head == null )
            return null;
        Node tmp = head;
        head = head.next;
        if ( head == null )
            tail = null;
        return tmp.data;
    }
    
    private class Node {
        Node next = null;;
        Object data;
        public Node(Object data) {
            this.data = data;
        }
    }
}
