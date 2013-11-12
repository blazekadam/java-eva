package evolution.prisoner.strategies;
import evolution.prisoner.Result;
import evolution.prisoner.Strategy;
import java.util.Random;

public final class FuckMeFuckYou extends Strategy {
    private int round = 0;
    private double p = 0.5;
    private boolean fucked = false;
    private Move last = Move.COOPERATE;
    private final double pInc = 0.03;
    private final Random rnd = new java.util.Random();

    @Override
    public Move nextMove() {
        round++;
        if(last == Move.DECEIVE && !fucked ){// Vratim mu jeho ojeb pokud to neni reakce na muj
            if(rnd.nextDouble() * 1.7 < p){// A nebo ne?
                return Move.DECEIVE;
            }
        }
        double modifier = 1.0;
        if(fucked){
            modifier = 0.8;
        }
        fucked = false;
        if(round % 11== 0 || round % 8 == 0){// Zkusim s nim obcas vyjebat
            if(rnd.nextDouble()*modifier < p){// A nebo ne?
                fucked = true;
                return Move.DECEIVE;
            }
        }
        return Move.COOPERATE;
    }

    @Override
    public void reward(Result res) {
        last = res.getOponentsMove();
        if(last == Move.DECEIVE && !fucked){//Ojebal me
            p += (2*pInc/3);
        }
        if(last == Move.COOPERATE && fucked){//Nevratil mi muj ojeb
            p += pInc;
        }
    }

    @Override
    public String authorName() {
        return "Adam Blazek";
    }

    @Override
    public String getName() {
        return "Fuck me? Fuck you!";
    }

    @Override
    public void reset() {
        round = 0;
        last = Move.COOPERATE;
        fucked = false;
        p = 0.5;
    }


}
