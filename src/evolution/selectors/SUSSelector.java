/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package evolution.selectors;

import evolution.Population;
import evolution.RandomNumberGenerator;
import evolution.individuals.Individual;

/**
 *
 * @author Adam
 */
public class SUSSelector implements Selector {

    RandomNumberGenerator rng = RandomNumberGenerator.getInstance();

    @Override
    public void select(int howMany, Population from, Population to) {

        double fitnessSum = 0.0;

        for (int i = 0; i < from.getPopulationSize(); i++) {
            fitnessSum += from.get(i).getFitnessValue();
        }

        double[] fitnesses = new double[from.getPopulationSize()];

        for (int i = 0; i < fitnesses.length; i++) {
            fitnesses[i] = from.get(i).getFitnessValue() / fitnessSum;
        }

        double ball = rng.nextDouble();
        double step = 1.0/howMany;
        double sum = 0;
        int index = 0;
        for (int i = 0; i < howMany; i++) {
            ball += step;
            if(ball > 1){
                ball -= 1;
                index = 0;
                sum = 0;
            }
            for (; index <= fitnesses.length; index++) {
                if (sum > ball || index == fitnesses.length - 1) {
                    to.add((Individual) from.get(index).clone());
                    from.get(index).setLogNotes(from.get(index).getLogNotes() + " " + this.getClass().getCanonicalName());
                    break;
                }
                sum += fitnesses[index];
            }
        }
    }

}
