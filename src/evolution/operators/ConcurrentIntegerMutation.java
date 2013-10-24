package evolution.operators;

import evolution.Population;
import evolution.RandomNumberGenerator;
import evolution.binPacking.ConcurrentInverseFitnessEvaluator;
import evolution.concurrent.ConcurrentWorker;
import evolution.individuals.IntegerIndividual;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Martin Pilat
 */
public class ConcurrentIntegerMutation implements Operator {

    private static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();
    
    double mutationProbability;
    double geneChangeProbability;
    RandomNumberGenerator rng = RandomNumberGenerator.getInstance();

    public ConcurrentIntegerMutation(double mutationProbability, double geneChangeProbability) {
        this.mutationProbability = mutationProbability;
        this.geneChangeProbability = geneChangeProbability;
    }

    @Override
    public void operate(Population parents, Population offspring) {
        int size = parents.getPopulationSize();
        offspring.addAll(parents);
        ConcurrentWorker.performConcurrently(offspring, ind->{
            IntegerIndividual intInd = (IntegerIndividual)ind;
            if (rng.nextDouble() < mutationProbability) {
                for (int j = 0; j < intInd.length(); j++) {
                    if (rng.nextDouble() < geneChangeProbability) {
                        intInd.set(j, RandomNumberGenerator.getInstance().nextInt(intInd.getMax() - intInd.getMin()) + intInd.getMin());
                    }
                }
            }
        });
    }

}
