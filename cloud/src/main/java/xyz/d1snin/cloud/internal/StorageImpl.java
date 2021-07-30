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
  public Path createFile(Path path) throws IllegalArgumentException {
    Checks.checkNotNull(path, "File path");

    Path res = null;

    try {
      res = Files.createFile(this.path.resolve(path));
    } catch (IOException e) {
      log.error(
          "An error occurred while tring to create a file in user storage. ({})", user.getUserId());
    }

    return res;
  }

  @Override
  public Path createDirectory(Path path) throws IllegalArgumentException {
    Checks.checkNotNull(path, "Directory path");

    Path res = null;
    try {
      res = Files.createDirectory(this.path.resolve(path));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return res;
  }

  @Override
  public Path resolvePath(Path path) {
    return this.path.resolve(path);
  }

  @Override
  public Path resolvePath(String path) {
    return this.path.resolve(path);
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
