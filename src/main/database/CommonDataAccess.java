package database;

public class CommonDataAccess {
  private static AuthDAO commonAuthDAO;

  static {
    try {
      commonAuthDAO=new AuthDAO();
    } catch (database.DataAccessException e) {
      throw new RuntimeException(e);
    }
  }

  private static UserDAO commonUserDAO;

  static {
    try {
      commonUserDAO=new UserDAO();
    } catch (database.DataAccessException e) {
      throw new RuntimeException(e);
    }
  }

  private static GameDAO commonGameDAO;

  static {
    try {
      commonGameDAO=new GameDAO();
    } catch (DataAccessException e) {
      throw new RuntimeException(e);
    }
  }

  public CommonDataAccess(){ /* empty */ }

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
