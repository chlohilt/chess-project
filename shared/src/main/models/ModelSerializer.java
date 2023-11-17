package models;

import com.google.gson.*;
import responses.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;


public class ModelSerializer {
  public static <T> T deserialize(String json, Class<T> responseClass) {
    return deserialize(new StringReader(json), responseClass);
  }

  public static <T> T deserialize(Reader reader, Class<T> responseClass) {
    GsonBuilder gsonBuilder = new GsonBuilder();
    if (responseClass == CreateGameResponse.class) {
      gsonBuilder.registerTypeAdapter(CreateGameResponse.class, new CreateGameResponseAdapter());
    } else if (responseClass == LoginResponse.class) {
      gsonBuilder.registerTypeAdapter(LoginResponse.class, new LoginResponseAdapter());
    } else if (responseClass == RegisterResponse.class) {
      gsonBuilder.registerTypeAdapter(RegisterResponse.class, new RegisterResponseAdapter());
    } else if (responseClass == ResponseClass.class) {
      gsonBuilder.registerTypeAdapter(ResponseClass.class, new ResponseClassAdapter());
    } else if (responseClass == ListGamesResponse.class) {
      gsonBuilder.registerTypeAdapter(ListGamesResponse.class, new ListGamesResponseAdapter());
    }
    String myString = readString(reader);
    return gsonBuilder.create().fromJson(myString, responseClass);
  }

  protected static String readString(Reader sr) {
    StringBuilder sb = new StringBuilder();
    char[] buf = new char[1024];
    int len;
    while (true) {
      try {
        if (!((len = sr.read(buf)) > 0)) break;
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      sb.append(buf, 0, len);
    }
    return sb.toString();
  }

  private static class CreateGameResponseAdapter implements JsonDeserializer<CreateGameResponse> {
    public CreateGameResponse deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
      return new Gson().fromJson(el, CreateGameResponse.class);
    }
  }
  
  private static class ListGamesResponseAdapter implements JsonDeserializer<ListGamesResponse> {
    public ListGamesResponse deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
      return new Gson().fromJson(el, ListGamesResponse.class);
    }
  }

  private static class LoginResponseAdapter implements JsonDeserializer<LoginResponse> {
    public LoginResponse deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
      return new Gson().fromJson(el, LoginResponse.class);
    }
  }

  private static class RegisterResponseAdapter implements JsonDeserializer<RegisterResponse> {
    public RegisterResponse deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
      return new Gson().fromJson(el, RegisterResponse.class);
    }
  }

  private static class ResponseClassAdapter implements JsonDeserializer<ResponseClass> {
    public ResponseClass deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
      return new Gson().fromJson(el, ResponseClass.class);
    }
  }

  private static class StringAdapter implements JsonDeserializer<String> {
    public String deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
      return new Gson().fromJson(el, String.class);
    }
  }
}