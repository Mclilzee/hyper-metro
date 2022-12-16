package metro;

import metro.printing.LineConnectionsPrinter;
import metro.printing.MetroPrinter;
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
        MetrosController controller = new MetrosController(new Scanner("/exit"), metroService);
        controller.start();
    }

    @ParameterizedTest
    @ValueSource(strings = {"/output something else", "/remove three value present", "/append \"missing qute", "/output \"first value\" second", "/append three values present", "/add-head ends with\"", "/connect only three values", "/connect two values", "/connect missing \""})
    void printInvalidCommand(String input) {
        MetrosController controller = new MetrosController(new Scanner(input + "\n/exit"), metroService);
        controller.start();

        String expected = "Invalid command" + System.lineSeparator();
        assertEquals(expected, outputStream.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/output Germany", "/output \"Germany\""})
    void printMetroInformation(String input) {
        MetrosController controller = new MetrosController(new Scanner(input + "\n/exit"), metroService);

        MetroLine germany = new MetroLine("Germany");
        metroService.addMetroLine(germany);
        metroService.appendStation("Germany", "Berlin");
        metroService.appendStation("Germany", "Bremen");
        metroService.appendStation("Germany", "Beirut");
        controller.start();

        // Use specific printer and add lineSeparator for printing
        MetroPrinter printer = new LineConnectionsPrinter();
        String expected = printer.getMetroLinePrintString(germany) + System.lineSeparator();

        assertEquals(expected, outputStream.toString());
    }

    @Test
    void printMetroInformationWithSpaces() {
        MetrosController controller = new MetrosController(new Scanner("/output \"Hammer City\"\n/exit"), metroService);

        MetroLine hammerCity = new MetroLine("Hammer City");
        metroService.addMetroLine(hammerCity);
        metroService.appendStation("Hammer City", "Berlin");
        metroService.appendStation("Hammer City", "Bremen");
        metroService.appendStation("Hammer City", "Beirut");
        controller.start();


        // Use specific printer and add lineSeparator for printing
        MetroPrinter printer = new LineConnectionsPrinter();
        String expected = printer.getMetroLinePrintString(hammerCity) + System.lineSeparator();

        assertEquals(expected, outputStream.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/append Germany Berlin\n/append Germany Bremen", "/append \"Germany\" Berlin\n/append \"Germany\" Bremen", "/append Germany \"Berlin\"\n/append Germany \"Bremen\"", "/append \"Germany\" \"Berlin\"\n/append \"Germany\" \"Bremen\""})
    void appendStation(String input) {
        MetrosController controller = new MetrosController(new Scanner(input + "\n/exit"), metroService);
        MetroLine germany = new MetroLine("Germany");
        metroService.addMetroLine(germany);
        controller.start();

        Station firstStation = germany.getHead().getNextStation().orElseThrow();
        Station secondStation = firstStation.getNextStation().orElseThrow();

        String firstStationExpected = "Berlin";
        String secondStationExpected = "Bremen";

        assertEquals(firstStationExpected, firstStation.getName());
        assertEquals(secondStationExpected, secondStation.getName());
    }

    @Test
    void appendStationWithSpaces() {
        MetrosController controller = new MetrosController(new Scanner("/append \"Hammer City\" \"Beirut Tower\"\n/exit"), metroService);
        MetroLine hammerCity = new MetroLine("Hammer City");
        metroService.addMetroLine(new MetroLine("Hammer City"));
        controller.start();

        assertTrue(hammerCity.findStationByName("Beirut Tower").isPresent());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/add-head Germany Berlin\n/add-head Germany Bremen", "/add-head \"Germany\" Berlin\n/add-head \"Germany\" Bremen", "/add-head Germany \"Berlin\"\n/add-head Germany \"Bremen\"", "/add-head \"Germany\" \"Berlin\"\n/add-head \"Germany\" \"Bremen\""})
    void addHeadStation(String input) {
        MetrosController controller = new MetrosController(new Scanner(input + "\n/exit"), metroService);
        MetroLine germany = new MetroLine("Germany");
        metroService.addMetroLine(germany);
        controller.start();

        Station firstStation = germany.getHead().getNextStation().orElseThrow();
        Station secondStation = firstStation.getNextStation().orElseThrow();

        String firstExpectedStation = "Bremen";
        String secondExpectedStation = "Berlin";

        assertEquals(firstExpectedStation, firstStation.getName());
        assertEquals(secondExpectedStation, secondStation.getName());
    }

    @Test
    void addHeadStationWithSpaces() {
        MetrosController controller = new MetrosController(new Scanner("/add-head \"Germany Town\" \"Berlin Bridge\"\n/exit"), metroService);
        MetroLine germanyTown = new MetroLine("Germany Town");
        metroService.addMetroLine(germanyTown);
        controller.start();

        assertTrue(germanyTown.findStationByName("Berlin Bridge").isPresent());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/remove Germany Bremen", "/remove \"Germany\" Bremen", "/remove Germany \"Bremen\"", "/remove \"Germany\" \"Bremen\""})
    void removeStation(String input) {
        MetrosController controller = new MetrosController(new Scanner(input + "\n/exit"), metroService);
        metroService.addMetroLine("Germany");
        metroService.appendStation("Germany", "Berlin");
        metroService.appendStation("Germany", "Bremen");
        metroService.appendStation("Germany", "Beirut");

        controller.start();

        MetroLine expected = new MetroLine("");
        expected.append("Berlin")
                .append("Beirut");

        assertTrue(metroService.getValues()
                               .contains(expected));
    }

    @Test
    void removeStationWithSpaces() {
        MetrosController controller = new MetrosController(new Scanner("/remove \"German Village\" \"Bremen Circus\"\n/exit"), metroService);
        metroService.addMetroLine("German Village");
        metroService.appendStation("German Village", "Berlin Town");
        metroService.appendStation("German Village", "Bremen Circus");
        metroService.appendStation("German Village", "Beirut Sea");

        controller.start();

        MetroLine expected = new MetroLine("");
        expected.append("Berlin Town")
                .append("Beirut Sea");

        assertTrue(metroService.getValues()
                               .contains(expected));
    }

    @Test
    void connectStationsCorrectly() {
        MetrosController controller = new MetrosController(new Scanner("/connect Germany Berlin Lebanon Beirut\n/exit"), metroService);
        metroService.addMetroLine("Germany");
        metroService.appendStation("Germany", "Berlin");

        metroService.addMetroLine("Lebanon");
        metroService.appendStation("Lebanon", "Beirut");
        controller.start();

        MetroLine expected = new MetroLine("Germany");
        Station berlin = new Station("Berlin");
        expected.append(berlin);

        MetroLine lebanon = new MetroLine("Lebanon");
        Station beirut = new Station("Beirut");
        berlin.addLineConnection(lebanon, beirut);

        assertTrue(metroService.getValues()
                               .contains(expected));
    }


    @Test
    void connectStationsCorrectlyWithSpaces() {
        MetrosController controller = new MetrosController(new Scanner("/connect Hammersmith-and-City Hammersmith Metro-Railway \"Edgver road\"\n/exit"), metroService);
        metroService.addMetroLine("Hammersmith-and-City");
        metroService.appendStation("Hammersmith-and-City", "Hammersmith");

        metroService.addMetroLine("Metro-Railway");
        metroService.appendStation("Metro-Railway", "Edgver road");
        controller.start();

        MetroLine expectedStation = new MetroLine("Hammersmith-and-City");
        Station hammersmith = new Station("Hammersmith");
        expectedStation.append(hammersmith);

        MetroLine metroRailway = new MetroLine("Metro-Railway");
        Station edgverRoad = new Station("Edgver road");
        hammersmith.addLineConnection(metroRailway, edgverRoad);

        assertTrue(metroService.getValues()
                               .contains(expectedStation));
    }
}