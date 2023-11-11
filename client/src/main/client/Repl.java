package client;

import java.util.Scanner;
import static ui.EscapeSequences.*;

public class Repl {
  private final ChessClient chessClient;
  public Repl(String serverUrl) {
    chessClient = new ChessClient(serverUrl);
  }

  // TODO: add logger
  public void run() {
    System.out.println("\uD83D\uDC36 Welcome to Chess Game Central. Sign in to start.");
    System.out.print(chessClient.help());

    Scanner scanner = new Scanner(System.in);
    var result = "";
    while (!result.equals("quit")) {
      printPrompt();
      String line = scanner.nextLine();

      try {
        result = chessClient.eval(line);
        System.out.print(result);
      } catch (Throwable e) {
        System.out.print(e.getMessage());
      }
    }
    System.out.println();
  }

  private void printPrompt() {
    System.out.print("\n" + ">>> " + SET_BG_COLOR_GREEN);
  }
}
