package xyz.d1snin.client.utils;

public class Checks {
  public static void checkNotNull(Object obj, String name) {
    if (obj == null) {
      throw new IllegalArgumentException(name + " can not be null.");
    }
  }
}
