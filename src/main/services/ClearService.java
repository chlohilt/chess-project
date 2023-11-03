package services;

import models.BaseClass;
import responses.ResponseClass;

/**
 * this service clears the database
 */
public class ClearService extends BaseClass {
  /**
   * this is the constructor for the service to clear a database
   */
  public ClearService(){}
  /**
   * this function clears the database--removes all users, games, and authTokens
   *
   */
  public ResponseClass clear() {
    try {
      if (this.getAuthDataAccess().getAuthTokenSize() != 0) {
        this.getAuthDataAccess().clearAuthTokens();
      }
      if (this.getGameDataAccess().getGameSize() != 0) {
        this.getGameDataAccess().clearGames();
      }
      if (this.getUserDataAccess().getUserSize() != 0) {
        this.getUserDataAccess().clearUsers();
      }
      return new ResponseClass();
    } catch (Exception e) {
      return new ResponseClass("Error: database error");
    }
  }
}
