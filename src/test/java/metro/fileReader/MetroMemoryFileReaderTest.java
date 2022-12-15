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
import java.util.List;

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
        MetroService service = reader.loadMetroServiceFromFile(incorrectJsonPath);
        assertTrue(service.getKeys().isEmpty());
    }

    @Test
    void getEmptyMapIfFileIsEmpty() {
      MetroService service = reader.loadMetroServiceFromFile(emptyFilePath);
      assertTrue(service.getKeys().isEmpty());
    }

    @Test
    void emptyFilePrintsCorrectMessage() {
        reader.loadMetroServiceFromFile(emptyFilePath);
        String expected = "Incorrect file" + System.lineSeparator();

        assertEquals(expected, outputStream.toString());
    }

    @Test
    void containsCorrectValues() {
        MetroService service = reader.loadMetroServiceFromFile(metroPath);

        MetroLine m1 = new MetroLine("m1");
        m1.append("Bishops-road").append("Edgver road").append("Baker Street");

        MetroLine m2 = new MetroLine("m2");
        m2.append("Hammersmith").append("Westbourne-park");

        MetroService mockService = new MetroMemoryService();
        mockService.addMetroLine("Metro-Railway");
        mockService.appendStation("Metro-Railway", "Bishops-road");
        mockService.appendStation("Metro-Railway", "Edgver road");
        mockService.appendStation("Metro-Railway", "Baker Street");

        mockService.addMetroLine("Hammersmith-and-City");
        mockService.appendStation("Hammersmith-and-City", "Hammersmith");
        mockService.appendStation("Hammersmith-and-City", "Westbourne-park");

        mockService.connectMetroLine("Metro-Railway", "Baker Street", "Hammersmith-and-City", "Baker Street");
        mockService.connectMetroLine("Hammersmith-and-City", "Baker Street", "Metro-Railway", "Baker Street");

        List<MetroLine> actual = service.getValues();
        List<MetroLine> expected = service.getValues();


        assertEquals(expected, actual);
    }

    @Test
    void containsCorrectKeys() {
        MetroService service = reader.loadMetroServiceFromFile(metroPath);

        MetroLine m1 = new MetroLine("m1");
        m1.append("Bishops-road").append("Edgver road").append("Baker Street");

        MetroLine m2 = new MetroLine("m2");
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