/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package evolution.prisoner.strategies;

import evolution.RandomNumberGenerator;
import evolution.prisoner.Result;
import evolution.prisoner.Strategy;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adam
 */
public class AdaptiveBitch extends Strategy{
    protected Strategy[] strategies = new Strategy[]{new BalancedRandom(),new TitForTat(),new AlwaysDeceive()};
    protected RandomNumberGenerator rng = RandomNumberGenerator.getInstance();
    protected Move[] moves;
    protected double[] avg;
    protected int[] credits;
    protected int[] creditsBase;
    protected int rounds;
    
    {
        reset();
    }
    
    @Override
    public Move nextMove() {
        List<Integer> withCredits = new ArrayList<>();
        moves = new Move[strategies.length];
        for(int i =0;i<credits.length;i++){
            moves[i] = strategies[i].nextMove();
            if(credits[i]>0){
                withCredits.add(i);
            }
        }
        int index = withCredits.get(rng.nextInt(withCredits.size()));
        
        credits[index]--;
        return moves[index];
    }

    @Override
    public void reward(Result res) {
        boolean zeroCredits = true;
        for(int i=0;i<strategies.length;i++){
            Result r = new Result(moves[i],res.getOponentsMove());
            strategies[i].reward(r);
            avg[i] = (rounds * avg[i] + r.getMyScore())/(rounds + 1);
            if(credits[i] > 0){
                zeroCredits = false;
            }
        }
        rounds++;
        
        if(zeroCredits){
            int maxIndex = 0;
            double maxAvg = 0;
            for(int i = 0;i<strategies.length;i++){
                if(avg[i] > maxAvg){
                    maxIndex = i;
                    maxAvg = avg[i];
                }
            }
            for(int i =0;i<strategies.length;i++){
                if(i == maxIndex){
                    creditsBase[i] = creditsBase[i] *2;
                }
                else{
                    creditsBase[i] = creditsBase[i] / 2 + creditsBase[i] % 2;
                }
                credits[i] = creditsBase[i];
            }
        }
    }

    @Override
    public String getName() {
        return "Adaptive bitch";
    }

    @Override
    public String authorName() {
        return "Adam Blazek";
    }

    @Override
    public void reset() {
        rounds = 0;
        avg = new double[strategies.length];
        credits = new int[strategies.length];
        creditsBase = new int[strategies.length];
        for(int i=0;i<credits.length;i++){
            credits[i] = 16;
            creditsBase[i] = 16;
        }
    }
    
}