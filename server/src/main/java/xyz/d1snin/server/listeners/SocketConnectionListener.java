package xyz.d1snin.server.listeners;

import xyz.d1snin.server.api.events.channel.ChannelConnectionEvent;

import java.io.IOException;

public class SocketConnectionListener extends Listener<ChannelConnectionEvent> {
  @Override
  protected void execute(ChannelConnectionEvent event) {
    try {
      event
          .getServer()
          .getLogger()
          .info(
              "The new user was connected from the address: {}",
              event.getSocket().getRemoteAddress());
    } catch (IOException e) {
      event
          .getServer()
          .getLogger()
          .error("An I/O error occurred while trying to get remote address from the socket.");
      e.printStackTrace();
    }

    event.sendMessageSafe("Welcome!");
  }
}
