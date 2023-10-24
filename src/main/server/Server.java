package server;

import handlers.RegisterHandler;
import spark.Spark;

public class Server {
  static RegisterHandler registerHandler = new RegisterHandler();

  public static void main(String[] args) {
    new Server().run();
  }

  private void run() {
    Spark.port(8080);

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
