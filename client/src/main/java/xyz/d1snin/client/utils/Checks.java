package xyz.d1snin.client.utils;

public class Checks {
  public static void checkNotNull(Object obj, String name) {
    if (obj == null) {
      throw new IllegalArgumentException(name + " can not be null.");
    }
  }

  public static void checkNotEmpty(String str, String name) {
    if (str.isEmpty()) {
      throw new IllegalArgumentException(name = " can not be empty.");
    }
  }
}
