import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import xyz.d1snin.cloud.internal.utils.Checks;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

@Slf4j
public class ChecksTest {

  private static File testFile;

  @BeforeAll
  public static void prepareFile() throws IOException {
    testFile = new File("testFile.txt");
    if (!testFile.createNewFile()) {
      log.warn("Test file already exists.");
    }
  }

  @Test
  public static void checkNotEmptyTest1() {
    Assertions.assertThrows(
        IllegalArgumentException.class, () -> Checks.checkNotEmpty("", "Empty parameter"));
    Assertions.assertDoesNotThrow(() -> Checks.checkNotEmpty("gnijedr", "Some parameter"));
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
        () -> Checks.checkExists(new File("random bullshit go!!!"), "File"));
    Assertions.assertDoesNotThrow(() -> Checks.checkExists(testFile, "Test file"));
  }

  @Test
  public static void checkNotExistsTest() {
    Assertions.assertThrows(
        IllegalArgumentException.class, () -> Checks.checkNotExists(testFile, "Test file"));
    Assertions.assertDoesNotThrow(
        () -> Checks.checkNotExists(new File("random bullshit go!!!"), "File"));
  }

  @AfterAll
  public static void deleteFile() {
    if (!testFile.delete()) {
      log.error("Something went wrong while trying to delete test file.");
    }
  }
}
