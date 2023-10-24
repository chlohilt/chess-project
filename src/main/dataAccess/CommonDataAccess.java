package dataAccess;

public class CommonDataAccess {
  private AuthDAO commonAuthDAO = new AuthDAO();

  private UserDAO commonUserDAO = new UserDAO();

  private GameDAO commonGameDAO = new GameDAO();

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
