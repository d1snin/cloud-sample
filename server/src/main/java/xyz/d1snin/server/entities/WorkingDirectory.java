package xyz.d1snin.server.entities;

import xyz.d1snin.server.api.events.channel.ChannelMessageReceivedEvent;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class WorkingDirectory {

  private Path currentDirectory;

  public WorkingDirectory() {
    currentDirectory = new File(System.getProperty("user.dir")).toPath();
  }

  public Path getCurrentDirectory() {
    return currentDirectory;
  }

  public void setCurrentDirectory(Path currentDirectory, ChannelMessageReceivedEvent event) {
    Path resolvedPath = this.currentDirectory.resolve(currentDirectory);

    if (!Files.exists(resolvedPath)) {
      event.sendMessageSafe("Path does not exists: " + resolvedPath);
      return;
    }

    if (!Files.isDirectory(resolvedPath)) {
      event.sendMessageSafe("Not a directory: " + resolvedPath);
      return;
    }

    this.currentDirectory = resolvedPath;
  }

  public Path resolvePathSafe(String other, ChannelMessageReceivedEvent event) {
    try {
      return currentDirectory.resolve(other);
    } catch (InvalidPathException e) {
      event.sendMessageSafe("Can not resolve this path: " + other);
    }
    return null;
  }
}
