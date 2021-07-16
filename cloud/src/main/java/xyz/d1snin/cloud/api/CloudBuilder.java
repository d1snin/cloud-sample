package xyz.d1snin.cloud.api;

import com.mongodb.MongoClient;
import xyz.d1snin.cloud.internal.CloudImpl;
import xyz.d1snin.cloud.utils.Checks;

import java.net.UnknownHostException;

public class CloudBuilder {
  private String storageName = "storage";
  private String usersCollectionName = "users";
  private String hostName = "localhost";
  private int port = 27017;
  private String dbName = "cloud";

  public CloudBuilder setStorageName(String storageName) throws IllegalArgumentException {
    Checks.checkNotEmpty(storageName, "Storage name");
    Checks.checkNotNull(storageName, "Storage name");
    this.storageName = storageName;
    return this;
  }

  public CloudBuilder setUsersCollectionName(String usersCollectionName)
      throws IllegalArgumentException {
    Checks.checkNotEmpty(usersCollectionName, "Collection name");
    Checks.checkNotNull(usersCollectionName, "Collection name");
    this.usersCollectionName = usersCollectionName;
    return this;
  }

  public CloudBuilder setHostName(String hostName) throws IllegalArgumentException {
    Checks.checkNotEmpty(hostName, "Host name");
    Checks.checkNotNull(hostName, "Host name");
    this.hostName = hostName;
    return this;
  }

  public CloudBuilder setPort(int port) {
    this.port = port;
    return this;
  }

  public CloudBuilder setDbName(String dbName) throws IllegalArgumentException {
    Checks.checkNotEmpty(dbName, "Database name");
    Checks.checkNotNull(dbName, "Database name");
    this.dbName = dbName;
    return this;
  }

  public Cloud buildCloudInstance() throws UnknownHostException {
    return new CloudImpl(
        storageName, usersCollectionName, new MongoClient(hostName, port).getDB(dbName));
  }
}
