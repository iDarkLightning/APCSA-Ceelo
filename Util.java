import java.io.IOException;

public class Util {
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";

  public static void waitAndClear(String msg) throws IOException {
    System.out.println(msg);
    System.in.read();
    System.out.print("\033\143");
  }

  public static void printError(String err) {
    System.out.println(ANSI_RED + err + ANSI_RESET);
  }

  public static void printSuccess(String msg) {
    System.out.println(ANSI_GREEN + msg + ANSI_RESET);
  }

  public static void printAccent(String msg) {
    System.out.println(ANSI_PURPLE + msg + ANSI_RESET);
  }
}