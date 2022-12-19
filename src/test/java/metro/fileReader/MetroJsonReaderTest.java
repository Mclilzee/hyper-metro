package metro.fileReader;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MetroJsonReaderTest {
    Path metroPath = Paths.get("src/test/java/metro/fileReader/test.json");
    Path emptyFilePath = Paths.get("src/test/java/metro/fileReader/empty-file.json");
    Path incorrectJsonPath = Paths.get("src/test/java/metro/fileReader/incorrect.json");

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
        Map<String, Map<String, StationDTO>> actual = MetroJsonReader.parseMetroJson(metroPath).orElseThrow();

        Map<String, Map<String, StationDTO>> expected = new HashMap<>();
        expected.put("Metro-Railway", new HashMap<>());
        ConnectionDTO metroRailwayConnection = new ConnectionDTO("Hammersmith-and-City", "Baker street");

        expected.get("Metro-Railway").put("3", new StationDTO("Baker street", List.of(metroRailwayConnection), 1));
        expected.get("Metro-Railway").put("1", new StationDTO("Bishops-road", List.of(), 2));
        expected.get("Metro-Railway").put("2", new StationDTO("Edgver road", List.of(), 3));

        expected.put("Hammersmith-and-City", new HashMap<>());
        ConnectionDTO hammerSmithConnection = new ConnectionDTO("Metro-Railway", "Baker street");

        expected.get("Hammersmith-and-City").put("2", new StationDTO("Westbourne-park", List.of(), 3));
        expected.get("Hammersmith-and-City").put("1", new StationDTO("Hammersmith", List.of(), 1));
        expected.get("Hammersmith-and-City").put("3", new StationDTO("Baker street", List.of(hammerSmithConnection), 3));

        assertEquals(expected, actual);
    }

}