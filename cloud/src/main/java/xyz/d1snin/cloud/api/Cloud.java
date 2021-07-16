package xyz.d1snin.cloud.api;

import java.nio.file.Path;
import java.util.List;

public interface Cloud {

  Path getStorageDirectory() throws IllegalStateException;

  List<User> getUsers();

  User createNewUser(String login, String password)
      throws IllegalArgumentException, IllegalAccessException;

  User loginUser(String login, String password)
      throws IllegalArgumentException, IllegalAccessException;

  User getUserById(String id) throws IllegalArgumentException;
}
