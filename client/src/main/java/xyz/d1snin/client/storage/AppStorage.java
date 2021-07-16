package xyz.d1snin.client.storage;

import com.google.gson.stream.JsonWriter;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
public class AppStorage {

  private final Path storageFolderPath;
  private final Path baseConfigPath;
  private final JsonWriter configWriter;

  @SneakyThrows
  public AppStorage(String name) {
    storageFolderPath = Paths.get(System.getProperty("user.home"), "." + name);

    if (!Files.exists(storageFolderPath)) {
      Files.createDirectory(storageFolderPath);
    }

    baseConfigPath = Paths.get(storageFolderPath.toAbsolutePath().toString(), "config.json");

    if (!Files.exists(baseConfigPath)) {
      Files.createFile(baseConfigPath);
    }

    configWriter = new JsonWriter(new FileWriter(baseConfigPath.toFile()));
  }
}
