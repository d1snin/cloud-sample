package xyz.d1snin.client.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
public class AppStorage {

  private final Path storageFolderPath;
  private final Path baseConfigPath;
  private final Gson gson;
  private final JsonWriter configWriter;
  private final JsonReader configReader;

  @SneakyThrows
  public AppStorage(String name) {
    this.gson = new GsonBuilder().setPrettyPrinting().create();

    this.storageFolderPath = Paths.get(System.getProperty("user.home"), "." + name);

    if (!Files.exists(storageFolderPath)) {
      Files.createDirectory(storageFolderPath);
    }

    this.baseConfigPath = Paths.get(storageFolderPath.toAbsolutePath().toString(), "config.json");

    if (!Files.exists(baseConfigPath)) {
      Files.createFile(baseConfigPath);
    }

    this.configWriter = gson.newJsonWriter(new FileWriter(baseConfigPath.toFile()));
    this.configReader = gson.newJsonReader(new FileReader(baseConfigPath.toFile()));
  }
}
