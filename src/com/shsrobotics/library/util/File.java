/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shsrobotics.library.util;

import com.sun.squawk.io.j2me.multicastoutput.Protocol;
import com.sun.squawk.microedition.io.FileConnection;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connector;

/**
 * Abstracts away FileConnection, etc. and makes it easier to get an input or output stream for a file.
 * @author Max
 */
public class File {
    FileConnection streamOrigin;
    String path;
    //public static final int READ_WRITE = 0;
    //public static final int READ = 1;
    //public static final int WRITE = 2;
    
    public File(String file) {
        path = file;
        try {
            streamOrigin = (FileConnection)Connector.open(path, Connector.READ_WRITE);
            streamOrigin.create();
            
        }
        catch (IOException ioe) {
            System.out.println("Something went wrong when opening connection to " + path );
        }
    }
    
    public InputStream getInputStream() throws FileNotFoundException {
        try {
            return streamOrigin.openInputStream();
        }
        catch (IOException ioe) {
            if ( streamOrigin.exists() || !streamOrigin.isDirectory() )
                System.out.println("Something went wrong when opening input stream to " + path );
            else
                throw new java.io.FileNotFoundException(path + " does not exist or is a directory.");
            return null;
        }
    }
    
    public OutputStream getOutputStream() {
        return null;
    }
    
}
