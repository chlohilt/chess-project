package services;

import requests.LoginRequest;
import responses.LoginResponse;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import static spark.Spark.before;
import static spark.Spark.halt;

/**
 * this class holds the service to login
 */
public class LoginService {
  /**
   * this is the constructor for a service to login
   */
  public LoginService() {}
  /**
   * this function logs a user in
   * @param r - a request to login a user
   * @return a login response to see if login was successful
   */
  public LoginResponse login(LoginRequest r) {
    before((request, response) -> {
      boolean authenticated=false;

      // ... check if authenticated

      if (!authenticated) {
        halt(401, "You need an authorization token to login");
      }
    });

    Spark.post("/session", new Route() {

      @Override
      public Object handle(Request request, Response response) throws Exception {
        return null;
      }
    });
    return null;
  }
}
