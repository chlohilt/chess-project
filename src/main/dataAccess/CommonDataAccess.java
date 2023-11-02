package dataAccess;

public class CommonDataAccess {
  private static AuthDAO commonAuthDAO;

  static {
    try {
      commonAuthDAO=new AuthDAO();
    } catch (DataAccessException e) {
      throw new RuntimeException(e);
    }
  }

  private static UserDAO commonUserDAO;

  static {
    try {
      commonUserDAO=new UserDAO();
    } catch (DataAccessException e) {
      throw new RuntimeException(e);
    }
  }

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
