package websocket;

import com.google.gson.Gson;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConnectionManager {
  public final ConcurrentMap<Integer, List<Connection>> connections=new ConcurrentHashMap<>();
  Gson gson=new Gson();

  public void add(Integer gameID, Connection connection) {
    if (connections.containsKey(gameID)) {
      connections.get(gameID).add(connection);
    } else {
      var list=new ArrayList<Connection>();
      list.add(connection);
      connections.put(gameID, list);
    }
  }

  public void removeUser(Integer gameID, String userName) {
    List<Connection> connectionList=connections.get(gameID);
    connectionList.removeIf(c -> Objects.equals(c.visitorName, userName));
  }

  public void removeGame(Integer gameID) {
    connections.remove(gameID);
  }

  public void broadcast(String excludeVisitorName, Integer gameID, ServerMessage serverMessage) throws IOException {
    for (var c : connections.values()) {
      for (var v : c) {
        if (v.session.isOpen() && v.gameID.equals(gameID)) {
          if (!v.visitorName.equals(excludeVisitorName)) {
            v.send(gson.toJson(serverMessage));
          }
        }
      }
    }
  }
}