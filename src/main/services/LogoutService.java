package services;

import dataAccess.DataAccessException;
import responses.ResponseClass;
import spark.Request;

/**
 * this service logs a user out
 */
public class LogoutService extends BaseService {
  /**
   * this is the constructor for a service to logout
   */
  public LogoutService() {}
  /**
   * this function logs a user out
   * @return a logout response to see if logout was successful
   */
  public ResponseClass logout(Request logoutRequest) {
    String authToken = logoutRequest.headers("Authorization");
    try {
      if (getAuthDataAccess().returnUsername(authToken) != null) {
        getUserDataAccess().returnUser(getAuthDataAccess().returnUsername(authToken)).setLoggedIn(false);
      }
      return authorizationCheck(logoutRequest);
    } catch (DataAccessException e) {
      return new ResponseClass("Error: database error");
    }
  }
}
