package xyz.d1snin.cloud.api;

import java.nio.file.Path;

public interface Storage {

  User getUser();

  Path getPath();

  Path createFile(Path path) throws IllegalArgumentException;

  Path createDirectory(Path path) throws IllegalArgumentException;

  Path resolvePath(Path path);

  Path resolvePath(String path);

  void deleteStorage() throws IllegalStateException;
}
