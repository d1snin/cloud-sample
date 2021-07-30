package xyz.d1snin.client.api.managers;

public interface SessionManager {

  boolean isSessionActive();

  String login(String username, String password);

  String register(String username, String password);
}
