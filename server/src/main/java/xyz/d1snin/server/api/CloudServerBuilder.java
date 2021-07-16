package xyz.d1snin.server.api;

import xyz.d1snin.cloud.api.Cloud;
import xyz.d1snin.cloud.internal.utils.Checks;
import xyz.d1snin.server.api.events.hooks.EventListener;
import xyz.d1snin.server.internal.CloudServerImpl;
import xyz.d1snin.server.internal.events.EventManager;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

/**
 * Позволяет создать и сконфигурировать сервер для работы с облаком.
 *
 * @author Mikhail Titov
 * @since 1.0.0
 */
public class CloudServerBuilder {

  private final Set<EventListener> listeners = new LinkedHashSet<>();
  private final ForkJoinPool executor = ForkJoinPool.commonPool();
  private int port = 8080;
  private Cloud cloud;

  /**
   * Используется для конфигурации порта на котором будет запущен инстанс сервера.
   *
   * @param port Порт на котором сервер будет слушать вхдящую информацию
   * @return {@link CloudServerBuilder}
   * @since 1.0.0
   */
  public CloudServerBuilder setPort(int port) {
    this.port = port;
    return this;
  }

  /**
   * Используется для конфигурации облака поверх которого работает сервер.
   *
   * @param cloud Сконфигурированный инстанс облака.
   * @throws IllegalArgumentException Если <b>cloud</b> будет null.
   * @return {@link CloudServerBuilder}
   * @since 1.0.0
   */
  public CloudServerBuilder setCloud(Cloud cloud) throws IllegalArgumentException {
    Checks.checkNotNull(cloud, "Cloud");
    this.cloud = cloud;
    return this;
  }

  /**
   * Используется для добавления листенеров.
   *
   * @param listeners Листенеры, имплементирующие {@link EventListener}
   * @throws IllegalArgumentException Если один из листенеров будет null или не будет передано не
   *     одного листенера.
   * @return {@link CloudServerBuilder}
   * @since 1.0.0
   */
  public CloudServerBuilder setEventListeners(EventListener... listeners) {
    Checks.checkNotNull(listeners, "Listeners");
    List<EventListener> list = Arrays.asList(listeners);
    Checks.checkNotEmpty(list, "Listeners");
    list.forEach(it -> Checks.checkNotNull(it, "Event listener"));
    this.listeners.addAll(list);
    return this;
  }

  /**
   * Собирает и возвращает сконфигурированный инстанс {@link CloudServer}
   *
   * @throws IllegalArgumentException Если {@link Cloud} будет null.
   * @throws IOException Если произошла ошибка при запуске сервера.
   * @return Сконфигурированный инстанс {@link CloudServer}
   * @since 1.0.0
   */
  public CloudServer buildServerInstance() throws IllegalArgumentException, IOException {
    return buildServerInstance(false);
  }

  /**
   * Собирает и возвращает сконфигурированный инстанс {@link CloudServer}
   *
   * @param delayedLaunch Если true, то сервер не будет запущен, нужно будет запустить сервер с
   *     помощью метода <b>launch()</b>, находящийся в {@link Cloud}
   * @throws IOException Если произошла ошибка при запуске сервера.
   * @return Сконфигурированный инстанс {@link CloudServer}
   * @since 1.0.0
   */
  public CloudServer buildServerInstance(boolean delayedLaunch) throws IOException {
    Checks.checkNotNull(cloud, "Cloud");
    CloudServer server =
        new CloudServerImpl(port, cloud, executor, new EventManager(listeners, executor));

    if (!delayedLaunch) {
      server.launch();
    }

    return server;
  }
}
