package metro.util;

import metro.MetroStations;
import metro.fileReader.MetroMemoryFileReader;
import metro.service.MetroMemoryService;
import metro.service.MetroService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MetroMemoryFileReaderTest {

    Path metroPath = Paths.get("src/test/java/metro/util/test.json");
    Path emptyFilePath = Paths.get("src/test/java/metro/util/empty-file.json");
    Path incorrectJsonPath = Paths.get("src/test/java/metro/util/incorrect.json");
    MetroMemoryFileReader reader = new MetroMemoryFileReader();

    static ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeAll
    static void init() {
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void handleIncorrectJson() {
        MetroService service = reader.loadMetroFromFile(incorrectJsonPath);
        assertTrue(service.getKeys().isEmpty());
    }

    @Test
    void getEmptyMapIfFileIsEmpty() {
      MetroService service = reader.loadMetroFromFile(emptyFilePath);
      assertTrue(service.getKeys().isEmpty());
    }

    @Test
    void emptyFilePrintsCorrectMessage() {
        reader.loadMetroFromFile(emptyFilePath);
        String expected = "Incorrect File" + System.lineSeparator();

        assertEquals(expected, outputStream.toString());
    }

    @Test
    void returnCorrectMap() {
        MetroService service = reader.loadMetroFromFile(metroPath);

        MetroStations m1 = new MetroStations();
        m1.append("Bishops-road").append("Edgver road").append("Baker Street");

        MetroStations m2 = new MetroStations();
        m2.append("Hammersmith").append("Westbourne-park");

        MetroService expected = new MetroMemoryService();
        expected.addMetroStations("m1");
        expected.appendStation("m1", "Bishops-road");
        expected.appendStation("m1", "Edgver road");
        expected.appendStation("m1", "Baker Street");

        expected.addMetroStations("m2");
        expected.appendStation("m2", "Hammersmith");
        expected.appendStation("m2", "Westbourne-park");

        assertEquals(expected.getKeys(), service.getKeys());
        assertTrue(service.getValues().containsAll(expected.getValues()));
    }
}