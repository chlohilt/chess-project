package websocket;

import webSocketMessages.serverMessages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConnectionManager {
  public final ConcurrentMap<Integer, List<Connection>> connections = new ConcurrentHashMap<>(); // game ID with sessions

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

  public void broadcast(String excludeVisitorName, ServerMessage serverMessage) throws IOException {
    var removeList = new ArrayList<Connection>();
    for (var c : connections.values()) {
      for (var v: c) {
        if (v.session.isOpen()) {
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
