Roshambo
========
Java framework for pairing different Rock Paper Scissors AIs

Setup
-----
* Download the latest jar distribution from the "dist" directory. 
* Add it to your project
* Import the "org.roshambo.Competitor" interface
* Create a class that extends the interface
* Don't forget to check the example code inside the package

Example
-------
```java
public class RandomAI implements Competitor {
    @Override
    public String name() {
        return "Random AI";
    }

    @Override
    public Weapon engage() {
        int pick = new Random().nextInt(Throw.values().length);

        return Throw.values()[pick];
    }

    @Override
    void feedback(int round, boolean hasWon, Throw myThrow, Throw opponentThrow) {
        // This "AI" actually doesn't care about 
        // the feedback as it always play random move
    }
}
```

Author
------
Vladimir Ivic <vlad@retentionscience>
