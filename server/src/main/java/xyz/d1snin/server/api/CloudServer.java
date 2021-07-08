package xyz.d1snin.server.api;

import org.slf4j.Logger;
import xyz.d1snin.cloud.api.Cloud;

/**
 * Ядро сервера, который работает с облаком. Все операции над сервером могут быть доступны из этого
 * класса.
 *
 * @author Mikhail Titov
 * @since 1.0.0
 */
public interface CloudServer {

  /**
   * Используется для получения порта, на котором запущен сервер.
   *
   * @return Числовое значение порта сервера
   * @since 1.0.0
   */
  int getPort();

  /**
   * Используется для получения облака поверх которого работает сервер.
   *
   * @return Инстанс {@link Cloud}
   * @since 1.0.0
   */
  Cloud getCloud();

  /**
   * Используется для получения текущего статуса сервера.
   *
   * @return Статус сервера в виде перечисления {@link CloudServerStatus}
   * @since 1.0.0
   */
  CloudServerStatus getServerStatus();

  /**
   * Используется для доступа к логгеру сервера.
   *
   * @return Инстанс логгера {@link Logger}
   * @since 1.0.0
   */
  Logger getLogger();

  /**
   * Используется для запуска сервера. Может быть вызвано только один раз.
   *
   * @since 1.0.0
   */
  void launch();
}
