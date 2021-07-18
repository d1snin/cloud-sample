package xyz.d1snin.client.internal;

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
import io.netty.handler.ssl.SslHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import xyz.d1snin.client.api.CloudClient;
import xyz.d1snin.client.api.managers.RequestManager;
import xyz.d1snin.client.api.managers.SessionManager;
import xyz.d1snin.client.controllers.LoginSceneController;
import xyz.d1snin.client.controllers.MainSceneController;
import xyz.d1snin.client.handlers.ResponseHandler;
import xyz.d1snin.client.internal.managers.RequestManagerImpl;
import xyz.d1snin.client.internal.managers.SessionManagerImpl;
import xyz.d1snin.client.storage.AppStorage;
import xyz.d1snin.commons.utils.SslUtils;

import javax.net.ssl.SSLEngine;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

public class CloudClientImpl implements CloudClient {

  private final String host;
  private final int port;
  private final Stage stage;
  private final AppStorage appStorage;
  private final InputStream sslKeyStore;
  private final String sslKeyPass;
  private final URL mainSceneLocation;
  private final URL loginSceneLocation;

  private Channel channel;
  private RequestManager requestManager;
  private SessionManager sessionManager;

  public CloudClientImpl(
      String host,
      int port,
      Stage stage,
      AppStorage appStorage,
      InputStream sslKeyStore,
      String sslKeyPass,
      URL mainSceneLocation,
      URL loginSceneLocation) {
    this.host = host;
    this.port = port;
    this.stage = stage;
    this.appStorage = appStorage;
    this.sslKeyStore = sslKeyStore;
    this.sslKeyPass = sslKeyPass;
    this.mainSceneLocation = mainSceneLocation;
    this.loginSceneLocation = loginSceneLocation;
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
  public AppStorage getAppStorage() {
    return appStorage;
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
  public void launch() {
    SSLEngine engine =
        SslUtils.createSslContext(sslKeyStore, sslKeyPass).createSSLEngine(host, port);
    engine.setUseClientMode(true);

    EventLoopGroup group = new NioEventLoopGroup();
    Bootstrap boot = new Bootstrap();

    boot.group(group)
        .channel(NioSocketChannel.class)
        .handler(
            new ChannelInitializer<SocketChannel>() {

              @Override
              protected void initChannel(SocketChannel ch) {
                ch.pipeline()
                    .addLast(
                        new SslHandler(engine),
                        new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                        new ObjectEncoder(),
                        new ResponseHandler(requestManager));
              }
            });

    try {
      this.channel = boot.connect(host, port).sync().channel();
    } catch (InterruptedException e) {
      e.printStackTrace();
      return;
    }

    this.requestManager = new RequestManagerImpl(channel);
    this.sessionManager = new SessionManagerImpl(requestManager);

    FXMLLoader loader = new FXMLLoader();
    Image icon =
        new Image(Objects.requireNonNull(getClass().getResourceAsStream("/cloud-computing.png")));

    stage.getIcons().add(icon);
    stage.setTitle("Cloud");
    stage.setWidth(750);
    stage.setHeight(400);

    if (sessionManager.isAuthenticated()) {
      loader.setLocation(mainSceneLocation);
      loader.setController(new MainSceneController(requestManager));

    } else {
      loader.setLocation(loginSceneLocation);
      loader.setController(new LoginSceneController(sessionManager));
    }

    try {
      stage.setScene(new Scene(loader.load()));
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }

    stage.show();
  }
}
