package xyz.d1snin.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/mainScene.fxml"));

    stage
        .getIcons()
        .add(
            new Image(
                Objects.requireNonNull(getClass().getResourceAsStream("/cloud-computing.png"))));
    stage.setTitle("Cloud");
    stage.setWidth(750);
    stage.setHeight(400);
    stage.setScene(new Scene(loader.load()));
    stage.show();
  }
}
