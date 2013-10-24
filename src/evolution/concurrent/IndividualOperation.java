/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package evolution.concurrent;

import evolution.individuals.Individual;

/**
 *
 * @author Adam
 */
public interface IndividualOperation {
    public void operate(Individual ind);
}
