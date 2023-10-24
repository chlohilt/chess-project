package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import handlers.RegisterHandler;
import models.AuthToken;
import models.User;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.iterators.RecordIterator;
import requests.RegisterRequest;
import responses.RegisterResponse;
import spark.Route;
import spark.Spark;
import dataAccess.UserDAO;

import java.util.HashSet;

/**
 * this class holds the service to register a new user
 */
public class RegisterService {

  UserDAO userDataAccess = new UserDAO();
  AuthDAO authTokenDataAccess = new AuthDAO();

  /**
   * this is the constructor for a service to register a new user
   */
  public RegisterService() {}
  /**
   * this function registers a new user
   * @param registerRequest - a request to register a new user
   * @return a register response to see if registering the user was successful
   */
  public RegisterResponse register(RegisterRequest registerRequest) {
    try {
      RegisterResponse registerResponse = new RegisterResponse();
      if (registerRequest.getUsername() == null || registerRequest.getPassword() == null || registerRequest.getEmail() == null) {
        registerResponse.setMessage("Error: bad request");
        return registerResponse;
      } else if (userDataAccess.returnUser(registerResponse.getUsername()) != null) {
        registerResponse.setMessage("Error: already taken");
        return registerResponse;
      }

      User newUser = new User(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getEmail());
      userDataAccess.createUser(newUser);
      authTokenDataAccess.createAuthToken(newUser.getUsername());
      registerResponse = new RegisterResponse(registerRequest.getUsername(), authTokenDataAccess.returnAuthToken(newUser.getUsername()));
      return registerResponse;

    } catch (DataAccessException e) {
      RegisterResponse registerResponse = new RegisterResponse();
      registerResponse.setMessage("Error: database error");
      return registerResponse;
    }
  }


}
