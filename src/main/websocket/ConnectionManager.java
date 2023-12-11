package websocket;

import webSocketMessages.serverMessages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConnectionManager {
  public final ConcurrentMap<Integer, Connection> connections = new ConcurrentHashMap<>(); // game ID with sessions

  public void add(Integer gameID, Connection connection) {
    connections.put(gameID, connection);
  }

  public void remove(Integer gameID) {
    connections.remove(gameID);
  }

  public void broadcast(String excludeVisitorName, ServerMessage serverMessage) throws IOException {
    var removeList = new ArrayList<Connection>();
    for (var c : connections.values()) {
      if (c.session.isOpen()) {
        if (!c.visitorName.equals(excludeVisitorName)) {
          c.send(serverMessage.toString());
        }
      } else {
        removeList.add(c);
      }
    }

    // Clean up any connections that were left open.
    for (var c : removeList) {
      connections.remove(c.gameID);
    }
  }
}
