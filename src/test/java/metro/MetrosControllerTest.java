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
import java.util.List;
import java.util.Scanner;
import java.util.Timer;

import static org.junit.jupiter.api.Assertions.*;

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

}