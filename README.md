Rumble [![Build Status](https://travis-ci.org/mancmelou/rumble.svg?branch=master)](https://travis-ci.org/mancmelou/rumble)
========
Java "framework" for pairing different Rock Paper Scissors AIs.

Setup
-----
* Download the latest `jar` distribution from the "dist" directory. 
* Add it to your project by copying the `jar` into your project's `lib` directory.
* Import the `org.rumble.Competitor` interface and create your AI class by implementing the interface.
* Don't forget to check the example code inside the package.

Example
-------
To build your own rock paper scissors AI, you'd need to create a new class in your project that implements the `Competitor` interface.

```java
public class RandomAI implements Competitor {
    @Override
    public String name() {
        return "Random AI";
    }

    @Override
    public Throw engage() {
        // You implement your logic here. This is the method that returns 
        // one of the "Competitor.Throw" values {ROCK, PAPER, SCISSORS}
        
        int pick = new Random().nextInt(Throw.values().length);
        return Throw.values()[pick];
    }

    @Override
    void feedback(int round, boolean victory, Throw myThrow, Throw opponentThrow) {
        // This "AI" actually doesn't care about the feedback as it always 
        // plays a random move.
        // 
        // If you want to implement something that will learn from 
        // the opponent's previous moves - this is your entry point.
    }
}
```

Now, let's say you want to try you AI fighting itself (or another AI). You'd need to create a `Match` instance and let the AIs fight for a number of rounds.

```java
import org.rumble.Match;
import org.rumble.Competitor;

public class Main {
    public static void main(String[] args) {
        Competitor player1 = new RandomAI();
        Competitor player2 = new RandomAI();
        
        // Alright, player1 vs player2 in 10 rounds
        Match match = new Match(player1, player2, 10);
        match.fightUntilFinished();
        
        Competitor winner = match.getWinner();
        
        if (winner == player1) {
            System.out.println("The winner is Player 1");
        } else if (winner == player2) {
            System.out.println("The winner is Player 2");
        } else {
            System.out.println("It's tie");
        }
    }
}
```

Author
------
Vladimir Ivic <vlad@retentionscience>

Todo
----
Better readme, more examples.
