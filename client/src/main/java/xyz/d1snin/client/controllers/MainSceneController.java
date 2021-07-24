package xyz.d1snin.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import xyz.d1snin.client.api.managers.RequestManager;

public class MainSceneController {

  private final RequestManager requestManager;

  public MainSceneController(RequestManager requestManager) {
    this.requestManager = requestManager;
  }

  @FXML
  private void upload(ActionEvent actionEvent) {}

  @FXML
  private void download(ActionEvent actionEvent) {}
}
