package metro;

import metro.service.MetroMemoryService;
import metro.service.MetroService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @Test
    void printMetroInformation() {
        MetrosController controller = new MetrosController(new Scanner("\\output Germany\n\\exit"), metroService);
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
    void printMetroInformationParseQuotes() {
        MetrosController controller = new MetrosController(new Scanner("\\output \"Germany\"\n\\exit"), metroService);
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
    void appendStation() {
        MetrosController controller = new MetrosController(new Scanner("\\append Germany Berlin\n\\append Germany Bremen\n\\exit"), metroService);
        metroService.addMetroStations("Germany");
        controller.start();

        MetroStations expected = new MetroStations();
        expected.append("Berlin").append("Bremen");

        assertTrue(metroService.getValues().contains(expected));
    }

}