package server;

import dataAccess.CommonDataAccess;
import handlers.ClearHandler;
import handlers.LoginHandler;
import handlers.LogoutHandler;
import handlers.RegisterHandler;
import spark.Spark;

import static spark.Spark.before;
import static spark.Spark.halt;

public class Server {
  static RegisterHandler registerHandler = new RegisterHandler();
  static LoginHandler loginHandler = new LoginHandler();
  static LogoutHandler logoutHandler = new LogoutHandler();
  static ClearHandler clearHandler = new ClearHandler();

  public static void main(String[] args) {
    new Server().run();
  }

  private void run() {
    Spark.port(8080);
    CommonDataAccess commonDataAccess = new CommonDataAccess();

    // Register a directory for hosting static files
    Spark.externalStaticFileLocation("web");

    Spark.delete("/db", (req, res) ->
            (getClearHandlerInstance()).handleRequest(
                    res)
    );

    Spark.post("/session", (req, res) ->
            (getLoginHandlerInstance()).handleRequest(req,
                    res)
    );

    Spark.post("/user", (req, res) ->
            (getRegisterHandlerInstance()).handleRequest(req,
                    res)
    );

    /*
    before((request, response) -> {
      boolean authenticated=false;
      request.headers();
      if (!authenticated) {
        halt(401, "You are not welcome here");
      } else {
        Spark.delete("/user", (req, res) ->
                (getLogoutHandlerInstance()).handleRequest(req,
                        res)
        );
      }
    });
     */


  }

  private static RegisterHandler getRegisterHandlerInstance() { return registerHandler; }
  private static LoginHandler getLoginHandlerInstance() { return loginHandler; }
  private static LogoutHandler getLogoutHandlerInstance() { return logoutHandler; }
  private static ClearHandler getClearHandlerInstance() { return clearHandler; }
}