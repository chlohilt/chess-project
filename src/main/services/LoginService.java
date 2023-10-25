package services;

import dataAccess.DataAccessException;
import models.User;
import requests.LoginRequest;
import responses.LoginResponse;

import java.util.Objects;

/**
 * this class holds the service to login
 */
public class LoginService extends BaseService {
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
    try {
      User u = getUserDataAccess().returnUser(r.getUsername());
      if (u != null) {
        if (Objects.equals(u.getPassword(), r.getPassword())) {
          u.setLoggedIn(true);
          String authToken = getAuthDataAccess().createAuthToken(r.getUsername());
          return new LoginResponse(r.getUsername(), authToken);
        }
      }
      return new LoginResponse("Error: unauthorized");
    } catch (DataAccessException e) {
      return new LoginResponse("Error: database error");
    }
  }
}
