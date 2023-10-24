package services;

import dataAccess.AuthDAO;
import dataAccess.CommonDataAccess;
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
  CommonDataAccess commonDataAccess = new CommonDataAccess();

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
      String userAuthToken = new String();
      if (registerRequest.getUsername() == null || registerRequest.getPassword() == null || registerRequest.getEmail() == null) {
        return new RegisterResponse("Error: bad request");
      } else if (commonDataAccess.getCommonUserDAO().returnUser(registerRequest.getUsername()) != null) {
        userAuthToken = commonDataAccess.getCommonAuthDAO().returnAuthToken(registerRequest.getUsername());
        return new RegisterResponse("Error: already taken");
      }

      User newUser = new User(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getEmail());
      commonDataAccess.getCommonUserDAO().createUser(newUser);
      commonDataAccess.getCommonAuthDAO().createAuthToken(newUser.getUsername());
      userAuthToken = commonDataAccess.getCommonAuthDAO().returnAuthToken(registerRequest.getUsername());
      return new RegisterResponse(registerRequest.getUsername(), userAuthToken);

    } catch (DataAccessException e) {
      return new RegisterResponse("Error: database error");
    }
  }


}
