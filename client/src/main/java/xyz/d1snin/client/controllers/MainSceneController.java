package xyz.d1snin.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import xyz.d1snin.client.api.CloudClient;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MainSceneController {

  private final CloudClient client;

  public MainSceneController(CloudClient client) {
    this.client = client;
  }

  @FXML TextArea logs_area;
  @FXML Label msg_label;

  @FXML
  @SneakyThrows
  private void upload(ActionEvent actionEvent) {
    JFileChooser chooser = new JFileChooser();
    int returnVal = chooser.showOpenDialog(null);

    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = chooser.getSelectedFile();

      try {
        client.uploadFile(Files.readAllBytes(file.toPath()), file.getName());
      } catch (RuntimeException e) {
        logs_area.appendText(e.getMessage());
        msg_label.setText(e.getMessage());
      }

      String msg = String.format("File %s was successfully uploaded.", file);
      logs_area.appendText(msg);
      msg_label.setText(msg);
    }
  }

  @FXML
  private void download(ActionEvent actionEvent) {
    Stage popup = new Stage();
    popup.initOwner(((Button) actionEvent.getSource()).getScene().getWindow());

    FXMLLoader loader =
        new FXMLLoader(MainSceneController.class.getResource("/fileSelectorScene.fxml"));
    loader.setController(new FileSelectorSceneController(popup, client, msg_label, logs_area));

    Parent root;
    try {
      root = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }

    popup.setScene(new Scene(root));
    popup.show();
  }
}
