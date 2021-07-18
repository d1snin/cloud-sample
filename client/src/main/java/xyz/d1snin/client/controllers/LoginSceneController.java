package xyz.d1snin.client.controllers;

import javafx.event.ActionEvent;
import xyz.d1snin.client.api.managers.SessionManager;

public class LoginSceneController {

  private final SessionManager sessionManager;

  public LoginSceneController(SessionManager sessionManager) {
    this.sessionManager = sessionManager;
  }

  public void login(ActionEvent actionEvent) {}

  public void register(ActionEvent actionEvent) {}
}
