package xyz.d1snin.server.commands.fsmanagement;

import xyz.d1snin.server.commands.ServerCommand;
import xyz.d1snin.server.commands.entities.Argument;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MkdirCommand extends ServerCommand {

  public MkdirCommand() {
    this.usage = "mkdir";

    exec(
        e -> {
          Path resolvedPath = e.getWorkingDirectory().resolvePathSafe(e.getContentArgs().get(1), e);

          if (Files.exists(resolvedPath)) {
            e.sendMessageSafe("Already exists: " + resolvedPath);
            return;
          }

          try {
            Files.createDirectories(resolvedPath);
          } catch (IOException ioException) {
            ioException.printStackTrace();
          }
        },
        new Argument(null, "<Directory>", true, false));
  }
}
