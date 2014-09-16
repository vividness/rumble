package org.rumble.example.ai;

import org.rumble.Competitor;
import java.util.Random;

public class RandomAI implements Competitor {
    /**
     * Your AI's name
     */
    private static String name = "RadnomAI";

    /**
     * Method that returns name of your bot
     *
     * @return String
     */
    @Override
    public String name() {
        return name;
    }

    /**
     * Method that returns throws when battling another opponent
     *
     * @return Throw
     */
    @Override
    public Throw engage() {
        int pick = new Random().nextInt(Throw.values().length);
        return Throw.values()[pick];
    }

    /**
     * Method that receives round feedback.
     * In this example, this bot is completely oblivious of such thing.
     *
     * @param round         Number of the round
     * @param victory        True if you have won
     * @param myThrow       Your throw
     * @param opponentThrow Your opponent's throw
     */
    @Override
    public void feedback(int round, boolean victory, Throw myThrow, Throw opponentThrow) {}
}