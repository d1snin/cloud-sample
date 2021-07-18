package xyz.d1snin.commons.server;

import xyz.d1snin.cloud.api.Cloud;

public interface CloudServer {

  int getPort();

  Cloud getCloud();

  void launch();

  void shutdown();
}
