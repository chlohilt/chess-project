package responses;

/**
 * This interface is to have the message in every respone
 */
public interface ResponseInterface {
  /**
   * this field holds the message given after success/failure
   */
  String message="";
  String getMessage();

  void setMessage(String message);
}
