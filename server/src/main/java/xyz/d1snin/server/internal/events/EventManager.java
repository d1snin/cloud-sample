package xyz.d1snin.server.internal.events;

import xyz.d1snin.server.api.events.Event;
import xyz.d1snin.server.api.events.hooks.EventListener;

import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class EventManager {

  private final Set<EventListener> listeners;
  private final ForkJoinPool executor;

  public EventManager(Set<EventListener> listeners, ForkJoinPool executor) {
    this.listeners = listeners;
    this.executor = executor;
  }

  public void post(Event event) {
    listeners.forEach(it -> executor.execute(() -> it.onEvent(event)));
  }
}
