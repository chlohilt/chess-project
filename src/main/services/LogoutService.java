package services;

import dataAccess.DataAccessException;
import responses.LogoutResponse;
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
    try {
      String authToken = logoutRequest.headers("Authorization");
      if (authToken == null) {
        return new ResponseClass("Error: unauthorized");
      }
      if (getAuthDataAccess().returnUsername(authToken) != null) {
        return new ResponseClass();
      } else {
        return new ResponseClass("Error: unauthorized");
      }
    } catch (Exception e) {
      return new ResponseClass("Error: database error");
    }
  }
}
