package services;

import dataAccess.DataAccessException;
import models.AuthToken;
import models.BaseClass;
import models.User;
import responses.ResponseClass;
import spark.Request;

/**
 * this service logs a user out
 */
public class LogoutService extends BaseClass {
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
      String username = getAuthDataAccess().returnUsername(authToken);
      if (authorizationCheck(logoutRequest).getMessage() == null) {
        if (username != null) {
          getAuthDataAccess().deleteAuthToken(username);
          return new ResponseClass("");
        }
      }
      return new ResponseClass("Error: unauthorized");
    } catch (DataAccessException e) {
      return new ResponseClass("Error: database error");
    }
  }
}
