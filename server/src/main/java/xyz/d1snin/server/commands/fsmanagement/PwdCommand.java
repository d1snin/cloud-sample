package xyz.d1snin.server.commands.fsmanagement;

import xyz.d1snin.server.commands.ServerCommand;

public class PwdCommand extends ServerCommand {

  public PwdCommand() {
    this.usage = "pwd";

    exec(e -> e.sendMessageSafe(e.getWorkingDirectory().getCurrentDirectory().toString()));
  }
}
