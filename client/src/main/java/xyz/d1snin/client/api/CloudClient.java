package xyz.d1snin.client.api;

import io.netty.channel.Channel;
import javafx.stage.Stage;
import xyz.d1snin.client.api.managers.RequestManager;
import xyz.d1snin.client.api.managers.SessionManager;
import xyz.d1snin.client.storage.AppStorage;

public interface CloudClient {

  Stage getStage();

  Channel getChannel();

  AppStorage getAppStorage();

  SessionManager getSessionManager();

  RequestManager getRequestManager();

  void launch();
}
