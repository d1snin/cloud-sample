package xyz.d1snin.server.api.events.channel;

import xyz.d1snin.server.api.CloudServer;

import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Ивент подключения пользователя к серверу
 *
 * @author Mikhail Titov
 * @since 1.0.0
 */
public class ChannelConnectionEvent extends ChannelEvent {

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
  public ChannelConnectionEvent(
      CloudServer server, ServerSocketChannel serverSocket, SocketChannel socket) {
    super(server, serverSocket, socket);
    this.socket = socket;
  }

  /**
   * Используется для получения информации о том, подключен ли пользователь к серверу.
   *
   * @return true, если пользователь подключен, false иначе.
   * @since 1.0.0
   */
  public boolean isConnected() {
    return !socket.isConnected();
  }
}
