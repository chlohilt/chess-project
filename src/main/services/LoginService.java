package services;

import dataAccess.CommonDataAccess;
import dataAccess.DataAccessException;
import models.User;
import org.glassfish.grizzly.compression.lzma.impl.Base;
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
          return new LoginResponse(r.getUsername(), getAuthDataAccess().returnAuthToken(r.getUsername()));
        }
      }
      return new LoginResponse("Error: unauthorized");
    } catch (DataAccessException e) {
      return new LoginResponse("Error: database error");
    }
  }
}
