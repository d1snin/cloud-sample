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
import lombok.extern.slf4j.Slf4j;
import xyz.d1snin.cloud.api.Cloud;
import xyz.d1snin.commons.managers.ClientSessionsManager;
import xyz.d1snin.commons.server.CloudServer;
import xyz.d1snin.server.handlers.RequestHandler;

@Slf4j
public class CloudServerImpl implements CloudServer {

  private final int port;
  private final Cloud cloud;
  private final ClientSessionsManager clientSessionsManager;

  private Channel channel;

  public CloudServerImpl(int port, Cloud cloud, ClientSessionsManager clientSessionsManager) {
    this.port = port;
    this.cloud = cloud;
    this.clientSessionsManager = clientSessionsManager;
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
  public ClientSessionsManager getClientSessionsManager() {
    return clientSessionsManager;
  }

  @Override
  public void launch() {

    EventLoopGroup auth = new NioEventLoopGroup(1);
    EventLoopGroup worker = new NioEventLoopGroup();

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
