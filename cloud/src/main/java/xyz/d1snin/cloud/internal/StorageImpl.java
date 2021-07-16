package xyz.d1snin.cloud.internal;

import lombok.extern.slf4j.Slf4j;
import xyz.d1snin.cloud.api.Cloud;
import xyz.d1snin.cloud.api.Storage;
import xyz.d1snin.cloud.api.User;
import xyz.d1snin.cloud.utils.Checks;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class StorageImpl implements Storage {

  private final User user;
  private final Path path;

  public StorageImpl(User user, Cloud cloud) {
    this.user = user;

    path = cloud.getStorageDirectory().toAbsolutePath().resolve(user.getUserId());

    if (!Files.exists(path)) {
      try {
        Files.createDirectories(path);

      } catch (IOException e) {
        e.printStackTrace();
        log.error(
            String.format(
                "An error occurred while trying to create user storage directory. (%s)",
                user.getUserId()));
      }
    }
  }

  @Override
  public User getUser() {
    return user;
  }

  @Override
  public Path getPath() {
    return path;
  }

  @Override
  public Path createFile(String fileName) throws IllegalArgumentException {
    Checks.checkNotNull(fileName, "File name");
    Checks.checkNotEmpty(fileName, "File name");

    Path res = null;

    try {
      res = Files.createFile(path.resolve(fileName));
    } catch (IOException e) {
      log.error(
          "An error occurred while tring to create a file in user storage. ({})", user.getUserId());
    }

    return res;
  }

  @Override
  public Path createDirectory(String dirName) throws IllegalArgumentException {
    Checks.checkNotNull(dirName, "Directory name");
    Checks.checkNotEmpty(dirName, "Directory name");

    Path res = null;
    try {
      res = Files.createDirectory(path.resolve(dirName));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return res;
  }

  @Override
  public void deleteStorage() {
    try {
      Files.delete(path);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
