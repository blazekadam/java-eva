/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package evolution.prisoner.strategies;

import evolution.prisoner.strategiesNotUsed.Balanced;
import evolution.RandomNumberGenerator;

/**
 *
 * @author Adam
 */
public class OptimisticPessimist extends Balanced{
    RandomNumberGenerator rng = RandomNumberGenerator.getInstance();
    
    @Override
    public Move nextMove(){
        int coop = coopCount + 1;
        int deci = decieveCount + 1;
        double ratio = coop/(double)deci;
        if(ratio < 1){
            if(ratio < 0.5){
                return Move.DECEIVE;
            }
            else{
                if(rng.nextDouble() < 1.5 - ratio){
                    return Move.COOPERATE;
                }
                else{
                    return Move.DECEIVE;
                }
            }
        }
        else{
            ratio = 1/ratio;
            if(ratio < 0.5){
                return Move.DECEIVE;
            }
            else{
                if(rng.nextDouble() < 1.5 - ratio){
                    return Move.DECEIVE;
                }
                else{
                    return Move.COOPERATE;
                }
            }
        }
    }
    
    @Override
    public String getName(){
        return "Optimistic pessimist";
    }
}
