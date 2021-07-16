package xyz.d1snin.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginSceneController {

  @FXML private TextField login_field_login;
  @FXML private PasswordField pass_field_login;
  @FXML private Button login_button;

  @FXML private TextField login_field_reg;
  @FXML private PasswordField pass_field_reg;
  @FXML private PasswordField pass_field_repeat_reg;
  @FXML private Button register_button;

  public void login(ActionEvent actionEvent) {}

  public void register(ActionEvent actionEvent) {}
}
