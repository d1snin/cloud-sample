package xyz.d1snin.server.listeners;

import xyz.d1snin.server.api.events.Event;
import xyz.d1snin.server.api.events.hooks.EventListener;

public abstract class Listener<T extends Event> implements EventListener {

  protected abstract void execute(T event);

  @Override
  @SuppressWarnings("unchecked")
  public void onEvent(Event event) {
    try {
      execute((T) event);
    } catch (ClassCastException ignored) {
    }
  }
}
