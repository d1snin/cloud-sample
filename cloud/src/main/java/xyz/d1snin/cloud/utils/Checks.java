package xyz.d1snin.cloud.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

public class Checks {
  public static void checkNotEmpty(String str, String name) {
    if (str.isEmpty()) {
      throw new IllegalArgumentException(name + " can not be empty.");
    }
  }

  public static void checkNotEmpty(Collection<?> collection, String name) {
    if (collection.isEmpty()) {
      throw new IllegalArgumentException(name + " can not be empty.");
    }
  }

  public static void checkNotNull(Object obj, String name) {
    if (obj == null) {
      throw new IllegalArgumentException(name + " can not be null.");
    }
  }

  public static void checkExists(Path file, String name) {
    if (!Files.exists(file)) {
      throw new IllegalStateException(name + " does not exists.");
    }
  }

  public static void checkNotExists(Path file, String name) {
    if (Files.exists(file)) {
      throw new IllegalArgumentException(name + " already exists.");
    }
  }
}
