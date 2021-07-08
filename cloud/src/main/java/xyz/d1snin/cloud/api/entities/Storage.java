package xyz.d1snin.cloud.api.entities;

import java.io.File;
import java.io.IOException;

/**
 * Объект, использующийся для выполнения операций над хранилищем пользователя.
 *
 * @author Mikhail Titov
 * @since 1.0.0
 */
public interface Storage {

  /**
   * Используется для получения пользователя, ассоциирующегося с этим хранилищем.
   *
   * @return Инстанс {@link User} этого хранилища.
   * @since 1.0.0
   */
  User getUser();

  /**
   * Используется для получения полного пути к хранилищу пользователя.
   *
   * @return Полный путь к хранилищу пользователя в виде {@link File}.
   * @since 1.0.0
   */
  File getPath();

  /**
   * Используется для создания файла внутри хранилища пользователя.
   *
   * @param fileName Имя для файла, может также использоваться для создания иерархии файлов.
   * @throws IllegalArgumentException Если файл уже существует или если <b>fileName</b> будет пустым
   *     или null.
   * @throws IOException Если возникла ошибка связанная с I/O
   * @return Созданный {@link File}
   * @since 1.0.0
   */
  File createFile(String fileName) throws IOException, IllegalArgumentException;

  /**
   * Используется для создания новых директорий внутри хранилища пользователя.
   *
   * @param dirName Путь директории
   * @throws IllegalArgumentException Если директория уже создана или если <b>dirName</b> будет
   *     пустым или null
   * @return Созданная директория в виде {@link File}
   * @since 1.0.0
   */
  File createDirectory(String dirName) throws IllegalArgumentException;
  /**
   * Используется для полного удаления хранилища пользователя
   *
   * @throws IllegalStateException Если пользовательское хранилище не существует.
   * @since 1.0.0
   */
  void deleteStorage() throws IllegalStateException;
}
