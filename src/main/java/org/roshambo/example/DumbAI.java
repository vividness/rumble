package org.roshambo.example;

import org.roshambo.Competitor;

/**
 * Dumb AI that throws in predictable order
 */
public class DumbAI implements Competitor {
    /**
     * Some internals
     */
    private Integer i = 0;

    /**
     * Your bot's name
     */
    private String name = "DumbAI";

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
        return Throw.values()[i++ % 3];
    }

    /**
     * Method that receives round feedback. In this example, this bot is completely oblivious of such thing.
     *
     * @param round         Number of the round
     * @param hasWon        True if you have won
     * @param myThrow       Your throw
     * @param opponentThrow Your opponent's throw
     */
    @Override
    public void feedback(int round, boolean hasWon, Competitor.Throw myThrow, Competitor.Throw opponentThrow) {}
}