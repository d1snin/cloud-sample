package xyz.d1snin.server.api.events.hooks;

import xyz.d1snin.server.api.events.Event;

/**
 * Каждый листенер ивентов должен имлементировать данный интерфейс.
 *
 * @author Mikhail Titov
 * @since 1.0.0
 */
public interface EventListener {

  /**
   * Метод, вызываемый при обработке нового евента.
   *
   * @param event Текущий евент
   * @since 1.0.0
   */
  void onEvent(Event event);
}
