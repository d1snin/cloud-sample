package xyz.d1snin.server.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.d1snin.cloud.api.Cloud;
import xyz.d1snin.server.api.CloudServer;
import xyz.d1snin.server.api.CloudServerStatus;
import xyz.d1snin.server.api.events.ServerEnabledEvent;
import xyz.d1snin.server.api.events.channel.ChannelConnectionEvent;
import xyz.d1snin.server.api.events.channel.ChannelMessageReceivedEvent;
import xyz.d1snin.server.internal.events.EventManager;
import xyz.d1snin.server.manager.WorkDirsManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class CloudServerImpl implements CloudServer {

  private final int port;
  private final Cloud cloud;
  private final ForkJoinPool executor;
  private final EventManager eventManager;
  private final ServerSocketChannel serverSocketChannel;
  private final Selector selector;
  private final ByteBuffer buffer;
  private final Logger log = LoggerFactory.getLogger("Cloud Server");
  private CloudServerStatus status;

  public CloudServerImpl(int port, Cloud cloud, ForkJoinPool executor, EventManager eventManager)
      throws IOException {
    this.port = port;
    this.cloud = cloud;
    this.executor = executor;
    this.eventManager = eventManager;
    status = CloudServerStatus.DISABLED;
    serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.bind(new InetSocketAddress(port));
    serverSocketChannel.configureBlocking(false);
    selector = Selector.open();
    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    buffer = ByteBuffer.allocate(1024);
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
  public CloudServerStatus getServerStatus() {
    return status;
  }

  @Override
  public Logger getLogger() {
    return log;
  }

  @Override
  public void launch() {
    status = CloudServerStatus.ENABLED;

    ForkJoinTask<?> task =
        executor.submit(
            () -> {
              while (serverSocketChannel.isOpen()) {
                try {
                  selector.select();
                } catch (IOException e) {
                  log.error(
                      "Something went wrong while trying to select a set of keys whose corresponding channels are ready for I/O operations.");
                  e.printStackTrace();
                }

                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                while (iterator.hasNext()) {

                  SelectionKey key = iterator.next();

                  if (key.isAcceptable()) {
                    try {
                      SocketChannel channel = serverSocketChannel.accept();
                      channel.configureBlocking(false);
                      channel.register(selector, SelectionKey.OP_READ);

                      WorkDirsManager.createNewWorkingDirectory(channel);

                      eventManager.post(
                          new ChannelConnectionEvent(this, serverSocketChannel, channel));
                    } catch (IOException e) {
                      log.error("An I/O exception occurred while trying to accept connection.");
                    }
                  }

                  if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();

                    try {
                      StringBuilder builder = new StringBuilder();

                      out:
                      while (channel.isOpen() && channel.isConnected()) {
                        int read = channel.read(buffer);

                        switch (read) {
                          case -1:
                            channel.close();
                            break out;
                          case 0:
                            buffer.flip();

                            while (buffer.hasRemaining()) {
                              builder.append((char) buffer.get());
                            }

                            buffer.clear();
                            break out;
                        }
                      }

                      if (channel.isOpen()) {
                        eventManager.post(
                            new ChannelMessageReceivedEvent(
                                this, serverSocketChannel, channel, builder.toString()));
                      }
                    } catch (IOException e) {
                      log.error("An I/O error occurred while trying to read data from channel.");
                      e.printStackTrace();
                    }
                  }

                  iterator.remove();
                }
              }
            });

    log.info("The server is successfully started and listens to events on the port " + port);
    eventManager.post(new ServerEnabledEvent(this));
    task.join();
  }
}
