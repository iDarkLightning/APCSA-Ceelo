import java.util.ArrayList;

public class DiceSim {
  private int diceCount;

  public DiceSim(int diceCount) {
    this.diceCount = diceCount;
  }

  public ArrayList<Integer> roll() {
    ArrayList<Integer> result = new ArrayList<Integer>();
    
    for(int i = 0; i < this.diceCount; i++) {
      result.add(Integer.valueOf(this.getRandomNumber(1, 6)));
    }
    
    return result;
  }

  private int getRandomNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }
}