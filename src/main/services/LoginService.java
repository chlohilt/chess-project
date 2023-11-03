package services;

import database.DataAccessException;
import models.BaseClass;
import models.User;
import requests.LoginRequest;
import responses.LoginResponse;

import java.util.Objects;

/**
 * this class holds the service to login
 */
public class LoginService extends BaseClass {
  /**
   * this is the constructor for a service to login
   */
  public LoginService() { /* empty */ }
  /**
   * this function logs a user in
   * @param r - a request to login a user
   * @return a login response to see if login was successful
   */
  public LoginResponse login(LoginRequest r) {
    try {
      User u = getUserDataAccess().returnUser(r.getUsername());
      if (u != null && (Objects.equals(u.getPassword(), r.getPassword()))) {
          String authToken = getAuthDataAccess().createAuthToken(r.getUsername());
          return new LoginResponse(r.getUsername(), authToken);

      }
      return new LoginResponse("Error: unauthorized");
    } catch (DataAccessException e) {
      return new LoginResponse("Error: database error");
    }
  }
}
