package xyz.d1snin.cloud.api;

import xyz.d1snin.cloud.api.entities.User;

import java.io.File;
import java.util.List;

/**
 * Ядро облака, используется для управления им и получения доступа ко всем остальным элементам.
 *
 * @author Mikhail Titov
 * @since 1.0.0
 */
public interface Cloud {

  /**
   * Используется для получения объекта {@link File}, ассоциирующегося с директорией хранилища.
   *
   * @throws IllegalStateException Если директории не существует.
   * @return Экземпляр {@link File} директории хранилища облака.
   * @since 1.0.0
   */
  File getStorageDirectory() throws IllegalStateException;

  /**
   * Используется для получения списка всех пользователей облака.
   *
   * @return Лист, содержащий всех пользователей облака и представленный ввиде объектов {@link
   *     User}, или пустой лист, если в базе данных нет пользователей
   * @since 1.0.0
   */
  List<User> getUsers();

  /**
   * Создает нового пользователя и регистрирует его в базе данных.
   *
   * @param login Логин для нового пользователя.
   * @param password Пароль для нового пользователя.
   * @throws IllegalArgumentException Если либо <b>login</b>, либо <b>password</b> будут пустыми или
   *     null.
   * @throws IllegalAccessException Если логин уже занят.
   * @return Объект {@link User} созданного пользователя.
   * @since 1.0.0
   */
  User createNewUser(String login, String password)
      throws IllegalArgumentException, IllegalAccessException;

  /**
   * @param login Логин пользователя.
   * @param password Пароль пользователя.
   * @throws IllegalArgumentException Если либо <b>login</b>, либо <b>password</b> будут пустыми или
   *     null.
   * @throws IllegalAccessException Если не удалось войти в облако.
   * @return Инстанс {@link User} если вход произошел успешно.
   * @since 1.0.0
   */
  User loginUser(String login, String password)
      throws IllegalArgumentException, IllegalAccessException;

  /**
   * @param id Идентификатор пользователя
   * @throws IllegalArgumentException Если <b>id</b> будет пустым, null или же переданный id не
   *     будет ассоциироваться ни с одним из пользователей.
   * @return Объект {@link User}, ассоциирующийся с переданным id.
   * @since 1.0.0
   */
  User getUserById(String id) throws IllegalArgumentException;
}
