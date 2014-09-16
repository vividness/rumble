package org.rumble.example;

/**
 * Import the package so you can use the match class and run different AIs
 * here in your main method.
 */
import org.rumble.*;

/**
 * Importing example AIs
 */
import org.rumble.example.competitors.DumbAI;
import org.rumble.example.competitors.RandomAI;

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
         *   1: {"PLAYER_1": "ROCK",  "PLAYER_2": "PAPER", "WINNER": "PLAYER_2"},
         *   2: {"PLAYER_1": "PAPER", "PLAYER_2": "PAPER", "WINNER": "NONE"},
         *   3: {"PLAYER_1": "PAPER", "PLAYER_2": "SCISSORS", "WINNER": "PLAYER_2"},
         * }
         */
        HashMap<Integer,HashMap<String, String>> results = match.getResults();
        HashMap<String, Integer> totals = match.getTotals();

        System.out.format("Round,%s,%s,Winner\n", player1.name(), player2.name());

        for (Map.Entry<Integer, HashMap<String, String>> entry : results.entrySet()) {
            Integer round   = entry.getKey();
            String  throwP1 = entry.getValue().get("PLAYER_1");
            String  throwP2 = entry.getValue().get("PLAYER_2");
            String  winner  = entry.getValue().get("WINNER");

            System.out.format("%d,%s,%s,%s\n", round, throwP1, throwP2, winner);
        }

        System.out.format("\n%s: %s,%s: %s,Ties: %s\n",
            player1.name(), totals.get("PLAYER_1"), player2.name(), totals.get("PLAYER_2"), totals.get("NONE")
        );
    }
}
