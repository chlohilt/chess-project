package dataAccess;

public class CommonDataAccess {
  private static AuthDAO commonAuthDAO = new AuthDAO();

  private static UserDAO commonUserDAO = new UserDAO();

  private static GameDAO commonGameDAO = new GameDAO();

  public CommonDataAccess(){}

  public AuthDAO getCommonAuthDAO() {
    return commonAuthDAO;
  }
  public UserDAO getCommonUserDAO() {
    return commonUserDAO;
  }

  public GameDAO getCommonGameDAO() {
    return commonGameDAO;
  }
}
