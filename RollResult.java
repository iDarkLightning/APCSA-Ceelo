import java.util.ArrayList;

public class RollResult {
  public enum RollState {
    WIN,
    LOSS,
    SCORE,
    REROLL
  }
  
  private int scoreDelta;
  private RollState rollState;
  private ArrayList<Integer> roll;

  RollResult(RollState rollState, ArrayList<Integer> roll) {
    this.rollState = rollState;
    this.roll = roll;
  }

  RollResult(RollState rollState, ArrayList<Integer> roll, int scoreDelta) {
    this.scoreDelta = scoreDelta;
    this.rollState = rollState;
    this.roll = roll;
  }

  public int getScoreDelta() {
    return this.scoreDelta;
  }

  public RollState getRollState() {
    return this.rollState;
  }

  public ArrayList<Integer> getRoll() {
    return this.roll;
  }

  public boolean isGameEnder() {
    return this.rollState == RollResult.RollState.WIN || this.rollState == RollResult.RollState.LOSS;
  }
}