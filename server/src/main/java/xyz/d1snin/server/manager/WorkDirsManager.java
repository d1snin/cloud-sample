package xyz.d1snin.server.manager;

import xyz.d1snin.server.entities.WorkingDirectory;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

public class WorkDirsManager {
  private static final Map<SocketChannel, WorkingDirectory> workDirs = new HashMap<>();

  public static WorkingDirectory getWorkingDirectory(SocketChannel channel) {
    return workDirs.get(channel);
  }

  public static void createNewWorkingDirectory(SocketChannel channel) {
    workDirs.put(channel, new WorkingDirectory());
  }
}
