package xyz.d1snin.server.listeners;

import xyz.d1snin.server.api.events.ServerEnabledEvent;

public class ServerEnabledListener extends Listener<ServerEnabledEvent> {
  @Override
  protected void execute(ServerEnabledEvent event) {
    event
        .getServer()
        .getLogger()
        .info("Server was successfully enabled! Hello from test event listener.");
  }
}
