package services;

import requests.CreateGameRequest;
import responses.CreateGameResponse;

/**
 * this class holds a service to create a game
 */
public class CreateGameService {
  /**
   * this is the constructor for a service to create a game
   */
  public CreateGameService(){}
  /**
   * this function creates a game
   * @param c - create game request
   * @return a create game response
   */
  public CreateGameResponse createGame(CreateGameRequest c) {
    // validate auth token
    // deserialize json body to request object
    // call service class to perform requested function
    // receive java response obj
    // serialize java response obj to JSON
    // send http response back to client w status code and response body
    return null;
  }
}
