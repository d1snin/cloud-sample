package xyz.d1snin.cloud.internal;

import xyz.d1snin.cloud.api.Cloud;
import xyz.d1snin.cloud.api.Storage;
import xyz.d1snin.cloud.api.User;

public class UserImpl implements User {

  private final String id;
  private final String login;
  private final String password;
  private final Storage storage;

  public UserImpl(String id, String login, String password, Cloud cloud) {
    this.id = id;
    this.login = login;
    this.password = password;
    storage = new StorageImpl(this, cloud);
  }

  @Override
  public Storage getUserStorage() {
    return storage;
  }

  @Override
  public String getUserId() {
    return id;
  }

  @Override
  public String getUserLogin() {
    return login;
  }

  @Override
  public String getUserPassword() {
    return password;
  }
}
