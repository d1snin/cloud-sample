package xyz.d1snin.cloud.api;

import com.mongodb.MongoClient;
import xyz.d1snin.cloud.internal.CloudImpl;
import xyz.d1snin.cloud.internal.utils.Checks;

import java.net.UnknownHostException;

/**
 * Используется для создания и конфигурации инстанса облака.
 *
 * @author Mikhail Titov
 * @since 1.0.0
 */
public class CloudBuilder {
  private String storageName = "storage";
  private String usersCollectionName = "users";
  private String hostName = "localhost";
  private int port = 27017;
  private String dbName = "cloud";

  /**
   * Конфигурация имени папки, в которой будет хранится содержимое облака. Значение по умолчанию -
   * storage.
   *
   * @param storageName Имя для папки
   * @throws IllegalArgumentException Если <b>storageName</b> будет пустым или же если
   *     <b>storageName</b> будет null.
   * @return {@link CloudBuilder}
   * @since 1.0.0
   */
  public CloudBuilder setStorageName(String storageName) throws IllegalArgumentException {
    Checks.checkNotEmpty(storageName, "Storage name");
    Checks.checkNotNull(storageName, "Storage name");
    this.storageName = storageName;
    return this;
  }

  /**
   * Конфигурация имени для коллекции пользователей в базе данных MongoDB. Значение по умолчанию -
   * users
   *
   * @param usersCollectionName Имя для коллекции пользователей.
   * @throws IllegalArgumentException Если <b>usersCollectionName</b> будет пустым или же если *
   *     <b>usersCollectionName</b> будет null.
   * @return {@link CloudBuilder}
   * @since 1.0.0
   */
  public CloudBuilder setUsersCollectionName(String usersCollectionName)
      throws IllegalArgumentException {
    Checks.checkNotEmpty(usersCollectionName, "Collection name");
    Checks.checkNotNull(usersCollectionName, "Collection name");
    this.usersCollectionName = usersCollectionName;
    return this;
  }

  /**
   * Конфигурация имени хоста для базы данных MongoDB. Значение по умолчанию - localhost.
   *
   * @param hostName Имя хоста
   * @throws IllegalArgumentException Если <b>hostName</b> будет пустым или же если <b>hostName</b>
   *     будет null.
   * @return {@link CloudBuilder}
   * @since 1.0.0
   */
  public CloudBuilder setHostName(String hostName) throws IllegalArgumentException {
    Checks.checkNotEmpty(hostName, "Host name");
    Checks.checkNotNull(hostName, "Host name");
    this.hostName = hostName;
    return this;
  }

  /**
   * Конфигурация порта для базы данных MongoDB. Значение по умолчанию - 27017
   *
   * @param port Порт для MongoDB
   * @return {@link CloudBuilder}
   * @since 1.0.0
   */
  public CloudBuilder setPort(int port) {
    this.port = port;
    return this;
  }

  /**
   * Конфигурация имени для базы данных, если база данных уже создана, будет создана новая база
   * данных с указанным именем.
   *
   * @param dbName Имя для базы данных MongoDB;
   * @throws IllegalArgumentException Если <b>dbName</b> будет пустым или же если <b>dbName</b>
   *     будет null.
   * @return {@link CloudBuilder}
   * @since 1.0.0
   */
  public CloudBuilder setDbName(String dbName) throws IllegalArgumentException {
    Checks.checkNotEmpty(dbName, "Database name");
    Checks.checkNotNull(dbName, "Database name");
    this.dbName = dbName;
    return this;
  }

  /**
   * Создает инстанс {@link Cloud}, готовый к использованию.
   *
   * @throws UnknownHostException Если хост базы данных не будет найден.
   * @return Созданный инстанс {@link Cloud}
   * @since 1.0.0
   */
  public Cloud buildCloudInstance() throws UnknownHostException {
    return new CloudImpl(
        storageName, usersCollectionName, new MongoClient(hostName, port).getDB(dbName));
  }
}
