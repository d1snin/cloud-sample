package xyz.d1snin.server.commands.fsmanagement;

import xyz.d1snin.server.commands.ServerCommand;
import xyz.d1snin.server.commands.entities.Argument;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TouchCommand extends ServerCommand {

  public TouchCommand() {
    this.usage = "touch";

    exec(
        e -> {
          Path resolvedPath = e.getWorkingDirectory().resolvePathSafe(e.getContentArgs().get(1), e);

          if (Files.exists(resolvedPath)) {
            e.sendMessageSafe("This file already exists!");
            return;
          }

          try {
            Files.createFile(resolvedPath);
          } catch (IOException ioException) {
            ioException.printStackTrace();
          }
        },
        new Argument(null, "<File name>", true, false));
  }
}
