package xyz.d1snin.server.api.events;

import xyz.d1snin.cloud.internal.utils.Checks;
import xyz.d1snin.server.api.CloudServer;

/**
 * Главный класс в иерархии ивентов. Каждый ивент наследует этот класс.
 *
 * @author Mikhail Titov
 * @since 1.0.0
 */
public abstract class Event {

  private final CloudServer server;

  /**
   * Создает новый инстанс ивента.
   *
   * @param server Текущий инстанс {@link CloudServer}
   * @throws IllegalArgumentException Если <b>server</b> будет null.
   * @since 1.0.0
   */
  public Event(CloudServer server) {
    Checks.checkNotNull(server, "Server");
    this.server = server;
  }

  /**
   * Используется для получения текущего инстанса {@link CloudServer}
   *
   * @return Текущий инстанс {@link CloudServer}
   * @since 1.0.0
   */
  public CloudServer getServer() {
    return server;
  }
}
