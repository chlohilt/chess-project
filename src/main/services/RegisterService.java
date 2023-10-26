package services;

import dataAccess.DataAccessException;
import models.BaseClass;
import models.User;
import requests.RegisterRequest;
import responses.RegisterResponse;

/**
 * this class holds the service to register a new user
 */
public class RegisterService extends BaseClass {

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
      if (registerRequest.getUsername() == null || registerRequest.getPassword() == null || registerRequest.getEmail() == null) {
        return new RegisterResponse("Error: bad request");
      } else if (getUserDataAccess().returnUser(registerRequest.getUsername()) != null) {
        return new RegisterResponse("Error: already taken");
      }

      User newUser = new User(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getEmail());
      getUserDataAccess().createUser(newUser);
      getAuthDataAccess().createAuthToken(newUser.getUsername());
      String userAuthToken = getAuthDataAccess().returnAuthTokenString(registerRequest.getUsername());
      return new RegisterResponse(registerRequest.getUsername(), userAuthToken);

    } catch (DataAccessException e) {
      return new RegisterResponse("Error: database error");
    }
  }


}
