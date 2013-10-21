/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package evolution.binPacking;

import evolution.FitnessEvaluator;
import evolution.FitnessFunction;
import evolution.Population;

/**
 *
 * @author Adam
 */
public class MinFitnessEvaluator implements FitnessEvaluator{

    FitnessFunction fitness;

    public MinFitnessEvaluator(FitnessFunction fitness) {
        this.fitness = fitness;
    }
    
    
    
    @Override
    public void evaluate(Population pop) {
        double[] fitnesses = new double[pop.getPopulationSize()];
        double max = 0.0;
        for(int i=0;i<fitnesses.length;i++){
            double f = fitness.evaluate(pop.get(i));
            pop.get(i).setFitnessValue(f);
            if(f > max){
                max = f;
            }
        }
        max++;
        for(int i = 0;i<fitnesses.length;i++){
            pop.get(i).setFitnessValue(max - pop.get(i).getFitnessValue());
        }
    }
    
}
