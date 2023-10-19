package services;

import requests.RegisterRequest;
import responses.RegisterResponse;
import spark.Route;
import spark.Spark;

/**
 * this class holds the service to register a new user
 */
public class RegisterService {
  /**
   * this is the constructor for a service to register a new user
   */
  public RegisterService() {}
  /**
   * this function registers a new user
   * @param r - a request to register a new user
   * @return a register response to see if registering the user was successful
   */
  public RegisterResponse register(RegisterRequest r) {
    Spark.post("/user", new Route() {

    })
    return null;
  }
}
