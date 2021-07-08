package xyz.d1snin.server.api.events;

import xyz.d1snin.server.api.CloudServer;

/**
 * Ивент успешного включения сервера.
 *
 * @author Mikhail Titov
 * @since 1.0.0
 */
public class ServerEnabledEvent extends Event {

  /**
   * Используется для создание нового инстанса ивента, который отображает то, что сервер был успешно
   * включен.
   *
   * @param server Текущий инстанс {@link CloudServer}
   * @throws IllegalArgumentException Если <b>server</b> будет null.
   * @since 1.0.0
   */
  public ServerEnabledEvent(CloudServer server) {
    super(server);
  }
}
