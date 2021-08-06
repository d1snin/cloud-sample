package xyz.d1snin.cloud.internal;

import com.mongodb.*;
import lombok.extern.slf4j.Slf4j;
import xyz.d1snin.cloud.api.Cloud;
import xyz.d1snin.cloud.api.User;
import xyz.d1snin.cloud.utils.Checks;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class CloudImpl implements Cloud {

  private final Path storageDirectory;
  private final DBCollection usersCollection;

  public CloudImpl(String storageName, String usersCollectionName, DB db) {
    storageDirectory = Paths.get(System.getProperty("user.home"), ".cloud-sample", storageName);

    if (!Files.exists(storageDirectory)) {
      try {
        Files.createDirectories(storageDirectory);
        log.info("The storage was created at " + storageDirectory);

      } catch (IOException e) {
        log.error("An error occurred while trying to setup storage directory.");
        e.printStackTrace();
      }
    }

    usersCollection = db.getCollection(usersCollectionName);

    log.info("Cloud was successfully initialized");
  }

  @Override
  public Path getStorageDirectory() {
    Checks.checkExists(storageDirectory, "Storage directory");
    return storageDirectory;
  }

  @Override
  public List<User> getUsers() {
    List<User> tmp = new LinkedList<>();

    DBCursor cursor = usersCollection.find();

    while (cursor.hasNext()) {
      DBObject current = cursor.next();
      tmp.add(
          new UserImpl(
              (String) current.get("id"),
              (String) current.get("login"),
              (String) current.get("password"),
              (String) current.get("token"),
              this));
    }

    return tmp;
  }

  @Override
  public User createNewUser(String login, String password) throws IllegalArgumentException {
    Checks.checkNotNull(login, "Login");
    Checks.checkNotEmpty(login, "Login");
    Checks.checkNotNull(password, "Password");
    Checks.checkNotEmpty(password, "Password");

    String newUserId;

    do {
      newUserId = UUID.randomUUID().toString();
    } while (usersCollection.count(new BasicDBObject("id", newUserId)) > 0);

    byte[] randomBytes = new byte[24];
    new SecureRandom().nextBytes(randomBytes);
    String token = Base64.getUrlEncoder().encodeToString(randomBytes);

    User newUser = new UserImpl(newUserId, login, password, token, this);

    if (usersCollection.count(new BasicDBObject("login", login)) > 0) {
      throw new IllegalArgumentException("User with this login already exists.");
    }

    usersCollection.insert(
        new BasicDBObject()
            .append("id", newUser.getUserId())
            .append("login", newUser.getUserLogin())
            .append("password", newUser.getUserPassword())
            .append("token", newUser.getAuthenticationToken()));

    return newUser;
  }

  @Override
  public User loginUser(String login, String password) throws IllegalArgumentException {
    Checks.checkNotNull(login, "Login");
    Checks.checkNotEmpty(login, "Login");
    Checks.checkNotNull(password, "Password");
    Checks.checkNotEmpty(password, "Password");

    DBCursor cursor =
        usersCollection.find(
            new BasicDBObject().append("login", login).append("password", password));
    if (!cursor.hasNext()) {
      throw new IllegalArgumentException(
          "Can not login this user because login or password is invalid.");

    } else {
      DBObject user = cursor.next();

      return new UserImpl(
          (String) user.get("id"),
          (String) user.get("login"),
          (String) user.get("password"),
          (String) user.get("token"),
          this);
    }
  }

  @Override
  public User getUserById(String id) {
    Checks.checkNotNull(id, "ID");
    Checks.checkNotEmpty(id, "ID");

    return getUsers().stream().filter(it -> it.getUserId().equals(id)).findFirst().orElse(null);
  }

  @Override
  public User getUserByToken(String token) {
    Checks.checkNotNull(token, "Auth Token");
    Checks.checkNotEmpty(token, "Auth Token");

    return getUsers().stream()
        .filter(it -> it.getAuthenticationToken().equals(token))
        .findFirst()
        .orElse(null);
  }
}
