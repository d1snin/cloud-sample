package xyz.d1snin.server.api.events.channel;

import xyz.d1snin.cloud.internal.utils.Checks;
import xyz.d1snin.server.api.CloudServer;
import xyz.d1snin.server.api.events.Event;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * Главный класс в иерархии ивентов, которые связаны с событиями происходящими в {@link
 * java.net.ServerSocket}
 *
 * @author Mikhail Titov
 * @since 1.0.0
 */
public abstract class ChannelEvent extends Event {

  private final ServerSocketChannel serverSocket;
  private final SocketChannel socket;

  /**
   * Создает новый инстанс ивента.
   *
   * @param server Текущий инстанс {@link CloudServer}
   * @param serverSocket Текущий сокет сервера.
   * @param socket Сокет подключения.
   * @throws IllegalArgumentException Если <b>server</b>, <b>serverSocket</b> или <b>channel</b>
   *     будут null.
   * @since 1.0.0
   */
  public ChannelEvent(CloudServer server, ServerSocketChannel serverSocket, SocketChannel socket) {
    super(server);
    Checks.checkNotNull(serverSocket, "Server channel");
    Checks.checkNotNull(socket, "Socket Channel");
    this.serverSocket = serverSocket;
    this.socket = socket;
  }

  /**
   * Используется для получения инстанса {@link ServerSocketChannel} на котором запущен сервер.
   *
   * @return Инстана {@link ServerSocketChannel} на котором запущен сервер.
   * @since 1.0.0
   */
  public ServerSocketChannel getServerSocket() {
    return serverSocket;
  }

  /**
   * Используется для получения инстанса {@link SocketChannel} этого евента.
   *
   * @return Инстанс {@link SocketChannel} этого евента.
   * @since 1.0.0
   */
  public SocketChannel getSocket() {
    return socket;
  }

  /**
   * Используется для отправки сообщений в сокет.
   *
   * @param content Контент сообщения
   * @since 1.0.0
   */
  public void sendMessageSafe(String content) {
    try {
      socket.write(ByteBuffer.wrap((content + "\n").getBytes(StandardCharsets.UTF_8)));
    } catch (IOException e) {
      getServer()
          .getLogger()
          .error("An I/O error occurred while trying to send message to socket.");
      e.printStackTrace();
    }
  }
}
