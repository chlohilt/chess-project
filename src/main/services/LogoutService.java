package services;

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
    return authorizationCheck(logoutRequest);
  }
}
