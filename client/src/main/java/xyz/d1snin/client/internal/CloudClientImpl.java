package xyz.d1snin.client.internal;

import com.google.gson.Gson;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import xyz.d1snin.client.api.CloudClient;
import xyz.d1snin.client.api.managers.RequestManager;
import xyz.d1snin.client.api.managers.SessionManager;
import xyz.d1snin.client.controllers.LoginSceneController;
import xyz.d1snin.client.controllers.MainSceneController;
import xyz.d1snin.client.handlers.ResponseHandler;
import xyz.d1snin.client.internal.managers.RequestManagerImpl;
import xyz.d1snin.client.internal.managers.SessionManagerImpl;
import xyz.d1snin.commons.server_requests.files.DownloadFileRequest;
import xyz.d1snin.commons.server_requests.files.FileUploadRequest;
import xyz.d1snin.commons.server_requests.files.UserFilesListRequest;
import xyz.d1snin.commons.server_responses.Response;
import xyz.d1snin.commons.server_responses.ResponseCodes;
import xyz.d1snin.commons.server_responses.model.files.FileData;
import xyz.d1snin.commons.server_responses.model.files.UserFilesData;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;

public class CloudClientImpl implements CloudClient {

  private final String host;
  private final int port;
  private final Stage stage;
  private final URL mainSceneLocation;
  private final URL loginSceneLocation;
  private final Gson gson;

  private Channel channel;
  private RequestManager requestManager;
  private SessionManager sessionManager;
  private String authenticationToken;

  public CloudClientImpl(
      String host,
      int port,
      Stage stage,
      URL mainSceneLocation,
      URL loginSceneLocation,
      Gson gson) {
    this.host = host;
    this.port = port;
    this.stage = stage;
    this.mainSceneLocation = mainSceneLocation;
    this.loginSceneLocation = loginSceneLocation;
    this.gson = gson;
  }

  @Override
  public Stage getStage() {
    return stage;
  }

  @Override
  public Channel getChannel() {
    return channel;
  }

  @Override
  public SessionManager getSessionManager() {
    return sessionManager;
  }

  @Override
  public RequestManager getRequestManager() {
    return requestManager;
  }

  @Override
  public String getAuthenticationToken() {
    return authenticationToken;
  }

  @Override
  public void setAuthenticationToken(String authenticationToken) {
    this.authenticationToken = authenticationToken;
  }

  @Override
  public Gson getGson() {
    return gson;
  }

  @Override
  @SneakyThrows
  public void uploadFile(byte[] data, String fileName) {
    Response response = requestManager.submitRequest(new FileUploadRequest(data, fileName)).get();

    if (response.getResponseCode() == ResponseCodes.FILE_ALREADY_EXISTS) {
      throw new IllegalArgumentException("File already exists on the cloud.");
    }

    if (response.getResponseCode() == ResponseCodes.UPLOADING_FAILED) {
      throw new RuntimeException("Uploading failed.");
    }
  }

  @Override
  @SneakyThrows
  public void downloadFile(String fileName) {
    Path downloads = Paths.get(System.getProperty("user.home"), "cloud");

    if (!Files.exists(downloads)) {
      Files.createDirectories(downloads);
    }

    Files.write(
        downloads.resolve(fileName),
        ((FileData)
                requestManager.submitRequest(new DownloadFileRequest(fileName)).get().getContent())
            .getBytes());
    ;
  }

  @Override
  @SneakyThrows
  public List<String> getFilesList() {

    return ((UserFilesData)
            requestManager.submitRequest(new UserFilesListRequest()).get().getContent())
        .getFiles();
  }

  @SneakyThrows
  @Override
  public void launch() {

    EventLoopGroup group = new NioEventLoopGroup();
    Bootstrap boot = new Bootstrap();

    CloudClient cloudClient = this;
    boot.group(group)
        .channel(NioSocketChannel.class)
        .handler(
            new ChannelInitializer<SocketChannel>() {

              @Override
              protected void initChannel(SocketChannel ch) {
                ch.pipeline()
                    .addLast(
                        new ObjectEncoder(),
                        new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                        new ResponseHandler(cloudClient));
              }
            });

    this.channel = boot.connect(host, port).sync().channel();

    this.requestManager = new RequestManagerImpl(this);
    this.sessionManager = new SessionManagerImpl(requestManager);

    if (sessionManager.isSessionActive()) {
      this.authenticationToken = sessionManager.retrieveAuthenticationToken();
      sessionManager.registerSession();
    }

    Image icon =
        new Image(Objects.requireNonNull(getClass().getResourceAsStream("/cloud-computing.png")));

    stage.getIcons().add(icon);
    stage.setTitle("Cloud");
    stage.setWidth(750);
    stage.setHeight(400);

    FXMLLoader mainLoader = new FXMLLoader();
    mainLoader.setLocation(mainSceneLocation);
    mainLoader.setController(new MainSceneController(this));

    FXMLLoader loginLoader = new FXMLLoader();
    loginLoader.setLocation(loginSceneLocation);
    loginLoader.setController(
        new LoginSceneController(sessionManager, cloudClient, stage, mainLoader));

    try {
      stage.setScene(
          new Scene(sessionManager.isSessionActive() ? mainLoader.load() : loginLoader.load()));
    } catch (IOException e) {
      e.printStackTrace();
    }

    stage.show();

    stage.setOnCloseRequest(
        w -> {
          channel.disconnect();
          channel.close();
        });

    Executors.newSingleThreadExecutor(
            r -> {
              Thread thread = new Thread(r);
              thread.setDaemon(true);
              return thread;
            })
        .execute(
            () -> {
              try {
                channel.closeFuture().sync();
              } catch (Exception e) {
                e.printStackTrace();
              }

              group.shutdownGracefully();
            });
  }
}
