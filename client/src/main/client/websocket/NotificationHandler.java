package client.websocket;


import webSocketMessages.serverMessages.NotificationMessage;

public interface NotificationHandler {
  void notify(NotificationMessage notification);
}
