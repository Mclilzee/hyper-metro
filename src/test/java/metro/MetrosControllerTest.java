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
import java.util.Set;

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
        metroService.addMetroLine(hammerCity);
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
        MetroLine germany = new MetroLine("Germany");
        metroService.addMetroLine(germany);
        metroService.appendStation("Germany", "Berlin");
        metroService.appendStation("Germany", "Bremen");
        metroService.appendStation("Germany", "Beirut");

        controller.start();

        assertTrue(germany.findStationByName("Bremen").isEmpty());
    }

    @Test
    void removeStationWithSpaces() {
        MetrosController controller = new MetrosController(new Scanner("/remove \"German Village\" \"Bremen Circus\"\n/exit"), metroService);
        MetroLine germanVillage = new MetroLine("German Village");
        metroService.addMetroLine(germanVillage);
        metroService.appendStation("German Village", "Berlin Town");
        metroService.appendStation("German Village", "Bremen Circus");
        metroService.appendStation("German Village", "Beirut Sea");

        controller.start();

        assertTrue(germanVillage.findStationByName("Bremen Circus").isEmpty());
    }

    @Test
    void connectStationsCorrectly() {
        MetrosController controller = new MetrosController(new Scanner("/connect Germany Berlin Lebanon Beirut\n/exit"), metroService);
        MetroLine germany = new MetroLine("Germany");
        Station berlin = new Station("Berlin");
        germany.append(berlin);
        metroService.addMetroLine(germany);

        MetroLine lebanon = new MetroLine("Lebanon");
        Station beirut = new Station("Beirut");
        lebanon.append(beirut);
        metroService.addMetroLine(lebanon);

        controller.start();

        Set<LineConnection> expectedBerlinConnection = Set.of(new LineConnection(lebanon, beirut));
        Set<LineConnection> expectedBeirutConnection = Set.of(new LineConnection(germany, berlin));

        assertEquals(expectedBerlinConnection, berlin.getLineConnections());
        assertEquals(expectedBeirutConnection, beirut.getLineConnections());
    }

    @Test
    void connectStationsCorrectlyWithSpaces() {
        MetrosController controller = new MetrosController(new Scanner("/connect Hammersmith-and-City Hammersmith Metro-Railway \"Edgver road\"\n/exit"), metroService);
        MetroLine hammersmithAndCity = new MetroLine("Hammersmith-and-City");
        Station hammersmith = new Station("Hammersmith");
        hammersmithAndCity.append(hammersmith);
        metroService.addMetroLine(hammersmithAndCity);

        MetroLine metroRailway = new MetroLine("Metro-Railway");
        Station edgverRoad = new Station("Edgver road");
        metroRailway.append(edgverRoad);
        metroService.addMetroLine(metroRailway);
        controller.start();

        Set<LineConnection> expectedHammersmithConnection = Set.of(new LineConnection(metroRailway, edgverRoad));
        Set<LineConnection> expectedEdgverRoadConnection = Set.of(new LineConnection(hammersmithAndCity, hammersmith));

        assertEquals(expectedHammersmithConnection, hammersmith.getLineConnections());
        assertEquals(expectedEdgverRoadConnection, edgverRoad.getLineConnections());
    }

    @Test
    void findShortestPathInput() {
        MetroService metroMemoryService = new MetroMemoryService();
        MetrosController controller = new MetrosController(new Scanner("/route Germany Berlin Lebanon Aramoun\n/exit"), metroMemoryService);

        MetroLine germany = new MetroLine("Germany");
        Station berlin = new Station("Berlin");
        Station bremen = new Station("Bremen");
        Station frankfurt = new Station("Frankfurt");
        germany.append(berlin).append(bremen).append(frankfurt);

        MetroLine lebanon = new MetroLine("Lebanon");
        Station beirut = new Station("Beirut");
        Station aramoun = new Station("Aramoun");
        lebanon.append(beirut).append(aramoun);

        germany.addLineConnection(frankfurt, lebanon, beirut);

        metroMemoryService.addMetroLine(germany);
        metroMemoryService.addMetroLine(lebanon);

        controller.start();

        String expected = """
                          Berlin
                          Bremen
                          Frankfurt
                          Transition to line Lebanon
                          Beirut
                          Aramoun""" + System.lineSeparator();

        assertEquals(expected, outputStream.toString());
    }

    @Test
    void findShortestPathSpaces() {
        MetroService metroMemoryService = new MetroMemoryService();
        MetrosController controller = new MetrosController(new Scanner("/route \"German Town\" \"Berlin Tower\" \"Lebanon Main\" \"Aramoun Hill\"\n/exit"), metroMemoryService);

        MetroLine germany = new MetroLine("German Town");
        Station berlin = new Station("Berlin Tower");
        Station bremen = new Station("Bremen");
        Station frankfurt = new Station("Frankfurt");
        germany.append(berlin).append(bremen).append(frankfurt);

        MetroLine lebanon = new MetroLine("Lebanon Main");
        Station beirut = new Station("Beirut");
        Station aramoun = new Station("Aramoun Hill");
        lebanon.append(beirut).append(aramoun);

        germany.addLineConnection(frankfurt, lebanon, beirut);

        metroMemoryService.addMetroLine(germany);
        metroMemoryService.addMetroLine(lebanon);

        controller.start();

        String expected = """
                          Berlin Tower
                          Bremen
                          Frankfurt
                          Transition to line Lebanon Main
                          Beirut
                          Aramoun Hill""" + System.lineSeparator();

        assertEquals(expected, outputStream.toString());
    }
}