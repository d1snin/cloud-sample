package xyz.d1snin.cloud.internal.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.d1snin.cloud.api.Cloud;
import xyz.d1snin.cloud.api.entities.Storage;
import xyz.d1snin.cloud.api.entities.User;
import xyz.d1snin.cloud.internal.utils.Checks;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class StorageImpl implements Storage {

  private final User user;
  private final File path;

  private final Logger log = LoggerFactory.getLogger(StorageImpl.class);

  public StorageImpl(User user, Cloud cloud) {
    this.user = user;

    path =
        new File(
            String.join(
                File.separator, cloud.getStorageDirectory().getAbsolutePath(), user.getUserId()));

    if (!path.exists()) {
      if (!path.mkdirs()) {
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
  public File getPath() {
    Checks.checkExists(path, "Storage directory");
    return path;
  }

  @Override
  public File createFile(String fileName) throws IOException {
    Checks.checkNotNull(fileName, "File name");
    Checks.checkNotEmpty(fileName, "File name");

    File file = new File(String.join(File.separator, getPath().getAbsolutePath(), fileName));
    Checks.checkNotExists(file, "File");

    if (fileName.contains(File.separator)) {
      List<String> path = Arrays.asList(fileName.split(File.separator));
      path.remove(path.size() - 1);

      if (!(new File(String.join(File.separator, path)).mkdirs())) {
        log.error("Something went wrong while trying to create directory.");
      }
    }

    if (!file.createNewFile()) {
      log.error("File already exists.");
    }

    return file;
  }

  @Override
  public File createDirectory(String dirName) {
    Checks.checkNotNull(dirName, "Directory name");
    Checks.checkNotEmpty(dirName, "Directory name");

    File dir = new File(dirName);
    Checks.checkNotExists(dir, "Directory");

    if (!dir.mkdirs()) {
      log.error("Something went wrong while trying to create directory.");
    }

    return dir;
  }

  @Override
  public void deleteStorage() {
    Checks.checkExists(path, "Storage directory");
    if (!path.delete()) {
      log.error("Something went wrong while trying to delete user storage.");
    }
  }
}
