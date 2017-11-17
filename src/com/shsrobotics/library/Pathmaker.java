/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shsrobotics.library;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * Maze path finding with direction output. Just call {@code getPath(table)}.
 * Assumes the start is in top-left corner (0,0) and end is in bottom left corner (7,7).
 * 
 * It uses no external libraries or anything from the standard library, so you can use it on your limited pre-2015 cRIO systems.  
 * 
 * @author Max Orth
 */
public class Pathmaker {
    
    public static void _main(String[] args) {
    	final String tablename = "datatable";
    	NetworkTable table = NetworkTable.getTable(tablename);
        //*
        int[] res = getPath(table);
        for ( int i = 0; i < res.length; i++ ) {
            System.out.println(res[i]);
        }
        /*/
        
        for ( int i = 0; i < 10; i++ ) {
            p(1<<i);
        }
        //*/
    }
    
    public static final int DIR_FWD = 0,
                            DIR_RIGHT = 1,
                            DIR_BACK = 2,
                            DIR_LEFT = 3;
    // change this block comment to /* when moving to the robot.
    /*    
    public static int[] getPath() {
        int[] h = new int[8];
        int[] v = new int[8];
        h[0] = 0b1010000;
        h[1] = 0b1001110;
        h[2] = 0b1110010;
        h[3] = 0b0100101;
        h[4] = 0b1000010;
        h[5] = 0b0001001;
        h[6] = 0b1010001;
        h[7] = 0b0100001;
        
        v[0] = 0b0000100;
        v[1] = 0b0001010;
        v[2] = 0b1101110;
        v[3] = 0b0111011;
        v[4] = 0b0001101;
        v[5] = 0b0110111;
        v[6] = 0b1001010;
        v[7] = 0b0101000;
        return getPath(h,v);
    }
    
    /*/
    private static int[] getPath(NetworkTable table) {
        double[] h = new double[8];
        double[] v = new double[8];
        h[0] = table.getNumber("h0", 0);
        h[1] = table.getNumber("h1", 0);
        h[2] = table.getNumber("h2", 0);
        h[3] = table.getNumber("h3", 0);
        h[4] = table.getNumber("h4", 0);
        h[5] = table.getNumber("h5", 0);
        h[6] = table.getNumber("h6", 0);
        h[7] = table.getNumber("h7", 0);
        
        v[0] = table.getNumber("v0", 0);
        v[1] = table.getNumber("v1", 0);
        v[2] = table.getNumber("v2", 0);
        v[3] = table.getNumber("v3", 0);
        v[4] = table.getNumber("v4", 0);
        v[5] = table.getNumber("v5", 0);
        v[6] = table.getNumber("v6", 0);
        v[7] = table.getNumber("v7", 0);
        return getPath(null,null);
    }
    //*/
    
    private static void p(Object o) {
        System.out.print(o);
    }
    private static void pl(Object o) {
        p(o+"\n");
    }
    
    static int __i = 0;
    
    private static int[] getPath(int[] h, int[] v) {
        int[] result = new int[63];
        Node[][] maze = new Node[8][8];
        //<editor-fold defaultstate="collapsed" desc="create">
        for ( int i = 0; i < 8; i++ ) {
            for ( int j = 0; j < 8; j++ ) {
                maze[i][j] = new Node(i,j);
            }
        }
        
        //horizontal
        for (int r = 0; r < 8; r++) {
            int row = h[r];
            for ( int c = 0; c < 7; c++ ) {
                if ( ( row&(64>>c) ) == 0 ) {
                    //p("h  " + r + "    " + c);
                    maze[r][c].connections[DIR_RIGHT] = maze[r][c+1];
                    maze[r][c+1].connections[DIR_LEFT] = maze[r][c];
                }
            }
        }
        
        //vertical
        for ( int c = 0; c < 8; c++ ) {
            int row = v[c];
            for ( int r = 0; r < 7; r++ ) {
                if ( ( row&(64>>r) ) == 0 ) {
                    //p("v  " + r + "    " + c);
                    maze[r][c].connections[DIR_BACK] = maze[r+1][c];
                    maze[r+1][c].connections[DIR_FWD] = maze[r][c];
                }
            }
        }
        
        // prints the maze entered, but omits some of the last rows of walls.
        /*
        for ( int rc = 0; rc < 7; rc++ ) {
            for ( int c = 0; c < 8; c++ ) {
                p(" ");
                p(maze[rc][c].connections[DIR_RIGHT] == null ? "|" : " " );
            }
            p("\n");
            for ( int c = 0; c < 8; c++ ) {
                p(maze[rc][c].connections[DIR_BACK] == null ? "-" : " ");
                p(" ");
            }
            p("\n");
        }
        */
        //</editor-fold>
        
        Node start = maze[0][0];
        final Node end = maze[7][7];
        Node current;
        
        
        //<editor-fold defaultstate="collapsed" desc="search">
        //LinkedList<Node> q = new LinkedList<>();
        Queue q = new Queue();
        q.push(start);
        start.visited = true;
        
        
        while ( (current = q.pop() ) != end ) {
            
            
            if ( current.connections[0] != null && !current.connections[0].visited ) {
                current.connections[0].visited = true;
                current.connections[0].fromDir = 0;
                q.push(current.connections[0]);
            }
            if ( current.connections[1] != null && !current.connections[1].visited ) {
                current.connections[1].visited = true;
                current.connections[1].fromDir = 1;
                q.push(current.connections[1]);
            }
            if ( current.connections[2] != null && !current.connections[2].visited ) {
                current.connections[2].visited = true;
                current.connections[2].fromDir = 2;
                q.push(current.connections[2]);
            }
            if ( current.connections[3] != null && !current.connections[3].visited ) {
                current.connections[3].visited = true;
                current.connections[3].fromDir = 3;
                q.push(current.connections[3]);
            }
            
            
            
            
        }
        
        pl("ff dsf    " + (current == end));
        
        
        
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="path reversal">
        //*
        current = end;
        int i = 0;
        while ( current != start ) {
            //pl("reverse" + i);
            result[i] = current.fromDir;
            i++;
            current = current.connections[reverseDirection(current.fromDir)];
        }
        result[i] = -1;
        int[] finalResult = new int[i];
        for ( int j = 0; j < i; j++ ) {
            finalResult[j] = result[i-j-1];
        }
        result = finalResult;
        
        //*/
        //</editor-fold>
        
        return result;
    }
    
    private static int reverseDirection(int dir) {
        switch(dir) {
            case 0:
                return 2;
            case 1:
                return 3;
            case 2:
                return 0;
            case 3:
                return 1;
            default:
                System.err.println("Bad input to referseDirection.");
                return -1;
        }
    }
    
    /**
     * Dinky little linked list queue.  
     */
    private static class Queue {
        QNode head;
        QNode tail;
        private class QNode {
            QNode next;
            Node data;
            public QNode(Node n) {
                data = n;
            }
        }
        public void push(Node n) {
            if ( head == null ) {
                head = new QNode(n);
                tail = head;
            }
            else {
                tail.next = new QNode(n);
                tail = tail.next;
            }
        }
        public Node pop() {
            Node n = head.data;
            if ( head != null )
                head = head.next;
            else {
                head = null;
                tail = null;
            }
            return n;
        }
        public boolean hasNext() {
            return head == null;
        }
    }
    
    private static class Node {
        private int x, y;
        boolean visited = false;
        int fromDir = -1;
        Node[] connections = new Node[4];
        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        private void addConnection(Node n) {
            for ( int i = 0; i < 4; i++ ) {
                if ( connections[i] == null ) {
                    connections[i] = n;
                }
            }
        }
        
    }
    
}
