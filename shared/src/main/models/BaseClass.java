package models;

import database.AuthDAO;
import database.CommonDataAccess;
import database.GameDAO;
import database.UserDAO;
import responses.ResponseClass;
import spark.Request;

public class BaseClass {
  CommonDataAccess commonDataAccess;
  public BaseClass() {
    commonDataAccess = new CommonDataAccess();
  }

  public ResponseClass authorizationCheck(Request request) {
    try {
      String authToken = request.headers("Authorization");
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

  public String returnUsernameFromAuthToken(Request request) {
    try {
      String authToken = request.headers("Authorization");
      return getAuthDataAccess().returnUsername(authToken);
    } catch (Exception e) {
      return "";
    }
  }

  public AuthDAO getAuthDataAccess() {
    return commonDataAccess.getCommonAuthDAO();
  }

  public UserDAO getUserDataAccess() {
    return commonDataAccess.getCommonUserDAO();
  }

  public GameDAO getGameDataAccess() {
    return commonDataAccess.getCommonGameDAO();
  }
}
