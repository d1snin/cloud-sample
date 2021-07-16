package xyz.d1snin.client.internal;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import xyz.d1snin.client.api.CloudClient;
import xyz.d1snin.client.storage.AppStorage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class CloudClientImpl implements CloudClient {

  private final String host;
  private final int port;
  private final Stage stage;
  private final AppStorage appStorage;
  private final URL mainSceneLocation;
  private final URL loginSceneLocation;

  public CloudClientImpl(
      String host,
      int port,
      Stage stage,
      AppStorage appStorage,
      URL mainSceneLocation,
      URL loginSceneLocation) {
    this.host = host;
    this.port = port;
    this.stage = stage;
    this.appStorage = appStorage;
    this.mainSceneLocation = mainSceneLocation;
    this.loginSceneLocation = loginSceneLocation;
  }

  @Override
  public Stage getStage() {
    return stage;
  }

  @Override
  public AppStorage getAppStorage() {
    return appStorage;
  }

  @Override
  public void launch() {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(mainSceneLocation);

    stage
        .getIcons()
        .add(
            new Image(
                Objects.requireNonNull(getClass().getResourceAsStream("/cloud-computing.png"))));

    stage.setTitle("Cloud");
    stage.setWidth(750);
    stage.setHeight(400);

    try {
      stage.setScene(new Scene(loader.load()));
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }

    stage.show();
  }
}
