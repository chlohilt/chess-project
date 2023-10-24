package responses;

import java.util.List;
import java.util.Map;

/**
 * this class holds the list game response
 */
public class ListGameResponse extends ResponseClass {
  /**
   * this array holds a game list
   */
  public Map<String, List> games;
  /**
   * this is the constructor for the list game response
   */
  public ListGameResponse() {}

}
