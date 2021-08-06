package xyz.d1snin.client;

import javafx.application.Application;
import javafx.stage.Stage;
import xyz.d1snin.client.api.CloudClientBuilder;

import java.util.Objects;

public class App extends Application {

  public static void main(String[] args) {
    Application.launch(args);
  }

  @Override
  public void start(Stage stage) {
    new CloudClientBuilder()
        .setHost("127.0.0.1")
        .setPort(1569)
        .setStage(stage)
        .setMainSceneLocation(Objects.requireNonNull(getClass().getResource("/mainScene.fxml")))
        .setLoginSceneLocation(Objects.requireNonNull(getClass().getResource("/loginScene.fxml")))
        .buildClientInstance();
  }
}
