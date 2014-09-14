package org.roshambo;

/**
 * Player interface
 *
 * example:
 *
 * public class RandomAI implements Competitor {
 *     //@Override
 *     public String name() {
 *         return "Random AI";
 *     }
 *
 *     //@Override
 *     public Weapon engage() {
 *         int pick = new Random().nextInt(Weapon.values().length);
 *
 *         return Letter.values()[pick];
 *     }
 *
 *     //@Override
 *     void feedback(int round, boolean hasWon, Competitor.Throw myThrow, Competitor.Throw opponentThrow) {
 *         // feedback logic here
 *     }
 */
public interface Competitor {
    /**
     * Values that the engage method can return
     */
    enum Throw {ROCK, PAPER, SCISSORS};

    /**
     * This method will be called when battling another competitor.
     *
     * @return Weapon
     */
    Throw engage();

    /**
     * This method will be called after your opponents throws.
     * This is the input for your AI.
     *
     * @param round   Round number
     * @param victory Set to true if you have won this round
     * @param myThrow Your throw
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