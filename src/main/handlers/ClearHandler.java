package handlers;

import com.google.gson.Gson;
import responses.ResponseClass;
import services.ClearService;
import spark.Response;

public class ClearHandler {
  ClearService clearService = new ClearService();
  Gson gson = new Gson();

  public String handleRequest(Response response) {
    ResponseClass clearResponse = clearService.clear();

    if (clearResponse.getMessage() != null) {
      if (clearResponse.getMessage().equals("Error: database error")) {
        response.status(500);
      }
    }
    response.status(200);

    String jsonResult = gson.toJson(clearResponse);
    return jsonResult;
  }
}
