package xyz.d1snin.server.commands;

public class PingCommand extends ServerCommand {

  public PingCommand() {
    this.usage = "ping";

    exec(e -> e.sendMessageSafe("Pong!"));
  }
}
