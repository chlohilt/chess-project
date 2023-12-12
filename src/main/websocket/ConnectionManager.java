package websocket;

import webSocketMessages.serverMessages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConnectionManager {
  public final ConcurrentMap<Integer, List<Connection>> connections = new ConcurrentHashMap<>();

  public void add(Integer gameID, Connection connection) {
    if (connections.containsKey(gameID)) {
      connections.get(gameID).add(connection);
    } else {
      var list = new ArrayList<Connection>();
      list.add(connection);
      connections.put(gameID, list);
    }
  }

  public void remove(Integer gameID) {
    connections.remove(gameID);
  }

  // add logic to make sure it's only sending notifications to those in the game
  public void broadcast(String excludeVisitorName, Integer gameID, ServerMessage serverMessage) throws IOException {
    var removeList = new ArrayList<Connection>();
    for (var c : connections.values()) {
      for (var v: c) {
        if (v.session.isOpen() && v.gameID.equals(gameID)) {
          if (!v.visitorName.equals(excludeVisitorName)) {
            v.send(serverMessage.toString());
          }
        } else {
          removeList.add(v);
        }
      }
    }

    // Clean up any connections that were left open.
    for (var c : removeList) {
      connections.remove(c.gameID);
    }
  }
}
