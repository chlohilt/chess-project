package server;

import dataAccess.CommonDataAccess;
import handlers.RegisterHandler;
import spark.Spark;

public class Server {
  static RegisterHandler registerHandler = new RegisterHandler();

  public static void main(String[] args) {
    new Server().run();
  }

  private void run() {
    Spark.port(8080);
    CommonDataAccess commonDataAccess = new CommonDataAccess();

    // Register a directory for hosting static files
    // Spark.externalStaticFileLocation("public");

    Spark.post("/user", (req, res) ->
            (getRegisterHandlerInstance()).handleRequest(req,
                    res)
    );

  }

  private static RegisterHandler getRegisterHandlerInstance() {
    return registerHandler;
  }
}
