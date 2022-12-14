package metro.fileReader;

import metro.MetroLine;
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

    Path metroPath = Paths.get("src/test/java/metro/fileReader/test.json");
    Path emptyFilePath = Paths.get("src/test/java/metro/fileReader/empty-file.json");
    Path incorrectJsonPath = Paths.get("src/test/java/metro/fileReader/incorrect.json");
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
        String expected = "Incorrect file" + System.lineSeparator();

        assertEquals(expected, outputStream.toString());
    }

    @Test
    void containsCorrectValues() {
        MetroService service = reader.loadMetroFromFile(metroPath);

        MetroLine m1 = new MetroLine();
        m1.append("Bishops-road").append("Edgver road").append("Baker Street");

        MetroLine m2 = new MetroLine();
        m2.append("Hammersmith").append("Westbourne-park");

        MetroService expected = new MetroMemoryService();
        expected.addMetroLine("Metro-Railway");
        expected.appendStation("Metro-Railway", "Bishops-road");
        expected.appendStation("Metro-Railway", "Edgver road");
        expected.appendStation("Metro-Railway", "Baker Street");

        expected.addMetroLine("Hammersmith-and-City");
        expected.appendStation("Hammersmith-and-City", "Hammersmith");
        expected.appendStation("Hammersmith-and-City", "Westbourne-park");

        expected.connectMetroLine("Metro-Railway", "Baker Street", "Hammersmith-and-City", "Baker Street");
        expected.connectMetroLine("Hammersmith-and-City", "Baker Street", "Metro-Railway", "Baker Street");

        assertTrue(service.getValues().containsAll(expected.getValues()));
    }

    @Test
    void containsCorrectKeys() {
        MetroService service = reader.loadMetroFromFile(metroPath);

        MetroLine m1 = new MetroLine();
        m1.append("Bishops-road").append("Edgver road").append("Baker Street");

        MetroLine m2 = new MetroLine();
        m2.append("Hammersmith").append("Westbourne-park");

        MetroService expected = new MetroMemoryService();
        expected.addMetroLine("Metro-Railway");
        expected.appendStation("Metro-Railway", "Bishops-road");
        expected.appendStation("Metro-Railway", "Edgver road");
        expected.appendStation("Metro-Railway", "Baker Street");

        expected.addMetroLine("Hammersmith-and-City");
        expected.appendStation("Hammersmith-and-City", "Hammersmith");
        expected.appendStation("Hammersmith-and-City", "Westbourne-park");

        assertEquals(expected.getKeys(), service.getKeys());
    }
}