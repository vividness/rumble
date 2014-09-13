package org.roshambo;


import java.util.HashMap;
import java.util.Map;

public class Match {
    private int numRounds;
    private int currentRound;

    private Competitor player1;
    private Competitor player2;

    private HashMap<Integer,HashMap<String, String>> results;
    private HashMap<String, Integer> totals = new HashMap<String, Integer>();

    public Match(Competitor player1, Competitor player2) {
        this(player1, player2, 10);
    }

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

    public void fightUntilFinish() {
        while (currentRound <= numRounds) {
            fight(currentRound++);
        }
    }

    public void fightNextRound() {
        fight(currentRound++);
    }

    public HashMap<Integer,HashMap<String, String>> getResults() {
        return results;
    }

    public void printResults() {
        System.out.format("Round,%s,%s,Winner\n", player1.name(), player2.name());

        for (Map.Entry<Integer, HashMap<String, String>> entry : results.entrySet()) {
            Integer round   = entry.getKey();
            String  weapon1 = entry.getValue().get(player1.name());
            String  weapon2 = entry.getValue().get(player2.name());
            String  winner  = entry.getValue().get("WINNER");

            System.out.format("%d,%s,%s,%s\n", round, weapon1, weapon2, winner);
        }

        System.out.println();
        System.out.format("%s: %s,%s: %s,Ties: %s\n", player1.name(), totals.get(player1.name()), player2.name(), totals.get(player2.name()), totals.get("Ties"));
    }

    public void printLastRoundResult() {
        HashMap<String, String> result = results.get(currentRound);

        String  weapon1 = result.get(player1.name());
        String  weapon2 = result.get(player2.name());
        String  winner  = result.get("WINNER");

        System.out.format("Round,%s,%s,Winner\n", player1.name(), player2.name());
        System.out.format("%d,%s,%s,%s\n", currentRound, weapon1, weapon2, winner);
        System.out.format("%s: %s,%s: %s,Ties: %s\n", player1.name(), totals.get(player1.name()), player2.name(), totals.get(player2.name()), totals.get("Ties"));
    }

    private void fight(int round) {
        Competitor.Throw throwP1 = player1.engage();
        Competitor.Throw throwP2 = player2.engage();

        Competitor winner = decideWinner(throwP1, throwP2);

        updateResults(round, winner, throwP1, throwP2);
        notifyCompetitors(round, winner, throwP1, throwP2);
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
            result.put("WINNER", "Tie");
        } else {
            result.put("WINNER", winner.name());
            totals.put(winner.name(), totals.get(winner.name()) + 1);
        }

        results.put(round, result);
    }
}