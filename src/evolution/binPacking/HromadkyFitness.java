package evolution.binPacking;

import evolution.FitnessFunction;
import evolution.individuals.Individual;
import evolution.individuals.IntegerIndividual;
import java.util.ArrayList;
import java.util.List;

import java.util.Vector;

public class HromadkyFitness implements FitnessFunction {

    final List<Double> weights;
    final int K;
    final double goal;

    public HromadkyFitness(Vector<Double> weights, int K) {
        this.weights = new ArrayList<>(weights);
        this.K = K;
        goal = weights.stream().reduce(0.0, (o,n)->o+n)/K;
    }

    public int[] getBinWeights(Individual ind) {

        int[] binWeights = new int[K];

        int[] bins = ((IntegerIndividual) ind).toIntArray();

        for (int i = 0; i < bins.length; i++) {

            binWeights[bins[i]] += weights.get(i);
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

        double fitness = (max - min);
        
        double cumulativeDistanceFromGoal = 0;
        for(int i = 0; i<K;i++){
            cumulativeDistanceFromGoal += Math.abs(binWeights[i] - goal);
        }
        double avgDist = cumulativeDistanceFromGoal / (0.5*K*(K-1));
        //AVERAGE DISTANCE FROM GOAL
        fitness = (cumulativeDistanceFromGoal/K);
        
        double cumulativeDistance =0;
        for(int i =0;i<K;i++){
            for(int j=i;j<K;j++){
                cumulativeDistance += Math.abs(binWeights[i] - binWeights[j]);
            }
        }
        //AVERAGE DISTANVE BETWEEN BINS
        //fitness = (cumulativeDistance)/(0.5*K*(K-1));
        
        fitness = fitness == 0 ? 1 : fitness;
        

        return fitness;
    }
}
