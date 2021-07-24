package xyz.d1snin.client.internal.managers;

import lombok.Data;
import lombok.NonNull;
import lombok.SneakyThrows;
import xyz.d1snin.client.api.managers.RequestManager;
import xyz.d1snin.client.api.managers.SessionManager;
import xyz.d1snin.client.storage.AppStorage;
import xyz.d1snin.commons.server_requests.auth.LoginRequest;
import xyz.d1snin.commons.server_requests.auth.RegistrationRequest;
import xyz.d1snin.commons.server_responses.Response;
import xyz.d1snin.commons.server_responses.ResponseCodes;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class SessionManagerImpl implements SessionManager {

  private final RequestManager requestManager;
  private final AppStorage appStorage;

  private boolean isAuthenticated = false;

  public SessionManagerImpl(
      @NonNull RequestManager requestManager, @NonNull AppStorage appStorage) {
    this.requestManager = requestManager;
    this.appStorage = appStorage;
  }

  @Override
  @SneakyThrows
  public boolean isAuthenticated() {
    if (!Files.readAllLines(appStorage.getBaseConfigPath()).isEmpty()) {
      AuthenticationData authenticationData =
          appStorage.getGson().fromJson(appStorage.getConfigReader(), AuthenticationData.class);

      try {
        login(authenticationData.getLogin(), authenticationData.getPassword());
        isAuthenticated = true;
        return true;
      } catch (IllegalArgumentException e) {
        return isAuthenticated;
      }
    }

    return isAuthenticated;
  }

  @Override
  @SneakyThrows
  public String login(@NonNull String username, @NonNull String password) {
    Response response = requestManager.submitRequest(new LoginRequest(username, password)).get();

    if (response.getResponseCode() == ResponseCodes.INVALID_LOGIN_OR_PASSWORD) {
      throw new IllegalArgumentException(response.getContent());
    }

    writeAuthenticationData(username, password);

    isAuthenticated = true;
    return response.getContent();
  }

  @Override
  @SneakyThrows
  public String register(@NonNull String username, @NonNull String password) {
    Response response =
        requestManager.submitRequest(new RegistrationRequest(username, password)).get();

    if (response.getResponseCode() == ResponseCodes.INVALID_LOGIN_OR_PASSWORD) {
      throw new IllegalArgumentException(response.getContent());
    }

    writeAuthenticationData(username, password);

    isAuthenticated = true;
    return response.getContent();
  }

  @SneakyThrows
  private void writeAuthenticationData(String login, String password) {
    Files.write(
        appStorage.getBaseConfigPath(),
        appStorage
            .getGson()
            .toJson(new AuthenticationData(login, password))
            .getBytes(StandardCharsets.UTF_8));
  }

  @Data
  private static class AuthenticationData {
    private final String login;
    private final String password;
  }
}
