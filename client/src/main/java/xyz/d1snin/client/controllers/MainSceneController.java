package xyz.d1snin.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import lombok.SneakyThrows;
import xyz.d1snin.client.api.CloudClient;
import xyz.d1snin.commons.server_requests.files.FileUploadRequest;

import javax.swing.*;
import java.io.File;
import java.nio.file.Files;

public class MainSceneController {

  private final CloudClient client;

  public MainSceneController(CloudClient client) {
    this.client = client;
  }

  @FXML
  @SneakyThrows
  private void upload(ActionEvent actionEvent) {
    JFileChooser chooser = new JFileChooser();
    int returnVal = chooser.showOpenDialog(null);

    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = chooser.getSelectedFile();
      client
          .getRequestManager()
          .submitRequest(new FileUploadRequest(Files.readAllBytes(file.toPath()), file.getName()));
    }
  }

  @FXML
  private void download(ActionEvent actionEvent) {}
}
