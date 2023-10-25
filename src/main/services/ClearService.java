package services;

import responses.ResponseClass;

/**
 * this service clears the database
 */
public class ClearService extends BaseService {
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
      if (this.getAuthDataAccess().getAuthTokenSet() != null) {
        this.getAuthDataAccess().clearAuthTokens();
      }
      if (this.getGameDataAccess().getGameMap() != null) {
        this.getGameDataAccess().clearGames();
      }
      if (this.getUserDataAccess().getUserMap() != null) {
        this.getUserDataAccess().clearUsers();
      }
      return new ResponseClass();
    } catch (Exception e) {
      return new ResponseClass("Error: database error");
    }
  }
}
