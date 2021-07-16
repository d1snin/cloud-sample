package xyz.d1snin.server.internal;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.ssl.SslHandler;
import lombok.extern.slf4j.Slf4j;
import xyz.d1snin.cloud.api.Cloud;
import xyz.d1snin.server.shared.utils.SslUtils;
import xyz.d1snin.server.api.CloudServer;

import javax.net.ssl.SSLEngine;
import java.io.InputStream;

@Slf4j
public class CloudServerImpl implements CloudServer {

  private final int port;
  private final Cloud cloud;
  private final InputStream keyStore;
  private final String sslKeyPass;

  private Channel channel;

  public CloudServerImpl(int port, Cloud cloud, InputStream keyStore, String sslKeyPass) {
    this.port = port;
    this.cloud = cloud;
    this.keyStore = keyStore;
    this.sslKeyPass = sslKeyPass;
  }

  @Override
  public int getPort() {
    return port;
  }

  @Override
  public Cloud getCloud() {
    return cloud;
  }

  @Override
  public void launch() {

    EventLoopGroup auth = new NioEventLoopGroup(1);
    EventLoopGroup worker = new NioEventLoopGroup();

    SSLEngine engine = SslUtils.createSslContext(keyStore, sslKeyPass).createSSLEngine();
    engine.setUseClientMode(false);

    try {
      CloudServer server = this;

      ServerBootstrap bootstrap =
          new ServerBootstrap()
              .group(auth, worker)
              .channel(NioServerSocketChannel.class)
              .childHandler(
                  new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) {
                      ch.pipeline()
                          .addLast(
                              new SslHandler(engine),
                              new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                              new ObjectEncoder(),
                              new RequestHandler(server));
                    }
                  });

      try {
        ChannelFuture future = bootstrap.bind(port).sync();

        log.info(
            "The server was successfully started and listens to incoming events on the port {}",
            port);

        channel = future.channel();
        channel.closeFuture().sync();
      } catch (InterruptedException e) {
        log.error("Thread was interrupted.");
        e.printStackTrace();
      }

    } finally {
      auth.shutdownGracefully();
      worker.shutdownGracefully();
    }
  }

  @Override
  public void shutdown() {
    channel.close();
  }
}
