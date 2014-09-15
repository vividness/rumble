Roshambo
========
Java framework for pairing different Rock Paper Scissors AIs

Setup
-----
* Download the latest jar distribution from the "dist" directory. 
* Add it to your project by copying the jar into your project's lib directory.
* Import the "org.roshambo.Competitor" interface.
* Create a class that extends the interface.
* Don't forget to check the example code inside the package.

Example
-------
To build your own rock paper scissors AI you'd just need to create a new class that extends the `Competitor` interface.

```java
public class RandomAI implements Competitor {
    @Override
    public String name() {
        return "Random AI";
    }

    @Override
    public Throw engage() {
        // Here, you implement your logic
        // Your AI should return one of the Competitor.Throw values
        
        int pick = new Random().nextInt(Throw.values().length);

        return Throw.values()[pick];
    }

    @Override
    void feedback(int round, boolean victory, Throw myThrow, Throw opponentThrow) {
        // This "AI" actually doesn't care about 
        // the feedback as it always play random move
    }
}
```

Now let's say you want to try you AI fighting itself. You'd need to create a Match instance and let the AIs fight for any amount of rounds.

```java
import org.roshambo.Match;
import org.roshambo.Competitor;

public class Main {
    public static void main(String[] args) {
        Competitor player1 = new RandomAI();
        Competitor player2 = new RandomAI();
        
        // Alright, player1 vs player2 in 10 rounds
        Match match = new Match(player1, player2, 10);
        
        match.fightUntilFinished();
        
        Competitor winner = m.getWinner();
        
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
