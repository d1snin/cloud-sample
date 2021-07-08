package xyz.d1snin.server.commands.fsmanagement;

import xyz.d1snin.server.commands.ServerCommand;
import xyz.d1snin.server.commands.entities.Argument;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class CatCommand extends ServerCommand {

  public CatCommand() {
    this.usage = "cat";

    exec(
        e -> {
          Path resolvedPath =
              e.getWorkingDirectory().resolvePathSafe(e.getContentArgs().get(1), e);

          if (!Files.exists(resolvedPath)) {
            e.sendMessageSafe("Path does not exists: " + resolvedPath);
            return;
          }

          if (Files.isDirectory(resolvedPath)) {
            e.sendMessageSafe("Not a file: " + resolvedPath);
            return;
          }

          if (!Files.isReadable(resolvedPath)) {
            e.sendMessageSafe("File is not readable: " + resolvedPath);
            return;
          }

          StringBuilder builder = new StringBuilder();
          try {
            Files.readAllLines(resolvedPath, StandardCharsets.UTF_8)
                .forEach(it -> builder.append(it).append("\n"));
          } catch (IOException ioException) {
            ioException.printStackTrace();
          }

          e.sendMessageSafe(builder.toString());
        },
        new Argument(null, "<File name>", true, false));
  }
}
