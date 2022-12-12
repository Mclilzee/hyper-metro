package metro.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MetroJsonReaderTest {
    Path metroPath = Paths.get("src/test/java/metro/util/test.json");
    Path emptyFilePath = Paths.get("src/test/java/metro/util/empty-file.json");
    Path incorrectJsonPath = Paths.get("src/test/java/metro/util/incorrect.json");

    static ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeAll
    static void init() {
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void parseJsonFromEmptyFile() {
        assertTrue(MetroJsonReader.parseMetroJson(emptyFilePath).isEmpty());
    }

    @Test
    void parseJsonIncorrectPath() {
        assertTrue(MetroJsonReader.parseMetroJson(incorrectJsonPath).isEmpty());
    }

    @Test
    void parseCorrectMap() {
        Map<String, Map<String, String>> actual = MetroJsonReader.parseMetroJson(metroPath).orElseThrow();

        Map<String, Map<String, String>> expected = new HashMap<>();
        expected.put("m1", new HashMap<>());
        expected.get("m1").put("3", "Baker Street");
        expected.get("m1").put("1", "Bishops-road");
        expected.get("m1").put("2", "Edgver road");

        expected.put("m2", new HashMap<>());
        expected.get("m2").put("2", "Westbourne-park");
        expected.get("m2").put("1", "Hammersmith");

        assertEquals(expected, actual);
    }

}