package org.rumble;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class MatchTest extends TestCase {

    private Competitor providePlayerThatThrows(final Competitor.Throw t) {
        return new Competitor() {
            @Override
            public Throw engage() {
                return t;
            }

            @Override
            public void feedback(int round, boolean victory, Throw myThrow, Throw opponentThrow) {

            }

            @Override
            public String name() {
                return "Competitor " + t.toString();
            }
        };
    }

    public void testPlayerGetName() {
        Competitor playerA = providePlayerThatThrows(Competitor.Throw.ROCK);
        assertEquals(playerA.name(), "Competitor ROCK");

        Competitor playerB = providePlayerThatThrows(Competitor.Throw.PAPER);
        assertEquals(playerB.name(), "Competitor PAPER");

        Competitor playerC = providePlayerThatThrows(Competitor.Throw.SCISSORS);
        assertEquals(playerC.name(), "Competitor SCISSORS");
    }

    public void testFightUntilFinishedPlayer1Wins() throws Exception {
        Competitor player11 = providePlayerThatThrows(Competitor.Throw.ROCK);
        Competitor player12 = providePlayerThatThrows(Competitor.Throw.SCISSORS);

        Match match1 = new Match(player11, player12, 10);
        match1.fightUntilFinished();
        assertEquals(match1.getWinner(), player11);

        Competitor player21 = providePlayerThatThrows(Competitor.Throw.PAPER);
        Competitor player22 = providePlayerThatThrows(Competitor.Throw.ROCK);

        Match match2 = new Match(player21, player22, 10);
        match2.fightUntilFinished();
        assertEquals(match2.getWinner(), player21);

        Competitor player31 = providePlayerThatThrows(Competitor.Throw.SCISSORS);
        Competitor player32 = providePlayerThatThrows(Competitor.Throw.PAPER);

        Match match3 = new Match(player31, player32, 10);
        match3.fightUntilFinished();
        assertEquals(match3.getWinner(), player31);
    }

    public void testFightUntilFinishedPlayer2Wins() throws Exception {
        Competitor player11 = providePlayerThatThrows(Competitor.Throw.SCISSORS);
        Competitor player12 = providePlayerThatThrows(Competitor.Throw.ROCK);

        Match match1 = new Match(player11, player12, 10);
        match1.fightUntilFinished();
        assertEquals(match1.getWinner(), player12);

        Competitor player21 = providePlayerThatThrows(Competitor.Throw.ROCK);
        Competitor player22 = providePlayerThatThrows(Competitor.Throw.PAPER);

        Match match2 = new Match(player21, player22, 10);
        match2.fightUntilFinished();
        assertEquals(match2.getWinner(), player22);

        Competitor player31 = providePlayerThatThrows(Competitor.Throw.PAPER);
        Competitor player32 = providePlayerThatThrows(Competitor.Throw.SCISSORS);

        Match match3 = new Match(player31, player32, 10);
        match3.fightUntilFinished();
        assertEquals(match3.getWinner(), player32);
    }

    public void testFightUntilFinishedTied() throws Exception {
        Competitor player11 = providePlayerThatThrows(Competitor.Throw.ROCK);
        Competitor player12 = providePlayerThatThrows(Competitor.Throw.ROCK);

        Match match1 = new Match(player11, player12, 10);
        match1.fightUntilFinished();
        assertEquals(match1.getWinner(), null);

        Competitor player21 = providePlayerThatThrows(Competitor.Throw.PAPER);
        Competitor player22 = providePlayerThatThrows(Competitor.Throw.PAPER);

        Match match2 = new Match(player21, player22, 10);
        match2.fightUntilFinished();
        assertEquals(match2.getWinner(), null);

        Competitor player31 = providePlayerThatThrows(Competitor.Throw.SCISSORS);
        Competitor player32 = providePlayerThatThrows(Competitor.Throw.SCISSORS);

        Match match3 = new Match(player31, player32, 10);
        match3.fightUntilFinished();
        assertEquals(match3.getWinner(), null);
    }

    public void testFightNextRound() throws Exception {
        Competitor player1 = providePlayerThatThrows(Competitor.Throw.PAPER);
        Competitor player2 = providePlayerThatThrows(Competitor.Throw.PAPER);

        Match match = new Match(player1, player2, 10);

        match.fightNextRound();
        assertEquals((int) match.getTotals().get("PLAYER_1"), 0);
        assertEquals((int) match.getTotals().get("PLAYER_2"), 0);
        assertEquals((int) match.getTotals().get("NONE"), 1);

        match.fightNextRound();
        assertEquals((int) match.getTotals().get("PLAYER_1"), 0);
        assertEquals((int) match.getTotals().get("PLAYER_2"), 0);
        assertEquals((int) match.getTotals().get("NONE"), 2);
    }

    public void testGetPlayer1() throws Exception {
        Competitor player1 = providePlayerThatThrows(Competitor.Throw.PAPER);
        Competitor player2 = providePlayerThatThrows(Competitor.Throw.PAPER);

        Match match = new Match(player1, player2, 100);

        assertEquals(player1, match.getPlayer1());
    }

    public void testGetPlayer2() throws Exception {
        Competitor player1 = providePlayerThatThrows(Competitor.Throw.PAPER);
        Competitor player2 = providePlayerThatThrows(Competitor.Throw.PAPER);

        Match match = new Match(player1, player2, 100);

        assertEquals(player2, match.getPlayer2());
    }

    public void testGetResultsPlayer1Wins() throws Exception {
        Competitor player1 = providePlayerThatThrows(Competitor.Throw.PAPER);
        Competitor player2 = providePlayerThatThrows(Competitor.Throw.ROCK);

        Match match = new Match(player1, player2, 10);
        match.fightUntilFinished();

        int roundCount = 0;

        for (Map.Entry<Integer, HashMap<String, String>> entry : match.getResults().entrySet()) {
            Integer round   = entry.getKey();
            String  throwP1 = entry.getValue().get("PLAYER_1");
            String  throwP2 = entry.getValue().get("PLAYER_2");
            String  winner  = entry.getValue().get("WINNER");

            assertEquals((int) round, ++roundCount);
            assertEquals(throwP1, Competitor.Throw.PAPER.toString());
            assertEquals(throwP2, Competitor.Throw.ROCK.toString());
            assertEquals(winner, "PLAYER_1");
        }
    }

    public void testGetResultsPlayer2Wins() throws Exception {
        Competitor player1 = providePlayerThatThrows(Competitor.Throw.PAPER);
        Competitor player2 = providePlayerThatThrows(Competitor.Throw.SCISSORS);

        Match match = new Match(player1, player2, 10);
        match.fightUntilFinished();

        int roundCount = 0;

        for (Map.Entry<Integer, HashMap<String, String>> entry : match.getResults().entrySet()) {
            Integer round   = entry.getKey();
            String  throwP1 = entry.getValue().get("PLAYER_1");
            String  throwP2 = entry.getValue().get("PLAYER_2");
            String  winner  = entry.getValue().get("WINNER");

            assertEquals((int) round, ++roundCount);
            assertEquals(throwP1, Competitor.Throw.PAPER.toString());
            assertEquals(throwP2, Competitor.Throw.SCISSORS.toString());
            assertEquals(winner, "PLAYER_2");
        }
    }

    public void testGetResultsTied() throws Exception {
        Competitor player1 = providePlayerThatThrows(Competitor.Throw.PAPER);
        Competitor player2 = providePlayerThatThrows(Competitor.Throw.PAPER);

        Match match = new Match(player1, player2, 10);
        match.fightUntilFinished();

        int roundCount = 0;

        for (Map.Entry<Integer, HashMap<String, String>> entry : match.getResults().entrySet()) {
            Integer round   = entry.getKey();
            String  throwP1 = entry.getValue().get("PLAYER_1");
            String  throwP2 = entry.getValue().get("PLAYER_2");
            String  winner  = entry.getValue().get("WINNER");

            assertEquals((int) round, ++roundCount);
            assertEquals(throwP1, Competitor.Throw.PAPER.toString());
            assertEquals(throwP2, Competitor.Throw.PAPER.toString());
            assertEquals(winner, "NONE");
        }
    }

    public void testGetTotalsPlayer1Wins() throws Exception {
        Competitor player1 = providePlayerThatThrows(Competitor.Throw.SCISSORS);
        Competitor player2 = providePlayerThatThrows(Competitor.Throw.PAPER);

        Match match = new Match(player1, player2, 10);

        match.fightUntilFinished();

        assertEquals((int) match.getTotals().get("PLAYER_1"), 10);
        assertEquals((int) match.getTotals().get("PLAYER_2"), 0);
        assertEquals((int) match.getTotals().get("NONE"), 0);
    }

    public void testGetTotalsPlayer2Wins() throws Exception {
        Competitor player1 = providePlayerThatThrows(Competitor.Throw.SCISSORS);
        Competitor player2 = providePlayerThatThrows(Competitor.Throw.ROCK);

        Match match = new Match(player1, player2, 10);

        match.fightUntilFinished();

        assertEquals((int) match.getTotals().get("PLAYER_1"), 0);
        assertEquals((int) match.getTotals().get("PLAYER_2"), 10);
        assertEquals((int) match.getTotals().get("NONE"), 0);
    }

    public void testGetTotalsTied() throws Exception {
        Competitor player1 = providePlayerThatThrows(Competitor.Throw.PAPER);
        Competitor player2 = providePlayerThatThrows(Competitor.Throw.PAPER);

        Match match = new Match(player1, player2, 10);

        match.fightUntilFinished();

        assertEquals((int) match.getTotals().get("PLAYER_1"), 0);
        assertEquals((int) match.getTotals().get("PLAYER_2"), 0);
        assertEquals((int) match.getTotals().get("NONE"), 10);
    }
}