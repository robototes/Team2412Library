/*
 * Copyright (c) 2015, Max
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package com.shsrobotics.library.util.functions;

import com.shsrobotics.library.util.wrappers.BooleanWrap;

/**
 *
 * @author Max
 * 
 */
public class UpdateFunction {
    
    public UpdateFunction(UpdateFunction.Function f) {
        update = f;
    }
    
    /**
     * Return true to continue running or false to stop running.
     * @return
     */
    private Function update;
    static final Object tdam = new Object();
    static BooleanWrap bwait = new BooleanWrap(true);
    boolean stopping = false;
    Thread thread;
    public static void run(UpdateFunction func) {
        func.thread = new Thread(()->{
            while ( !func.stopping && func.update.u() ) {
                synchronized(tdam) {
                    try {
                        while ( bwait.value && !func.thread.isInterrupted() ) {
                            tdam.wait();
                        }
                    }
                    catch (InterruptedException ie) {
                        func.stopping = true;
                    }
                }
            }
        });
        func.thread.start();
    }
    
    public void interupt() {
        thread.interrupt();
    }
    
    public boolean isAlive() {
        return thread.isAlive();
    }
    
    public static void setActive() {
        synchronized(tdam) {
            bwait.value = false;
            tdam.notifyAll();
        }
    }
    
    public interface Function {
        public boolean u();
    }
    
}
