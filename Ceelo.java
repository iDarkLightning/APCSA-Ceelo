import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.util.InputMismatchException;

public class Ceelo {
  private final int BANKER_CHIPS = 1000;
  private final int ROLLER_CHIPS = 100;
  private final Scanner scanner = new Scanner(System.in);
  private final Roller banker = new Roller("The Banker", BANKER_CHIPS);
  private final ArrayList<Roller> rollers = new ArrayList<>();
  private int deadPlayers = 0;

  public void run() throws IOException, InterruptedException {
    this.initialize();

    Util.waitAndClear("Press enter to start the game");

    this.loop();
  }

  private void loop() throws IOException, InterruptedException {
    while (true) {
      RollResult bankerResult = this.banker.runTurn();

      if (bankerResult.isGameEnder()) {
        System.out.println("The game is over, the banker rolled and got: " + bankerResult.getRollState());

        if (bankerResult.getRollState() == RollResult.RollState.WIN) {
          this.banker.incrementRoundsWon();
        }

        Util.printSuccess(this.banker.name + " has a total of " + this.banker.getChips() + " total chips");
      } else {
        Util.printSuccess(this.banker.name + " has a total of " + this.banker.getChips() + " total chips");

          
        for (Roller roller : rollers) {
          if (roller.getChips() <= 0) {
            this.deadPlayers++;
            continue;
          }

          int bet = this.getBet(roller);
          Util.printSuccess(roller.name + " has bet " + bet + " chips");
          RollResult rollResult = roller.runTurn();

          if (rollResult.getRollState() == RollResult.RollState.WIN) {
            if (!this.handleWin(roller, bet)) return;
          } else if (rollResult.getRollState() == RollResult.RollState.LOSS) {
            if (!this.handleLoss(roller, bet)) {
              if (this.deadPlayers >= this.rollers.size()) {
                return;
              }

              continue;
            }
          } else if (rollResult.getRollState() == RollResult.RollState.SCORE) {
            if (rollResult.getScoreDelta() > bankerResult.getScoreDelta()) {
              if (!this.handleWin(roller, bet)) return;
            } else if (rollResult.getScoreDelta() == bankerResult.getScoreDelta()) {
              Util.printAccent("Stale roll, the banker and the roller have the same score");
            } else {
              if (!this.handleLoss(roller, bet)) {              
                if (this.deadPlayers >= this.rollers.size()) {
                  System.out.println("returning");
                  return;
                };

                continue;
              }
            }
          }

          Util.printAccent(roller.name + " has a total of " + roller.getChips() + " total chips");
          Util.printAccent("The banker now has " + this.banker.getChips() + " chips");
        }
      }

      Util.waitAndClear("Press enter to play another round");
    }
  }

  private boolean handleWin(Roller roller, int bet) {
    if (bet > this.banker.getChips()) {
      Util.printSuccess("The bank has been broken, the game is over!");
      return false;
    }

    Util.printSuccess(roller.name + " received " + bet + " chips from The Banker");
    roller.incrementChips(bet);
    this.banker.incrementChips(-1 * bet);
    return true;
  }

  private boolean handleLoss(Roller roller, int bet) {
    Util.printError(roller.name + " lost their bet of " + bet + " chips to The Banker");
    
    if (bet >= roller.getChips()) {
      Util.printError(roller.name + " is out of chips and has lost the game!");
      this.deadPlayers++;
      System.out.println(this.deadPlayers);
      
      this.banker.incrementChips(roller.getChips());
      roller.incrementChips(roller.getChips() * -1);
      return false;
    }

    roller.incrementChips(-1 * bet);
    this.banker.incrementChips(bet);
    return true;
  }
  
  private void initialize() {
    int rollerCount = this.getRollerCount();

    for (int i = 0; i < rollerCount; i++) {
      Roller roller = new Roller(this.getRollerName(), ROLLER_CHIPS);
      this.rollers.add(roller);
      Util.printSuccess("Added roller " + roller.name);
    }

  }

  private int getRollerCount() {
    while (true) {
      System.out.print("How many rollers are you playing with (1-4)? ");
      int input;

      try {
        input = this.scanner.nextInt();
      } catch (InputMismatchException exception) {
        Util.printError("Invalid Input!");
        this.scanner.nextLine();
        continue;
      }

      if (input > 4 || input < 1) {
        Util.printError("Invalid number of players! Please a choose a number between 1 and 4");
      } else {
        return input;
      }
    }
  }

  private String getRollerName() {
    System.out.print("Please enter a name for the roller: ");
    return this.scanner.next();
  }

  private int getBet(Roller roller) {
    while (true) {
      System.out.print("How many chips would " + roller.name + " like to bet (1-" + roller.getChips() + ")? ");
      int input;

      try {
        input = this.scanner.nextInt();
      } catch (InputMismatchException exception) {
        Util.printError("Invalid Input!");
        this.scanner.nextLine();
        continue;
      }

      if (input > roller.getChips() || input <= 0) {
        Util.printError("Invalid bet! Please choose a number greater than 0 and less than your total chip count");
      } else {
        return input;
      }
    }
  }
}