package xyz.d1snin.client.api.managers;

public interface SessionManager {

  boolean isAuthenticated();

  void login(String username, String password);

  void register(String username, String password);
}
