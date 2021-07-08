package xyz.d1snin.server.commands.fsmanagement;

import xyz.d1snin.server.commands.ServerCommand;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LsCommand extends ServerCommand {

  public LsCommand() {
    this.usage = "ls";

    exec(
        e -> {
          StringBuilder builder = new StringBuilder();
          Path currentDirectory = e.getWorkingDirectory().getCurrentDirectory();
          try {
            Files.walk(currentDirectory, 1)
                .filter(it -> !it.getFileName().equals(currentDirectory.getFileName()))
                .forEach(it -> builder.append(it.getFileName()).append(" "));
          } catch (IOException ioException) {
            ioException.printStackTrace();
          }
          e.sendMessageSafe(builder.toString());
        });
  }
}
