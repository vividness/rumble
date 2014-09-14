package org.roshambo;

import junit.framework.TestCase;
import org.junit.Ignore;

public class MatchTest extends TestCase {

    public void testFightUntilFinished() throws Exception {
        Competitor c1 = new Competitor() {
            @Override
            public Throw engage() {
                return Throw.PAPER;
            }

            @Override
            public void feedback(int round, boolean victory, Throw myThrow, Throw opponentThrow) {

            }

            @Override
            public String name() {
                return "Competitor 1";
            }
        };

        Competitor c2 = new Competitor() {
            @Override
            public Throw engage() {
                return Throw.SCISSORS;
            }

            @Override
            public void feedback(int round, boolean victory, Throw myThrow, Throw opponentThrow) {

            }

            @Override
            public String name() {
                return "Competitor 2";
            }
        };

        Match match = new Match(c1, c2, 10);
        match.fightUntilFinished();

        assertEquals(match.getWinner(), c2);
    }

    public void testFightNextRound() throws Exception {
        Competitor c1 = new Competitor() {
            @Override
            public Throw engage() {
                return Throw.PAPER;
            }

            @Override
            public void feedback(int round, boolean victory, Throw myThrow, Throw opponentThrow) {

            }

            @Override
            public String name() {
                return "Competitor 1";
            }
        };

        Competitor c2 = new Competitor() {
            @Override
            public Throw engage() {
                return Throw.SCISSORS;
            }

            @Override
            public void feedback(int round, boolean victory, Throw myThrow, Throw opponentThrow) {

            }

            @Override
            public String name() {
                return "Competitor 2";
            }
        };

        Match match = new Match(c1, c2, 10);
        match.fightNextRound();

        // assert winner
        // check results manually vs create a method that returns winner
    }

    public void testGetPlayer1() throws Exception {
        Competitor c1 = new Competitor() {
            @Override
            public Throw engage() {
                return Throw.PAPER;
            }

            @Override
            public void feedback(int round, boolean victory, Throw myThrow, Throw opponentThrow) {

            }

            @Override
            public String name() {
                return "Competitor 1";
            }
        };

        Competitor c2 = new Competitor() {
            @Override
            public Throw engage() {
                return Throw.SCISSORS;
            }

            @Override
            public void feedback(int round, boolean victory, Throw myThrow, Throw opponentThrow) {

            }

            @Override
            public String name() {
                return "Competitor 2";
            }
        };

        Match match = new Match(c1, c2, 10);
        assertEquals(c1, match.getPlayer1());
    }

    public void testGetPlayer2() throws Exception {
        Competitor c1 = new Competitor() {
            @Override
            public Throw engage() {
                return Throw.PAPER;
            }

            @Override
            public void feedback(int round, boolean victory, Throw myThrow, Throw opponentThrow) {

            }

            @Override
            public String name() {
                return "Competitor 1";
            }
        };

        Competitor c2 = new Competitor() {
            @Override
            public Throw engage() {
                return Throw.SCISSORS;
            }

            @Override
            public void feedback(int round, boolean victory, Throw myThrow, Throw opponentThrow) {

            }

            @Override
            public String name() {
                return "Competitor 2";
            }
        };

        Match match = new Match(c1, c2, 10);
        assertEquals(c2, match.getPlayer2());
    }

    @Ignore
    public void testGetResults() throws Exception {

    }

    @Ignore
    public void testGetTotals() throws Exception {

    }
}