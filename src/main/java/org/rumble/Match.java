package org.rumble;

import java.util.HashMap;

public class Match {
    /**
     * Total number of rounds to fight
     */
    private int numRounds;

    /**
     * Current round
     */
    private int currentRound;

    /**
     * Once the match is over this variable will point to
     * the competitor who won the match
     */
    private Competitor winner = null;

    /**
     * Competitors
     */
    private Competitor player1;
    private Competitor player2;

    /**
     * Match results
     */
    private HashMap<Integer,HashMap<String, String>> results;
    private HashMap<String, Integer> totals = new HashMap<String, Integer>();

    /**
     *
     * @param player1   Player 1
     * @param player2   Player 2
     * @param numRounds Number of rounds to fight in this match
     */
    public Match(Competitor player1, Competitor player2, int numRounds) {
        this.player1      = player1;
        this.player2      = player2;
        this.numRounds    = numRounds;
        this.currentRound = 0;
        this.results      = new HashMap<Integer,HashMap<String, String>>();

        this.totals.put("NONE", 0);
        this.totals.put("PLAYER_1", 0);
        this.totals.put("PLAYER_2", 0);
    }

    /**
     * Fights all rounds
     */
    public void fightUntilFinished() {
        do {
            currentRound += 1;
            fight();
        } while (currentRound < numRounds);

        if (totals.get("PLAYER_1") > totals.get("PLAYER_2")) {
            winner = player1;
        } else if (totals.get("PLAYER_1") < totals.get("PLAYER_2")) {
            winner = player2;
        }
    }

    /**
     * Fights next round
     */
    public void fightNextRound() {
        currentRound += 1;
        fight();
    }

    /**
     * Player 1 getter
     *
     * @return Player 1
     */
    public Competitor getPlayer1() {
        return player1;
    }

    /**
     * Player 2 getter
     *
     * @return Player 2
     */
    public Competitor getPlayer2() {
        return player2;
    }

    /**
     * Returns the winner of the match
     *
     * @return Competitor that won the match
     */
    public Competitor getWinner() { return winner; }

    /**
     * Returns match results
     *
     * @return Match results hash
     */
    public HashMap<Integer,HashMap<String, String>> getResults() {
        return results;
    }

    /**
     * Returns match results totals aggregate
     *
     * @return Match totals aggregate
     */
    public HashMap<String, Integer> getTotals() {
        return totals;
    }

    /**
     * Fights next round
     */
    private void fight() {
        Competitor.Throw throwP1 = player1.engage();
        Competitor.Throw throwP2 = player2.engage();

        Competitor winner = decideWinner(throwP1, throwP2);

        updateResults(winner, throwP1, throwP2);
        notifyCompetitors(winner, throwP1, throwP2);
    }

    /**
     * Return winner of the round
     *
     * @param throwP1 Player 1's throw
     * @param throwP2 Player 2's throw
     * @return Round winner
     */
    private Competitor decideWinner(Competitor.Throw throwP1, Competitor.Throw throwP2) {
        switch (throwP1) {
            case PAPER: {
                if (throwP2 == Competitor.Throw.PAPER)         return null;
                else if (throwP2 == Competitor.Throw.SCISSORS) return player2;
                else if (throwP2 == Competitor.Throw.ROCK)     return player1;
            }
            case ROCK: {
                if (throwP2 == Competitor.Throw.PAPER)         return player2;
                else if (throwP2 == Competitor.Throw.SCISSORS) return player1;
                else if (throwP2 == Competitor.Throw.ROCK)     return null;
            }
            case SCISSORS: {
                if (throwP2 == Competitor.Throw.PAPER)         return player1;
                else if (throwP2 == Competitor.Throw.SCISSORS) return null;
                else if (throwP2 == Competitor.Throw.ROCK)     return player2;
            }
            default: {
                return null;
            }
        }
    }

    /**
     * Send round results to the player 1 and player 2 competitor objects
     *
     * @param winner  Player that has won the round
     * @param throwP1 Player 1's throw
     * @param throwP2 Player 2's throw
     */
    private void notifyCompetitors(Competitor winner, Competitor.Throw throwP1, Competitor.Throw throwP2) {
        player1.feedback(currentRound, winner == player1, throwP1, throwP2);
        player2.feedback(currentRound, winner == player2, throwP2, throwP1);
    }

    /**
     * Updates results hash
     *
     * @param winner  Winner of the round
     * @param throwP1 Player 1's throw
     * @param throwP2 Player 2's throw
     */
    private void updateResults(Competitor winner, Competitor.Throw throwP1, Competitor.Throw throwP2) {
        HashMap<String, String> result = new HashMap<String, String>();

        result.put("PLAYER_1", throwP1.toString());
        result.put("PLAYER_2", throwP2.toString());

        if (winner == player1) {
            result.put("WINNER", "PLAYER_1");
            totals.put("PLAYER_1", totals.get("PLAYER_1") + 1);
        } else if (winner == player2) {
            result.put("WINNER", "PLAYER_2");
            totals.put("PLAYER_2", totals.get("PLAYER_2") + 1);
        } else {
            totals.put("NONE", totals.get("NONE") + 1);
            result.put("WINNER", "NONE");
        }

        results.put(currentRound, result);
    }
}