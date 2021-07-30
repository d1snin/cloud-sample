package xyz.d1snin.server.api;

import xyz.d1snin.cloud.api.Cloud;
import xyz.d1snin.cloud.utils.Checks;
import xyz.d1snin.commons.server.CloudServer;
import xyz.d1snin.server.internal.CloudServerImpl;
import xyz.d1snin.server.internal.managers.ClientSessionManagerImpl;

public class CloudServerBuilder {

  private int port = 8080;
  private Cloud cloud = null;

  public CloudServerBuilder setPort(int port) {
    this.port = port;
    return this;
  }

  public CloudServerBuilder setCloud(Cloud cloud) throws IllegalArgumentException {
    Checks.checkNotNull(cloud, "Cloud");
    this.cloud = cloud;
    return this;
  }

  public CloudServer buildServerInstance() throws IllegalArgumentException {
    return buildServerInstance(false);
  }

  public CloudServer buildServerInstance(boolean delayedLaunch) {
    Checks.checkNotNull(cloud, "Cloud");

    CloudServer server = new CloudServerImpl(port, cloud, new ClientSessionManagerImpl());

    if (!delayedLaunch) {
      server.launch();
    }

    return server;
  }
}
