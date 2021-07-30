package xyz.d1snin.client.api;

import com.google.gson.GsonBuilder;
import javafx.stage.Stage;
import lombok.NonNull;
import xyz.d1snin.client.internal.CloudClientImpl;
import xyz.d1snin.client.utils.Checks;

import java.net.URL;

public class CloudClientBuilder {

  private String host;
  private int port = -1;
  private Stage stage;
  private URL mainSceneLocation;
  private URL loginSceneLocation;

  private boolean delayedLaunch = false;

  public CloudClientBuilder setHost(@NonNull String host) {
    Checks.checkNotEmpty(host, "Host");
    this.host = host;
    return this;
  }

  public CloudClientBuilder setPort(int port) {
    this.port = port;
    return this;
  }

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

  public CloudClientBuilder setDelayedLaunch(boolean value) {
    this.delayedLaunch = value;
    return this;
  }

  public CloudClient buildClientInstance() {
    Checks.checkNotNull(host, "Host");

    if (port == -1) {
      throw new IllegalArgumentException("Port is not provided.");
    }

    Checks.checkNotNull(stage, "Stage");
    Checks.checkNotNull(mainSceneLocation, "Main Scene Location");
    Checks.checkNotNull(loginSceneLocation, "Login Scene Location");

    CloudClient client =
        new CloudClientImpl(
            host,
            port,
            stage,
            mainSceneLocation,
            loginSceneLocation,
            new GsonBuilder().setPrettyPrinting().create());

    if (!delayedLaunch) {
      client.launch();
    }

    return client;
  }
}
