/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package evolution.selectors;

import evolution.Population;
import evolution.RandomNumberGenerator;
import evolution.individuals.Individual;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Adam
 */
public class BigTournamentSelector implements Selector{

    static RandomNumberGenerator rng = RandomNumberGenerator.getInstance();
    
    @Override
    public void select(int howMany, Population from, Population to) {
        List<Individual> individuals = new ArrayList<>();
        for(Individual ind : from){
            individuals.add(ind);
        }
        Collections.shuffle(individuals);
        int size = 2;
        while(size * 2 <from.getPopulationSize()){
            size *= 2;
        }
        Individual[] loosers = new Individual[0];
        Individual[] old = new Individual[size];
        System.arraycopy(individuals.toArray(new Individual[0]), 0, old, 0, size);
        
        while(old.length > howMany){
            Individual[] winners = new Individual[old.length/2];
            loosers = new Individual[old.length/2];
            for(int i = 0;i<winners.length;i++){
                if(old[2*i].getFitnessValue() > old[2*1 + 1].getFitnessValue()){
                    winners[i] = old[2*i];
                    loosers[i] = old[2*i + 1];
                }
                else{
                    winners[i] = old[2*i + 1];
                    loosers[i] = old[2+i];
                }
            }
            old = winners;
        }
        
        for(Individual ind : old){
            ind.setLogNotes(ind.getLogNotes() + " " + this.getClass().getCanonicalName());
            to.add((Individual)ind.clone());
        }
        
        for(int i= 0;i<howMany - old.length;i++){
            int rand = rng.nextInt(old.length);
            loosers[rand].setLogNotes(loosers[rand].getLogNotes() + " " + this.getClass().getCanonicalName());
            to.add((Individual)loosers[rand].clone());
        }
        
    }
    
}
