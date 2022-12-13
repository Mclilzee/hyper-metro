package metro;

import metro.service.MetroMemoryService;
import metro.service.MetroService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MetrosControllerTest {

    MetroService metroService = new MetroMemoryService();
    static ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeAll
    static void init() {
        System.setOut(new PrintStream(outputStream));
    }

    @BeforeEach
    void setup() {
        outputStream.reset();
    }

    @Test
    void exitControllerCorrectly() {
        MetrosController controller = new MetrosController(new Scanner("\\exit"), metroService);
        controller.start();
    }

    @ParameterizedTest
    @ValueSource(strings = {"\\output something else", "\\remove thre value present", "\\append \"missing qute",
    "\\output \"first value\" second", "\\append three values present", "\\add-head ends with\""})
    void printInvalidCommand(String input) {
        MetrosController controller = new MetrosController(new Scanner(input + "\n\\exit"), metroService);
        controller.start();

        String expected = "Invalid command" + System.lineSeparator();
        assertEquals(expected, outputStream.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"\\output Germany", "\\output \"Germany\""})
    void printMetroInformation(String input) {
        MetrosController controller = new MetrosController(new Scanner(input + "\n\\exit"), metroService);
        metroService.addMetroStations("Germany");
        metroService.appendStation("Germany", "Berlin");
        metroService.appendStation("Germany", "Bremen");
        metroService.appendStation("Germany", "Beirut");
        controller.start();

        String expected = "depot - Berlin - Bremen" + System.lineSeparator() +
                "Berlin - Bremen - Beirut" + System.lineSeparator() +
                "Bremen - Beirut - depot" + System.lineSeparator();

        assertEquals(expected, outputStream.toString());
    }

    @Test
    void printMetroInformationWithSpaces() {
        MetrosController controller = new MetrosController(new Scanner("\\output \"Hammer City\"\n\\exit"), metroService);
        metroService.addMetroStations("Hammer City");
        metroService.appendStation("Hammer City", "Berlin");
        metroService.appendStation("Hammer City", "Bremen");
        metroService.appendStation("Hammer City", "Beirut");
        controller.start();

        String expected = "depot - Berlin - Bremen" + System.lineSeparator() +
                "Berlin - Bremen - Beirut" + System.lineSeparator() +
                "Bremen - Beirut - depot" + System.lineSeparator();

        assertEquals(expected, outputStream.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"\\append Germany Berlin\n\\append Germany Bremen",
            "\\append \"Germany\" Berlin\n\\append \"Germany\" Bremen",
            "\\append Germany \"Berlin\"\n\\append Germany \"Bremen\"",
            "\\append \"Germany\" \"Berlin\"\n\\append \"Germany\" \"Bremen\""})
    void appendStation(String input) {
        MetrosController controller = new MetrosController(new Scanner(input + "\n\\exit"), metroService);
        metroService.addMetroStations("Germany");
        controller.start();

        MetroStations expected = new MetroStations();
        expected.append("Berlin").append("Bremen");

        assertTrue(metroService.getValues().contains(expected));
    }

    @Test
    void appendStationWithSpaces() {
        MetrosController controller = new MetrosController(new Scanner("\\append \"Hammer City\" \"Beirut Tower\"\n\\exit"), metroService);
        metroService.addMetroStations("Hammer City");
        controller.start();

        MetroStations expected = new MetroStations();
        expected.append("Beirut Tower");

        assertTrue(metroService.getValues().contains(expected));
    }


    @ParameterizedTest
    @ValueSource(strings = {"\\add-head Germany Berlin\n\\add-head Germany Bremen",
            "\\add-head \"Germany\" Berlin\n\\add-head \"Germany\" Bremen",
            "\\add-head Germany \"Berlin\"\n\\add-head Germany \"Bremen\"",
            "\\add-head \"Germany\" \"Berlin\"\n\\add-head \"Germany\" \"Bremen\""})
    void addHeadStation(String input) {
        MetrosController controller = new MetrosController(new Scanner(input + "\n\\exit"), metroService);
        metroService.addMetroStations("Germany");
        controller.start();

        MetroStations expected = new MetroStations();
        expected.addHead("Berlin").addHead("Bremen");

        assertTrue(metroService.getValues().contains(expected));
    }

    @Test
    void addHeadStationWithSpaces() {
        MetrosController controller = new MetrosController(new Scanner("\\add-head \"Germany Town\" \"Berlin Bridge\"\n\\exit"), metroService);
        metroService.addMetroStations("Germany Town");
        controller.start();

        MetroStations expected = new MetroStations();
        expected.addHead("Berlin Bridge");

        assertTrue(metroService.getValues().contains(expected));
    }

    @ParameterizedTest
    @ValueSource(strings = {"\\remove Germany Bremen",
            "\\remove \"Germany\" Bremen",
            "\\remove Germany \"Bremen\"",
            "\\remove \"Germany\" \"Bremen\""})
    void removeStation(String input) {
        MetrosController controller = new MetrosController(new Scanner(input + "\n\\exit"), metroService);
        metroService.addMetroStations("Germany");
        metroService.appendStation("Germany", "Berlin");
        metroService.appendStation("Germany", "Bremen");
        metroService.appendStation("Germany", "Beirut");

        controller.start();

        MetroStations expected = new MetroStations();
        expected.append("Berlin").append("Beirut");

        assertTrue(metroService.getValues().contains(expected));
    }

    @Test
    void removeStationWithSpaces() {
        MetrosController controller = new MetrosController(new Scanner("\\remove \"German Village\" \"Bremen Circus\"\n\\exit"), metroService);
        metroService.addMetroStations("German Village");
        metroService.appendStation("German Village", "Berlin Town");
        metroService.appendStation("German Village", "Bremen Circus");
        metroService.appendStation("German Village", "Beirut Sea");

        controller.start();

        MetroStations expected = new MetroStations();
        expected.append("Berlin Town").append("Beirut Sea");

        assertTrue(metroService.getValues().contains(expected));
    }

}