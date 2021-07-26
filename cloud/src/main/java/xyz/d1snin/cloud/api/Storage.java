package xyz.d1snin.cloud.api;

import java.io.IOException;
import java.nio.file.Path;

public interface Storage {

  User getUser();

  Path getPath();

  Path createFile(String fileName) throws IllegalArgumentException;

  Path createDirectory(String dirName) throws IllegalArgumentException;

  void deleteStorage() throws IllegalStateException;
}
