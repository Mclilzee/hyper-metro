package metro.util;

import metro.MetroStations;
import metro.Station;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MetroFileReaderTest {

    Path metroPath = Paths.get("src/test/java/metro/util/test.json");
    Path emptyFilePath = Paths.get("src/test/java/metro/util/empty-file.json");
    Path incorrectJsonPath = Paths.get("src/test/java/metro/util/incorrect.json");

    static ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeAll
    static void init() {
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void handleIncorrectJson() {
        Map<String, MetroStations> map = MetroFileReader.loadMetroFromFile(incorrectJsonPath);
        assertTrue(map.isEmpty());
    }

    @Test
    void getEmptyMapIfFileIsEmpty() {
      Map<String, MetroStations> metroMap =  MetroFileReader.loadMetroFromFile(emptyFilePath);
      assertTrue(metroMap.isEmpty());
    }

    @Test
    void emptyFilePrintsCorrectMessage() {
        MetroFileReader.loadMetroFromFile(emptyFilePath);
        String expected = "Incorrect File" + System.lineSeparator();

        assertEquals(expected, outputStream.toString());
    }

    @Test
    void returnCorrectMap() {
        Map<String, MetroStations> map = MetroFileReader.loadMetroFromFile(metroPath);

        MetroStations m1 = new MetroStations();
        m1.append("Bishops-road").append("Edgver road").append("Baker Street");

        MetroStations m2 = new MetroStations();
        m2.append("Hammersmith").append("Westbourne-park");

        Map<String, MetroStations> expected = new HashMap<>();
        expected.put("m1", m1);
        expected.put("m2", m2);

        assertEquals(expected, map);
    }
}