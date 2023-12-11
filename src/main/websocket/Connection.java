package websocket;

import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public class Connection {
  public String visitorName;
  public Session session;
  public Integer gameID;

  public Connection(Integer gameID, String visitorName, Session session) {
    this.gameID = gameID;
    this.visitorName = visitorName;
    this.session = session;
  }

  public void send(String msg) throws IOException {
    session.getRemote().sendString(msg);
  }
}
