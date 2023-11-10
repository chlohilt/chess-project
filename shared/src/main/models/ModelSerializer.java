package models;

import com.google.gson.*;
import responses.CreateGameResponse;
import responses.LoginResponse;
import responses.RegisterResponse;
import responses.ResponseClass;

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
    } else if (responseClass == String.class) {
      gsonBuilder.registerTypeAdapter(String.class, new StringAdapter());
    }
    return gsonBuilder.create().fromJson(reader, responseClass);
  }

  private static class CreateGameResponseAdapter implements JsonDeserializer<CreateGameResponse> {
    public CreateGameResponse deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
      return new Gson().fromJson(el, CreateGameResponse.class);
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