package responses;

import java.util.List;
import java.util.Map;

/**
 * this class holds the list game response
 */
public class ListGameResponse implements ResponseInterface{
  /**
   * this array holds a game list
   */
  public Map<String, List> games;
  /**
   * this is the constructor for the list game response
   */
  public ListGameResponse() {}

  @Override
  public String getMessage() {
    return null;
  }

  @Override
  public void setMessage(String message) {

  }
}
