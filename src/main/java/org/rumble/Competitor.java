package org.rumble;

public interface Competitor {
    /**
     * Values that the engage method must return
     */
    enum Throw { ROCK, PAPER, SCISSORS }

    /**
     * This method will be called when battling another competitor.
     * It should always return on of the three throws defined inside the enum above.
     *
     * @return Weapon
     */
    Throw engage();

    /**
     * This method will be called after your opponents throws.
     * This is the input for your AI.
     *
     * @param round         Round number
     * @param victory       Set to true if you have won this round
     * @param myThrow       Your throw
     * @param opponentThrow Opponent's throw
     */
    void feedback(int round, boolean victory, Throw myThrow, Throw opponentThrow);

    /**
     * This method will be used for the result table.
     *
     * @return String Name of the competitor
     */
    String name();
}