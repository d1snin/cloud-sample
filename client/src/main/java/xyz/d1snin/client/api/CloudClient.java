package xyz.d1snin.client.api;

import com.google.gson.Gson;
import io.netty.channel.Channel;
import javafx.stage.Stage;
import xyz.d1snin.client.api.managers.RequestManager;
import xyz.d1snin.client.api.managers.SessionManager;

import java.util.List;

public interface CloudClient {

  Stage getStage();

  Channel getChannel();

  SessionManager getSessionManager();

  RequestManager getRequestManager();

  String getAuthenticationToken();

  void setAuthenticationToken(String authenticationToken);

  Gson getGson();

  void uploadFile(byte[] data, String fileName) throws RuntimeException;

  void downloadFile(String fileName);

  List<String> getFilesList();

  void launch();
}
