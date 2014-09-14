package org.roshambo;

import java.util.HashMap;

public class Match {
    private int numRounds;
    private int currentRound;

    private Competitor player1;
    private Competitor player2;

    private HashMap<Integer,HashMap<String, String>> results;
    private HashMap<String, Integer> totals = new HashMap<String, Integer>();

    public Match(Competitor player1, Competitor player2, int numRounds) {
        this.player1      = player1;
        this.player2      = player2;
        this.numRounds    = numRounds;
        this.currentRound = 1;
        this.results      = new HashMap<Integer,HashMap<String, String>>();

        this.totals.put("Ties", 0);
        this.totals.put(player1.name(), 0);
        this.totals.put(player2.name(), 0);
    }

    public void fightUntilFinish() {
        while (currentRound <= numRounds) {
            fight(currentRound++);
        }
    }

    public void fightNextRound() {
        fight(currentRound++);
    }

    public Competitor getPlayer1() {
        return player1;
    }

    public Competitor getPlayer2() {
        return player2;
    }

    public HashMap<Integer,HashMap<String, String>> getResults() {
        return results;
    }

    public HashMap<String, Integer> getTotals() {
        return totals;
    }

    private void fight(int round) {
        Competitor.Throw throwP1 = player1.engage();
        Competitor.Throw throwP2 = player2.engage();

        Competitor winner = decideWinner(throwP1, throwP2);

        updateResults(round, winner, throwP1, throwP2);
        notifyCompetitors(round, winner, throwP1, throwP2);
    }

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

    private void notifyCompetitors(int round, Competitor winner, Competitor.Throw throwP1, Competitor.Throw throwP2) {
        player1.feedback(round, winner == player1, throwP1, throwP2);
        player2.feedback(round, winner == player2, throwP2, throwP1);
    }

    private void updateResults(int round, Competitor winner, Competitor.Throw throwP1, Competitor.Throw throwP2) {
        HashMap<String, String> result = new HashMap<String, String>();

        result.put(player1.name(), throwP1.toString());
        result.put(player2.name(), throwP2.toString());

        if (winner == null) {
            totals.put("Ties", totals.get("Ties") + 1);
            result.put("WINNER", "[Tie]");
        } else {
            result.put("WINNER", winner.name());
            totals.put(winner.name(), totals.get(winner.name()) + 1);
        }

        results.put(round, result);
    }
}