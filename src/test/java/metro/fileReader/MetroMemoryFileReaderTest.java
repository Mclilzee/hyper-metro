package metro.fileReader;

import metro.MetroLine;
import metro.Station;
import metro.service.MetroMemoryService;
import metro.service.MetroService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    void containsCorrectAppendingOrder() {
        MetroService service = reader.loadMetroServiceFromFile(metroPath);

        MetroService mockService = new MetroMemoryService();
        mockService.addMetroLine(new MetroLine("Metro-Railway"));
        mockService.appendStation("Metro-Railway", "Bishops-road", 2);
        mockService.appendStation("Metro-Railway", "Edgver road", 3);
        mockService.appendStation("Metro-Railway", "Baker street", 1);

        mockService.addMetroLine(new MetroLine("Hammersmith-and-City"));
        mockService.appendStation("Hammersmith-and-City", "Hammersmith", 1);
        mockService.appendStation("Hammersmith-and-City", "Westbourne-park", 3);
        mockService.appendStation("Hammersmith-and-City", "Baker street", 3);

        mockService.connectMetroLine("Metro-Railway", "Baker street", "Hammersmith-and-City", "Baker street");

        List<MetroLine> actual = service.getValues();
        List<MetroLine> expected = mockService.getValues();


        assertEquals(expected, actual);
    }

    @Test
    void containsCorrectKeys() {
        MetroService service = reader.loadMetroServiceFromFile(metroPath);

        MetroLine m1 = new MetroLine("m1");
        m1.append(new Station("Bishops-road")).append(new Station("Edgver road")).append(new Station("Baker street"));

        MetroLine m2 = new MetroLine("m2");
        m2.append(new Station("Hammersmith")).append(new Station("Westbourne-park"));

        MetroService mockService = new MetroMemoryService();
        mockService.addMetroLine(new MetroLine("Metro-Railway"));
        mockService.addMetroLine(new MetroLine("Metro-Railway"));
        mockService.appendStation("Metro-Railway", "Bishops-road", 2);
        mockService.appendStation("Metro-Railway", "Edgver road", 3);
        mockService.appendStation("Metro-Railway", "Baker street", 1);

        mockService.addMetroLine(new MetroLine("Hammersmith-and-City"));
        mockService.appendStation("Hammersmith-and-City", "Hammersmith", 1);
        mockService.appendStation("Hammersmith-and-City", "Westbourne-park", 3);
        mockService.appendStation("Hammersmith-and-City", "Baker street", 3);


        assertEquals(mockService.getKeys(), service.getKeys());
    }
}