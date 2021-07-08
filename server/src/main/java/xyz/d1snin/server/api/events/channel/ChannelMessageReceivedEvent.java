package xyz.d1snin.server.api.events.channel;

import xyz.d1snin.cloud.internal.utils.Checks;
import xyz.d1snin.server.api.CloudServer;
import xyz.d1snin.server.entities.WorkingDirectory;
import xyz.d1snin.server.manager.WorkDirsManager;

import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.List;

/**
 * Ивент получения нового сообщения из сокета на сервер.
 *
 * @author Mikhail Titov
 * @since 1.0.0
 */
public class ChannelMessageReceivedEvent extends ChannelEvent {

  private final String messageContent;
  private final SocketChannel channel;

  /**
   * Создает новый инстанс ивента.
   *
   * @param server Текущий инстанс {@link CloudServer}
   * @param serverSocket Текущий сокет сервера.
   * @param socket Сокет подключения.
   * @throws IllegalArgumentException Если <b>server</b>, <b>serverSocket</b>, <b>channel</b> или
   *     <b>messageContent</b> будут null.
   * @since 1.0.0
   */
  public ChannelMessageReceivedEvent(
      CloudServer server,
      ServerSocketChannel serverSocket,
      SocketChannel socket,
      String messageContent) {
    super(server, serverSocket, socket);
    Checks.checkNotNull(messageContent, "Message content");
    this.messageContent = messageContent;
    this.channel = socket;
  }

  /**
   * Используется для получения данных из потока байт в виде строки.
   *
   * @return Строка данных из потока.
   * @since 1.0.0
   */
  public String getContent() {
    return messageContent;
  }

  /**
   * Используется для получения списка всех аргументов сообщения.
   *
   * @return Экземпляр {@link List}, содержащий все аргументы сообщения
   * @since 1.0.0
   */
  public List<String> getContentArgs() {
    return Arrays.asList(messageContent.split("\\s+"));
  }

  /**
   * Позволяет получить текст из сообщения по индексу.
   *
   * @param index Начальный индекс.
   * @return Текст из сообщения по индексу.
   * @since 1.0.0
   */
  public String getContentByIndex(int index) {
    StringBuilder builder = new StringBuilder();
    List<String> args = getContentArgs();

    for (int i = index; i < args.size(); i++) {
      builder.append(args.get(i)).append(" ");
    }
    return builder.toString().trim();
  }

  /**
   * Позволяет получить текущую рабочую директорию пользователя на сервере.
   *
   * @return Инстанс {@link WorkingDirectory}
   * @since 1.0.0
   */
  public WorkingDirectory getWorkingDirectory() {
    return WorkDirsManager.getWorkingDirectory(channel);
  }
}
