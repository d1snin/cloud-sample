package xyz.d1snin.server.commands.fsmanagement;

import xyz.d1snin.server.commands.ServerCommand;
import xyz.d1snin.server.commands.entities.Argument;

import java.io.File;

public class CdCommand extends ServerCommand {

  public CdCommand() {
    this.usage = "cd";

    exec(
        e ->
            e.getWorkingDirectory()
                .setCurrentDirectory(new File(e.getContentArgs().get(1)).toPath(), e),
        new Argument(null, "<Directory>", true, false));
  }
}
