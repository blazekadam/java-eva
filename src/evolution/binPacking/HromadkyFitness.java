package evolution.binPacking;

import evolution.FitnessFunction;
import evolution.individuals.Individual;
import evolution.individuals.IntegerIndividual;
import java.util.ArrayList;
import java.util.List;

import java.util.Vector;

public class HromadkyFitness implements FitnessFunction {

    final double[] weights;
    final int K;
    double goal;

    public HromadkyFitness(Vector<Double> weights, int K) {
        this.weights = new double[weights.size()];
        for(int i = 0;i<weights.size();i++){
            this.weights[i] = weights.get(i);
        }
        this.K = K;
        goal = 0;
        for(double w : weights){
            goal +=w;
        }
        goal /= K;
    }

    public int[] getBinWeights(Individual ind) {

        int[] binWeights = new int[K];

        int[] bins = ((IntegerIndividual) ind).toIntArray();

        for (int i = 0; i < bins.length; i++) {

            binWeights[bins[i]] += weights[i];
        }

        return binWeights;

    }

    @Override
    public double evaluate(Individual ind) {

        int[] binWeights = getBinWeights(ind);

        double min = Integer.MAX_VALUE;
        double max = Integer.MIN_VALUE;
        for (int i = 0; i < K; i++) {
            if (binWeights[i] < min) {
                min = binWeights[i];
            }
            if (binWeights[i] > max) {
                max = binWeights[i];
            }
        }

        ind.setObjectiveValue(max - min);    // tohle doporucuji zachovat
        double fitness;
        //AVERAGE DISTANCE FROM GOAL
        /*
        double cumulativeDistanceFromGoal = 0;
        for(int i = 0; i<K;i++){
            cumulativeDistanceFromGoal += Math.abs(binWeights[i] - goal);
        }
        
        fitness = (cumulativeDistanceFromGoal/K);
        */

        //AVERAGE DISTANVE BETWEEN BINS
        
        double cumulativeDistance =0;
        for(int i =0;i<K;i++){
            for(int j=i;j<K;j++){
                cumulativeDistance += Math.abs(binWeights[i] - binWeights[j]);
            }
        }
        
        fitness = (cumulativeDistance)/(0.5*K*(K-1));
        
        fitness = fitness == 0 ? 1 : fitness;
        

        return fitness;
    }
}
