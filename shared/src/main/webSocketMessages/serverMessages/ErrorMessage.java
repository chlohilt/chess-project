package webSocketMessages.serverMessages;

public class ErrorMessage extends ServerMessage{
  public ErrorMessage(ServerMessageType type, String errorMessage) {
    super(type);
    this.errorMessage = "Error: " + errorMessage;
  }

  private String errorMessage;
}
