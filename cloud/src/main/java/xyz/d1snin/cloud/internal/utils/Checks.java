package xyz.d1snin.cloud.internal.utils;

import java.io.File;
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

  public static void checkExists(File file, String name) {
    if (!file.exists()) {
      throw new IllegalStateException(name + " does not exists.");
    }
  }

  public static void checkNotExists(File file, String name) {
    if (file.exists()) {
      throw new IllegalArgumentException(name + " already exists.");
    }
  }
}
