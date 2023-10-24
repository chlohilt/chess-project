package services;

import dataAccess.AuthDAO;
import dataAccess.CommonDataAccess;
import dataAccess.GameDAO;
import dataAccess.UserDAO;

public class BaseService {
  CommonDataAccess commonDataAccess;
  BaseService() {
    commonDataAccess = new CommonDataAccess();
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
