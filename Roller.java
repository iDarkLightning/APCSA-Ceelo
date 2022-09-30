import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

public class Roller {
  public String name;
  private int chips;
  private int roundsWon;
  private final DiceSim diceSim = new DiceSim(3);
  private final int[] WIN_ARRAY = { 4, 5, 6 };
  private final int[] LOSE_ARRAY = { 1, 2, 3 };

  public Roller(String name, int chips) {
    this.name = name;
    this.chips = chips;
  }

  public int getRoundsWon() {
    return this.roundsWon;
  }

  public void incrementRoundsWon() {
    this.roundsWon++;
  }

  public int getChips() {
    return this.chips;
  }

  public void incrementChips(int difference) {
    this.chips += difference;
  }

  public RollResult runTurn() throws InterruptedException {
    RollResult rollResult = this.roll();
    
    do {
      System.out.println(this.name + " is rolling");
      rollResult = this.roll();

      String scoreString = rollResult.getRollState() == RollResult.RollState.SCORE ? ". Their score is " + rollResult.getScoreDelta() : "";
      
      System.out.println(this.name + " rolled " + rollResult.getRoll() + " and got: " + rollResult.getRollState() + scoreString);
    } while (rollResult.getRollState() == RollResult.RollState.REROLL);

    return rollResult;
  }

  private RollResult roll() {
    ArrayList<Integer> results = this.diceSim.roll();
    Collections.sort(results);

    if (this.checkTriplets(results) || this.checkMatch(results, WIN_ARRAY)) {
      return new RollResult(RollResult.RollState.WIN, results);
    } else if (this.checkMatch(results, LOSE_ARRAY)) {
      return new RollResult(RollResult.RollState.LOSS, results);
    } else if (this.checkDoubles(results)) {
      int score;
      if (results.get(0) < results.get(1)) {
        score = results.get(0);
      } else {
        score = results.get(2);
      }
      return new RollResult(RollResult.RollState.SCORE, results, score);
    }

    return new RollResult(RollResult.RollState.REROLL, results);
  }

  private boolean checkTriplets(ArrayList<Integer> results) {
    if (results.size() != 3)
      throw new IllegalArgumentException("The array list must only have 3 elements");

    int itemOne = results.get(0);
    return itemOne == results.get(1) && itemOne == results.get(2);
  }

  private boolean checkDoubles(ArrayList<Integer> results) {
    if (!(results.size() >= 2))
      throw new IllegalArgumentException("The array list must have at least 2 elements");

    HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

    for (int i : results) {
      if (map.containsKey(i)) {
        int current = map.get(i);
        if (current + i >= 2) {
          return true;
        }
        
        map.put(i, current + 1);
      } else {
        map.put(i, 1);
      }
    }

    return false;
  }

  private boolean checkMatch(ArrayList<Integer> results, int[] match) {
    if (results.size() != 3)
      throw new IllegalArgumentException("The array list must only have 3 elements");

    for (int i = 0; i < results.size(); i++) {
      if (results.get(i) == match[i]) {
        continue;
      }

      return false;
    }

    return true;
  }
}