package xyz.d1snin.client.internal.managers;

import lombok.NonNull;
import xyz.d1snin.client.api.managers.RequestManager;
import xyz.d1snin.client.api.managers.SessionManager;

public class SessionManagerImpl implements SessionManager {

  private final RequestManager requestManager;

  public SessionManagerImpl(@NonNull RequestManager requestManager) {
    this.requestManager = requestManager;
  }

  @Override
  public boolean isAuthenticated() {
    return false;
  }

  @Override
  public void login(@NonNull String username, @NonNull String password) {}

  @Override
  public void register(@NonNull String username, @NonNull String password) {}
}
