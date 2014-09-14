package org.roshambo;

import java.util.HashMap;
import java.util.Map;

public class Helpers {
    public static void printResults(Match match) {
        Competitor player1 = match.getPlayer1();
        Competitor player2 = match.getPlayer2();

        HashMap<String, Integer> totals = match.getTotals();
        HashMap<Integer,HashMap<String, String>> results = match.getResults();

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

    public static void printRoundResult(int round, Match match) {
        Competitor player1 = match.getPlayer1();
        Competitor player2 = match.getPlayer2();

        HashMap<String, Integer> totals = match.getTotals();
        HashMap<Integer,HashMap<String, String>> results = match.getResults();

        HashMap<String, String> result = results.get(round);

        String  weapon1 = result.get(player1.name());
        String  weapon2 = result.get(player2.name());
        String  winner  = result.get("WINNER");

        System.out.format("Round,%s,%s,Winner\n", player1.name(), player2.name());
        System.out.format("%d,%s,%s,%s\n", round, weapon1, weapon2, winner);
        System.out.format("%s: %s,%s: %s,Ties: %s\n", player1.name(), totals.get(player1.name()), player2.name(), totals.get(player2.name()), totals.get("Ties"));
    }
}
