package xyz.d1snin.commons.server;

import xyz.d1snin.cloud.api.Cloud;
import xyz.d1snin.commons.managers.ClientSessionsManager;

public interface CloudServer {

  int getPort();

  Cloud getCloud();

  ClientSessionsManager getClientSessionsManager();

  void launch();

  void shutdown();
}
