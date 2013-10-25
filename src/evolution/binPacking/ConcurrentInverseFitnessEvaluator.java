/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package evolution.binPacking;

import evolution.FitnessEvaluator;
import evolution.FitnessFunction;
import evolution.Population;
import evolution.concurrent.ConcurrentWorker;
import evolution.concurrent.IndividualOperation;
import evolution.individuals.Individual;

/**
 *
 * @author Adam
 */
public class ConcurrentInverseFitnessEvaluator implements FitnessEvaluator{
    FitnessFunction fitness;
    
    private static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();
;
    public ConcurrentInverseFitnessEvaluator(FitnessFunction fitness) {
        this.fitness = fitness;
    }
    
    
    
    @Override
    public void evaluate(Population pop) {
        ConcurrentWorker.performConcurrently(pop, new IndividualOperation() {

            @Override
            public void operate(Individual ind) {
                ind.setFitnessValue(1/fitness.evaluate(ind));
            }
        });
    }
}
