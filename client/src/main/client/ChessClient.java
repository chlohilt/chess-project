package client;

import requests.CreateGameRequest;
import requests.JoinGameRequest;
import requests.LogoutRequest;
import requests.RegisterRequest;
import responses.CreateGameResponse;
import responses.RegisterResponse;
import responses.ResponseClass;
import spark.Request;
import database.DataAccessException;
import server.ServerFacade;

import java.util.Arrays;

public class ChessClient {
  private String visitorName = null;
  private final ServerFacade server;
  private final String serverUrl;
  private State state = State.SIGNEDOUT;

  public ChessClient(String serverUrl) {
    server = new ServerFacade(serverUrl);
    this.serverUrl = serverUrl;
  }

  public String eval(String input) {
    try {
      var tokens = input.toLowerCase().split(" ");
      var cmd = (tokens.length > 0) ? tokens[0] : "help";
      var params = Arrays.copyOfRange(tokens, 1, tokens.length);
      return switch (cmd) {
        case "login" -> signIn(params);
        case "register" -> register(params);
        case "logout" -> logOut(params);
        case "create" -> createGame(params);
        case "join" -> joinGame(params);
        case "list" -> listGames();
        case "observe " -> joinGame(params);
        case "quit" -> "quit";
        default -> help();
      };
    } catch (DataAccessException ex) {
      return ex.getMessage();
    }
  }

  public ResponseClass joinGame(String... params) throws DataAccessException {
    return server.joinGame(new JoinGameRequest(visitorName, (Integer) params[0], params[1]));
  }

  public CreateGameResponse createGame(String... params) throws DataAccessException {
    return server.createGame(new CreateGameRequest(params[0]));
  }

  public ResponseClass logOut(String... params) throws DataAccessException {
    return server.logout(new LogoutRequest(params[0]));
  }

  public RegisterResponse register(String... params) throws DataAccessException {
    return server.register(new RegisterRequest(params[0], params[1], params[2]));
  }

  public String signIn(String... params) throws DataAccessException {
    if (params.length >= 1) {
      state = State.SIGNEDIN;
      visitorName = String.join("-", params);
      return String.format("You signed in as %s.", visitorName);
    }
    throw new DataAccessException("Expected: <yourname>");
  }

  public String listGames() throws DataAccessException {
    assertSignedIn();
    Request request = new Request();
    return server.listGames(request);
  }

  public String help() {
    if (state == State.SIGNEDOUT) {
      return """
                    - logIn <yourname>
                    - register <yourname> <yourpassword> <youremail>
                    - help
                    - quit
                    """;
    }
    return """
                - list
                - create <gamename>
                - join <gameID> <WHITE | BLACK>
                - observe <gameID>
                - logOut
                - help
                - quit
                """;
  }

  private void assertSignedIn() throws DataAccessException {
    if (state == State.SIGNEDOUT) {
      throw new DataAccessException("You must sign in first.");
    }
  }
}
