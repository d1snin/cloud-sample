package xyz.d1snin.client.controllers;

import io.netty.channel.Channel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import xyz.d1snin.client.api.CloudClient;
import xyz.d1snin.client.api.managers.SessionManager;

public class LoginSceneController {

  private final SessionManager sessionManager;
  private final CloudClient cloudClient;
  private final Stage stage;
  private final FXMLLoader mainSceneLoader;

  @FXML private TextField username_field_login;
  @FXML private PasswordField pass_field_login;
  @FXML private Label log_label_login;

  @FXML private TextField username_field_reg;
  @FXML private PasswordField pass_field_reg;
  @FXML private PasswordField pass_field_repeat_reg;
  @FXML private Label log_label_reg;

  public LoginSceneController(
      SessionManager sessionManager,
      CloudClient cloudClient,
      Stage stage,
      FXMLLoader mainSceneLoader) {
    this.sessionManager = sessionManager;
    this.cloudClient = cloudClient;
    this.stage = stage;
    this.mainSceneLoader = mainSceneLoader;
  }

  @SneakyThrows
  public void login() {
    if (username_field_login.getText().isEmpty() || pass_field_login.getText().isEmpty()) {
      log_label_login.setText("The field cannot be empty.");
      return;
    }

    try {
      cloudClient.setAuthenticationToken(
          sessionManager.login(username_field_login.getText(), pass_field_login.getText()));
      changeScene();
    } catch (IllegalArgumentException e) {
      log_label_login.setText(e.getMessage());
    }
  }

  @SneakyThrows
  public void register() {
    if (username_field_reg.getText().isEmpty()
        || pass_field_reg.getText().isEmpty()
        || pass_field_repeat_reg.getText().isEmpty()) {
      log_label_reg.setText("The field cannot be empty.");
      return;
    }

    if (!pass_field_reg.getText().equals(pass_field_repeat_reg.getText())) {
      log_label_reg.setText("The passwords are not the same. Check the correctness");
      return;
    }

    try {
      cloudClient.setAuthenticationToken(
          sessionManager.register(username_field_reg.getText(), pass_field_repeat_reg.getText()));
      changeScene();
    } catch (IllegalArgumentException e) {
      log_label_reg.setText("This user name is already occupied.");
    }
  }

  @SneakyThrows
  private void changeScene() {
    stage.close();
    Parent root = mainSceneLoader.load();
    stage.setScene(new Scene(root));
    stage.show();
    stage.requestFocus();

    stage.setOnCloseRequest(
        e -> {
          Channel channel = cloudClient.getChannel();
          channel.disconnect();
          channel.close();
        });
  }
}
