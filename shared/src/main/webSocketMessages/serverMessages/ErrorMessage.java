package webSocketMessages.serverMessages;

public class ErrorMessage extends ServerMessage{
  public ErrorMessage(ServerMessageType type) {
    super(type);
    this.errorMessage = "Error: Invalid command";
  }

  private String errorMessage;
}
