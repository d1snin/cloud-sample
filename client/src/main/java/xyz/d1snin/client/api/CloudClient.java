package xyz.d1snin.client.api;

import com.google.gson.Gson;
import io.netty.channel.Channel;
import javafx.stage.Stage;
import xyz.d1snin.client.api.managers.RequestManager;
import xyz.d1snin.client.api.managers.SessionManager;

public interface CloudClient {

  Stage getStage();

  Channel getChannel();

  SessionManager getSessionManager();

  RequestManager getRequestManager();

  String getClientId();

  Gson getGson();

  void launch();
}
