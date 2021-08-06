package xyz.d1snin.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import xyz.d1snin.client.api.CloudClient;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class FileSelectorSceneController implements Initializable {

  private final Stage stage;
  private final CloudClient cloudClient;
  private final Label msg_label;
  private final TextArea logs_area;

  public FileSelectorSceneController(
      Stage stage, CloudClient cloudClient, Label msg_label, TextArea logs_area) {
    this.stage = stage;
    this.cloudClient = cloudClient;
    this.msg_label = msg_label;
    this.logs_area = logs_area;
  }

  @FXML private ListView<String> file_view;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    file_view.getItems().addAll(cloudClient.getFilesList());
    file_view.refresh();
  }

  @FXML
  public void cancel(ActionEvent e) {
    stage.close();
  }

  @FXML
  public void handleClick(MouseEvent event) {
    String fileName = file_view.getSelectionModel().getSelectedItem();

    if (fileName == null) {
      return;
    }

    cloudClient.downloadFile(fileName);

    String msg =
        String.format(
            "File was downloaded successfully and stored to %s",
            Paths.get(System.getProperty("user.home"), "cloud", fileName));

    msg_label.setText(msg);
    logs_area.appendText(msg);

    stage.close();
  }
}
