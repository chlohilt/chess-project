package server;

import dataAccess.CommonDataAccess;
import handlers.LoginHandler;
import handlers.RegisterHandler;
import spark.Spark;

public class Server {
  static RegisterHandler registerHandler = new RegisterHandler();
  static LoginHandler loginHandler = new LoginHandler();
  CommonDataAccess commonDataAccess = new CommonDataAccess();


  public static void main(String[] args) {
    new Server().run();
  }

  private void run() {
    Spark.port(8080);
    CommonDataAccess commonDataAccess = new CommonDataAccess();

    // Register a directory for hosting static files
    // Spark.externalStaticFileLocation("public");

    Spark.post("/session", (req, res) ->
            (getLoginHandlerInstance()).handleRequest(req,
                    res)
    );
    Spark.post("/user", (req, res) ->
            (getRegisterHandlerInstance()).handleRequest(req,
                    res)
    );

  }

  private static RegisterHandler getRegisterHandlerInstance() {
    return registerHandler;
  }
  private static LoginHandler getLoginHandlerInstance() { return loginHandler; }
}
