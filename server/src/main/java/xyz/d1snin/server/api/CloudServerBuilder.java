package xyz.d1snin.server.api;

import xyz.d1snin.cloud.api.Cloud;
import xyz.d1snin.cloud.utils.Checks;
import xyz.d1snin.commons.server.CloudServer;
import xyz.d1snin.server.internal.CloudServerImpl;

import java.io.IOException;
import java.io.InputStream;

public class CloudServerBuilder {

  private int port = 8080;
  private Cloud cloud = null;
  private InputStream keyStore = null;
  private String sslKeyPass = null;

  public CloudServerBuilder setPort(int port) {
    this.port = port;
    return this;
  }

  public CloudServerBuilder setCloud(Cloud cloud) throws IllegalArgumentException {
    Checks.checkNotNull(cloud, "Cloud");
    this.cloud = cloud;
    return this;
  }

  public CloudServerBuilder setKeyStore(InputStream keyStore) {
    Checks.checkNotNull(keyStore, "KeyStore Stream");
    this.keyStore = keyStore;
    return this;
  }

  public CloudServerBuilder setSslKeyPass(String sslKeyPass) {
    if (sslKeyPass != null) {
      Checks.checkNotEmpty(sslKeyPass, "SSL Key Pass");
    }

    this.sslKeyPass = sslKeyPass;
    return this;
  }

  public CloudServer buildServerInstance() throws IllegalArgumentException, IOException {
    return buildServerInstance(false);
  }

  public CloudServer buildServerInstance(boolean delayedLaunch) {
    Checks.checkNotNull(cloud, "Cloud");
    Checks.checkNotNull(keyStore, "KeyStore Stream");

    CloudServer server =
        new CloudServerImpl(port, cloud, keyStore, sslKeyPass);

    if (!delayedLaunch) {
      server.launch();
    }

    return server;
  }
}
