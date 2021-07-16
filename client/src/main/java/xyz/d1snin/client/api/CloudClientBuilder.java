package xyz.d1snin.client.api;

import javafx.stage.Stage;
import lombok.NonNull;
import xyz.d1snin.client.internal.CloudClientImpl;
import xyz.d1snin.client.utils.Checks;
import xyz.d1snin.client.storage.AppStorage;

import java.net.URL;

public class CloudClientBuilder {

  private Stage stage;
  private URL mainSceneLocation;
  private URL loginSceneLocation;

  public CloudClientBuilder setStage(@NonNull Stage stage) {
    this.stage = stage;
    return this;
  }

  public CloudClientBuilder setLoginSceneLocation(@NonNull URL loginSceneLocation) {
    this.loginSceneLocation = loginSceneLocation;
    return this;
  }

  public CloudClientBuilder setMainSceneLocation(@NonNull URL mainSceneLocation) {
    this.mainSceneLocation = mainSceneLocation;
    return this;
  }

  public CloudClient buildClientInstance() {
    Checks.checkNotNull(stage, "Stage");
    Checks.checkNotNull(mainSceneLocation, "Main Scene Location");
    Checks.checkNotNull(loginSceneLocation, "Login Scene Location");

    CloudClient client =
        new CloudClientImpl(
            stage, new AppStorage("cloud-app"), mainSceneLocation, loginSceneLocation);
    client.launch();
    return client;
  }
}
