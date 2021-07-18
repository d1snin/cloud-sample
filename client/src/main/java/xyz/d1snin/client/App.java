package xyz.d1snin.client;

import com.google.gson.JsonParser;
import javafx.application.Application;
import javafx.stage.Stage;
import xyz.d1snin.client.api.CloudClientBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Objects;

public class App extends Application {

  public static void main(String[] args) {
    Application.launch(args);
  }

  @Override
  public void start(Stage stage) {
    String keyPass;
    try {
      keyPass =
          JsonParser.parseReader(new FileReader("client/ssl_configuration.json"))
              .getAsJsonObject()
              .get("ssl_key")
              .getAsString();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return;
    }

    new CloudClientBuilder()
        .setHost("127.0.0.1")
        .setPort(1569)
        .setSslKeyStore(Objects.requireNonNull(getClass().getResourceAsStream("/client.jks")))
        .setSslKeyPass(keyPass)
        .setStage(stage)
        .setMainSceneLocation(Objects.requireNonNull(getClass().getResource("/mainScene.fxml")))
        .setLoginSceneLocation(Objects.requireNonNull(getClass().getResource("/loginScene.fxml")))
        .buildClientInstance();
  }
}
