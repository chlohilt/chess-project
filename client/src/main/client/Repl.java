package client;

import client.websocket.NotificationHandler;
import webSocketMessages.serverMessages.NotificationMessage;

import java.util.Scanner;

import static java.awt.Color.RED;
import static ui.EscapeSequences.*;


public class Repl implements NotificationHandler {
  private final ChessClient chessClient;
  public Repl(String serverUrl) {
    chessClient = new ChessClient(serverUrl, this);
  }

  public void run() {
    System.out.println(SET_TEXT_COLOR_WHITE + SET_BG_COLOR_DARK_GREY + "\u2654\u265a Welcome to Chess Game Central. Sign in to start. \u265a\u2654");
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
    System.out.print("\n" + ">>> ");
  }

  @Override
  public void notify(NotificationMessage notification) {
    System.out.println(RED); // notifiction get message );
    printPrompt();
  }
}
