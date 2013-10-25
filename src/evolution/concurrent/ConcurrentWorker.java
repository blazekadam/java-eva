/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package evolution.concurrent;

import evolution.Population;

/**
 *
 * @author Adam
 */
public class ConcurrentWorker {
    private static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();
    
    public static void performConcurrently(final Population pop,final IndividualOperation op){
        Thread[] threads = new Thread[THREAD_COUNT];
        final int popSize = pop.getPopulationSize();
        for(int tid = 0;tid<THREAD_COUNT;tid++){
            final int tidF = tid;
            threads[tid] = new Thread(){
                @Override
                public void run() {
                    for(int i = popSize/THREAD_COUNT * tidF;i<(tidF == THREAD_COUNT - 1 ? popSize : popSize/THREAD_COUNT * (tidF+1) );i++){
                        op.operate(pop.get(i));
                    }
                }
            };
            threads[tid].start();
        }
        for(int tid = 0;tid<THREAD_COUNT;tid++){
            try {
                threads[tid].join();
            }
            catch (InterruptedException ex) {
                System.err.println("Error in concurrent operation.");
            }
        }
    }
}
