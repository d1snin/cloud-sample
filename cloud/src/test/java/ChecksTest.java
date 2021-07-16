import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import xyz.d1snin.cloud.utils.Checks;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

@Slf4j
public class ChecksTest {

  private static Path testFile;

  @BeforeAll
  public static void prepareFile() {
    testFile = Paths.get("testFile.txt");

    if (!Files.exists(testFile)) {
      try {
        Files.createFile(testFile);
      } catch (IOException e) {
        if (e instanceof FileAlreadyExistsException) {
          return;
        }
        e.printStackTrace();
      }
    }
  }

  @Test
  public static void checkNotEmptyTest1() {
    Assertions.assertThrows(
        IllegalArgumentException.class, () -> Checks.checkNotEmpty("", "Empty parameter"));
    Assertions.assertDoesNotThrow(() -> Checks.checkNotEmpty("param", "Some parameter"));
  }

  public static void checkNotEmptyTest2() {
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> Checks.checkNotEmpty(Collections.emptyList(), "Empty collection"));
    Assertions.assertDoesNotThrow(
        () -> Checks.checkNotEmpty(Arrays.asList(1, 2, 3), "Not empty collection"));
  }

  @Test
  public static void checkNotNullTest() {
    Assertions.assertThrows(
        IllegalArgumentException.class, () -> Checks.checkNotNull(null, "Null parameter"));
  }

  @Test
  public static void checkExistsTest() {
    Assertions.assertThrows(
        IllegalStateException.class,
        () -> Checks.checkExists(Paths.get("random bullshit go!!!"), "File"));
    Assertions.assertDoesNotThrow(() -> Checks.checkExists(testFile, "Test file"));
  }

  @Test
  public static void checkNotExistsTest() {
    Assertions.assertThrows(
        IllegalArgumentException.class, () -> Checks.checkNotExists(testFile, "Test file"));
    Assertions.assertDoesNotThrow(
        () -> Checks.checkNotExists(Paths.get("random bullshit go!!!"), "File"));
  }

  @AfterAll
  public static void deleteFile() {
    try {
      Files.delete(testFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
