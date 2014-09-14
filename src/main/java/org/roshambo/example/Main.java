package org.roshambo.example;

/**
 * Import the package so you can use the match class and run different AIs
 * here in your main method.
 */
import org.roshambo.*;

/**
 * Importing example AIs
 */
import org.roshambo.example.competitors.DumbAI;
import org.roshambo.example.competitors.RandomAI;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        /**
         * Instantiate competitors
         */
        Competitor player1 = new DumbAI();
        Competitor player2 = new RandomAI();

        /**
         * Setup a new match and pass in the competitors and the number of rounds
         */
        Match match = new Match(player1, player2, 10);

        /**
         * This is kinda convenience method. There's also another method "fightNextRound()"
         * where you can iterate through rounds one by one.
         *
         * IMPORTANT: After each round, the match instance will send feedback to the both
         * competitors so they can adjust their strategy based on previous actions.
         */
        match.fightUntilFinished();

        /**
         * Output helper method. If you want the results table as a hash just call "match.getResults()".
         */
        printResults(match);
    }

    public static void printResults(Match match) {
        Competitor player1 = match.getPlayer1();
        Competitor player2 = match.getPlayer2();

        /**
         * "match.getResults" will return a hash map that looks like this HashMap<Integer,HashMap<String, String>>
         *
         * or to say in JSON, it would look something like this:
         *
         * {
         *   1: {"player_1": "ROCK",  "player_2": "PAPER", "WINNER": "player_2"},
         *   2: {"player_1": "PAPER", "player_2": "PAPER", "WINNER": "Tie"},
         *   3: {"player_1": "PAPER", "player_2": "SCISSORS", "WINNER": "player_2"},
         * }
         */
        HashMap<Integer,HashMap<String, String>> results = match.getResults();
        HashMap<String, Integer> totals = match.getTotals();

        System.out.format("Round,%s,%s,Winner\n", player1.name(), player2.name());

        for (Map.Entry<Integer, HashMap<String, String>> entry : results.entrySet()) {
            Integer round   = entry.getKey();
            String  weapon1 = entry.getValue().get(player1.name());
            String  weapon2 = entry.getValue().get(player2.name());
            String  winner  = entry.getValue().get("WINNER");

            System.out.format("%d,%s,%s,%s\n", round, weapon1, weapon2, winner);
        }

        System.out.format("\n%s: %s,%s: %s,Ties: %s\n",
            player1.name(), totals.get(player1.name()), player2.name(), totals.get(player2.name()), totals.get("Ties")
        );
    }
}
