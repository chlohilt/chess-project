package services;

import dataAccess.DataAccessException;
import models.BaseClass;
import requests.LogoutRequest;
import responses.ResponseClass;

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
  public ResponseClass logout(LogoutRequest logoutRequest) {
    try {
      if (getAuthDataAccess().returnAuthToken(logoutRequest.getUsername()) != null) {
        getAuthDataAccess().deleteAuthToken(logoutRequest.getUsername());
        return new ResponseClass("");
      } else {
        return new ResponseClass("Error: database error");
      }
    } catch (DataAccessException e) {
      return new ResponseClass("Error: database error");
    }
  }
}
