package xyz.d1snin.client.internal.managers;

import lombok.NonNull;
import lombok.SneakyThrows;
import xyz.d1snin.client.api.managers.RequestManager;
import xyz.d1snin.client.api.managers.SessionManager;
import xyz.d1snin.commons.server_requests.auth.AuthenticationTokenRequest;
import xyz.d1snin.commons.server_requests.auth.LoginRequest;
import xyz.d1snin.commons.server_requests.auth.RegistrationRequest;
import xyz.d1snin.commons.server_requests.session.SessionActiveRequest;
import xyz.d1snin.commons.server_requests.session.SessionRegistrationRequest;
import xyz.d1snin.commons.server_responses.Response;
import xyz.d1snin.commons.server_responses.ResponseCodes;
import xyz.d1snin.commons.server_responses.model.auth.AuthenticationData;
import xyz.d1snin.commons.server_responses.model.session.SessionActivityData;

public class SessionManagerImpl implements SessionManager {

  private final RequestManager requestManager;

  public SessionManagerImpl(@NonNull RequestManager requestManager) {
    this.requestManager = requestManager;
  }

  @Override
  @SneakyThrows
  public boolean isSessionActive() {
    return ((SessionActivityData)
            requestManager.submitRequest(new SessionActiveRequest()).get().getContent())
        .isActive();
  }

  @Override
  @SneakyThrows
  public String login(@NonNull String username, @NonNull String password) {
    Response response = requestManager.submitRequest(new LoginRequest(username, password)).get();

    if (response.getResponseCode() == ResponseCodes.INVALID_LOGIN_OR_PASSWORD) {
      throw new IllegalArgumentException("Invalid login or password.");
    }

    return ((AuthenticationData) response.getContent()).getAuthenticationToken();
  }

  @Override
  @SneakyThrows
  public String register(@NonNull String username, @NonNull String password) {
    Response response =
        requestManager.submitRequest(new RegistrationRequest(username, password)).get();

    if (response.getResponseCode() == ResponseCodes.INVALID_LOGIN_OR_PASSWORD) {
      throw new IllegalArgumentException("Invalid login or password.");
    }

    return ((AuthenticationData) response.getContent()).getAuthenticationToken();
  }

  @Override
  public void registerSession() {
    requestManager.submitRequest(new SessionRegistrationRequest());
  }

  @Override
  @SneakyThrows
  public String retrieveAuthenticationToken() {
    return isSessionActive()
        ? ((AuthenticationData)
                requestManager.submitRequest(new AuthenticationTokenRequest()).get().getContent())
            .getAuthenticationToken()
        : null;
  }
}
