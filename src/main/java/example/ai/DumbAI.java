package example.ai;

import org.rumble.Competitor;

import java.util.Random;

/**
 * Dumb AI that throws in predictable order
 */
public class DumbAI implements Competitor {
    private static Integer i = 0;

    /**
     * Your AI's name
     */
    private static String name = "DumbAI";

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

    /**
     * AI that utilizes Markov Chain in playing Rock Paper Scissors game.
     */
    public static class MarkovChain implements Competitor {
        /**
         * Put the name inside a static String so your "name()"
         * method doesn't return a new String object every time is called.
         */
        private static String name = "MarkovChain";

        /**
         * Raw matrix that will keep the count of the events.
         * For stochastic transition matrix call "getNextMoveProbability()".
         */
        private int[][] chain = new int[3][3];

        /**
         * Randomly pick initial previous move. Makes it easier to implement.
         */
        private Throw previousThrow = Throw.values()[new Random().nextInt(Throw.values().length)];

        @Override
        public String name() {
            return name;
        }

        /**
         * In this method we care only about the previous move. We will increment the count
         * in the raw matrix. The "engage()" method will utilize that info later.
         *
         * @param round         Round number
         * @param victory       Set to true if you have won this round
         * @param myThrow       Your throw
         * @param opponentThrow Opponent's throw
         */
        @Override
        public void feedback(int round, boolean victory, Throw myThrow, Throw opponentThrow) {
            int previousMove = getThrowIndex(previousThrow);
            int currentMove  = getThrowIndex(opponentThrow);

            chain[previousMove][currentMove] += 1;

            previousThrow = opponentThrow;
        }

        /**
         * First we will predict the opponent's next move and based on that
         * we will chose the right winning move.
         * @return
         */
        @Override
        public Throw engage() {
            Throw predictedMove = predictOpponentsNextMove(previousThrow);

            return pickNextMove(predictedMove);
        }

        /**
         * Return the right winning move based on the predicted move.
         *
         * @param predicted
         * @return
         */
        private Throw pickNextMove(Throw predicted) {
            if (predicted == Throw.PAPER) {
                return Throw.SCISSORS;
            } else if (predicted == Throw.ROCK) {
                return Throw.PAPER;
            } else if (predicted == Throw.SCISSORS) {
                return Throw.ROCK;
            }

            return null;
        }

        /**
         * Based on previous move pick the move that has the highest probability.
         *
         * @param previousThrow
         * @return
         */
        private Throw predictOpponentsNextMove(Throw previousThrow) {
            float probability[]  = getNextMoveProbability(previousThrow);
            float probabilityMax = 0F;

            int iMax = 0;

            for (int i = 0; i < 3; i++) {
                if (probability[i] > probabilityMax) {
                    probabilityMax = probability[i];
                    iMax = i;
                }
            }

            return Throw.values()[iMax];
        }

        /**
         * Normalizes the raw matrix and returns the right probability vector
         * based on opponent's previous move.
         *
         * @param lastThrow
         * @return
         */
        private float[] getNextMoveProbability(Throw lastThrow) {
            float[][] result = new float[3][3];

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    result[i][j] = (float) chain[i][j] / (chain[i][0] + chain[i][1] + chain[i][2]);
                }
            }

            return result[getThrowIndex(lastThrow)];
        }

        /**
         * Helper method, return enum's index for eacier enum to int[][] mapping.
         *
         * @param t
         * @return
         */
        private int getThrowIndex(Throw t) {
            return Throw.valueOf(t.toString()).ordinal();
        }
    }
}