package xyz.d1snin.client.api;

import javafx.stage.Stage;
import lombok.NonNull;
import xyz.d1snin.client.internal.CloudClientImpl;
import xyz.d1snin.client.storage.AppStorage;
import xyz.d1snin.client.utils.Checks;

import java.io.InputStream;
import java.net.URL;

public class CloudClientBuilder {

  private String host;
  private int port = -1;
  private InputStream sslKeyStore;
  private String sslKeyPass;
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

  public CloudClientBuilder setSslKeyStore(@NonNull InputStream sslKeyStore) {
    this.sslKeyStore = sslKeyStore;
    return this;
  }

  public CloudClientBuilder setSslKeyPass(@NonNull String sslKeyPass) {
    Checks.checkNotEmpty(sslKeyPass, "SSL KeyStore Pass");
    this.sslKeyPass = sslKeyPass;
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
    Checks.checkNotNull(sslKeyStore, "SSL KeyStore");
    Checks.checkNotNull(sslKeyPass, "SSL KeyStore pass");
    Checks.checkNotEmpty(sslKeyPass, "SSL KeyStore pass");

    CloudClient client =
        new CloudClientImpl(
            host,
            port,
            stage,
            new AppStorage("cloud-app"),
            sslKeyStore,
            sslKeyPass,
            mainSceneLocation,
            loginSceneLocation);

    if (!delayedLaunch) {
      client.launch();
    }

    return client;
  }
}
