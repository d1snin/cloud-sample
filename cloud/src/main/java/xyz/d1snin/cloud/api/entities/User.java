package xyz.d1snin.cloud.api.entities;

/**
 * Объект пользователя, использующийся для выполнения связанных с ним операций.
 *
 * @author Mikhail Titov
 * @since 1.0.0
 */
public interface User {

  /**
   * Используется для доступа к хранилищу юзера
   *
   * @return {@link Storage} этого инстанса юзера
   * @since 1.0.0
   */
  Storage getUserStorage();

  /**
   * Используется для получения уникального идентификатора пользователя.
   *
   * @return Уникальный идентификатор пользователя
   * @since 1.0.0
   */
  String getUserId();

  /**
   * Используется для получения логина пользователя.
   *
   * @return Логин пользователя.
   * @since 1.0.0
   */
  String getUserLogin();

  /**
   * Используется для поучения пароля пользователя.
   *
   * @return Пароль пользователя.
   * @since 1.0.0
   */
  String getUserPassword();
}
