/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package evolution.prisoner.strategiesNotUsed;

import evolution.prisoner.Result;
import evolution.prisoner.Strategy;

/**
 *
 * @author Adam
 */
public class Balanced extends Strategy{

    protected int decieveCount = 0;
    protected int coopCount = 0;
    
    @Override
    public Move nextMove() {
        if(coopCount > decieveCount){
            return Move.DECEIVE;
        }
        else{
            return Move.COOPERATE;
        }
    }

    @Override
    public void reward(Result res) {
        if(res.getOponentsMove() == Move.COOPERATE){
            coopCount++;
        }
        else{
            decieveCount++;
        }
    }

    @Override
    public String getName() {
        return "Balanced";
    }

    @Override
    public String authorName() {
        return "Adam Blazek";
    }

    @Override
    public void reset() {
        decieveCount = 0;
        coopCount = 0;
    }
    
}
