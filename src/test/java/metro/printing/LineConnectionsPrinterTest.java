package metro.printing;

import metro.MetroStations;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class LineConnectionsPrinterTest {

    MetroPrinter printer = new LineConnectionsPrinter();
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
    void printStationsWithNoLineConnections() {
        MetroStations metroStations = new MetroStations();
        metroStations.append("Berlin").append("Bremen").append("Beirut");
        printer.printMetroStations(metroStations);

        String expected = "depot" + System.lineSeparator() +
                "Berlin" + System.lineSeparator() +
                "Bremen" + System.lineSeparator() +
                "Beirut" + System.lineSeparator() +
                "depot" + System.lineSeparator();

        assertEquals(expected, outputStream.toString());
    }

    @Test
    void printStationWithLineConnections() {
        MetroStations metroStations = new MetroStations();
        metroStations.append("Berlin").append("Bremen").append("Beirut");
        metroStations.addLineConnection("Bremen", "Germany", "Beirut");
        printer.printMetroStations(metroStations);

        String expected = "depot" + System.lineSeparator() +
                "Berlin" + System.lineSeparator() +
                "Bremen - Beirut (Germany)" + System.lineSeparator() +
                "Beirut" + System.lineSeparator() +
                "depot" + System.lineSeparator();

        assertEquals(expected, outputStream.toString());
    }

    @Test
    void printStationWithMultipleConnections() {
        MetroStations metroStations = new MetroStations();
        metroStations.append("Berlin").append("Bremen").append("Beirut");
        metroStations.addLineConnection("Bremen", "Germany", "Beirut");
        metroStations.addLineConnection("Bremen", "Lebanon", "Aramoun");
        metroStations.addLineConnection("Beirut", "France", "Paris");
        printer.printMetroStations(metroStations);

        String expected = "depot" + System.lineSeparator() +
                "Berlin" + System.lineSeparator() +
                "Bremen - Beirut (Germany)" + System.lineSeparator() +
                "Bremen - Aramoun (Lebanon)" + System.lineSeparator() +
                "Beirut - Paris (France)" + System.lineSeparator() +
                "depot" + System.lineSeparator();

        assertEquals(expected, outputStream.toString());
    }

    @Test
    void printNothingIfMetroIsEmpty() {
        MetroStations metroStations = new MetroStations();
        printer.printMetroStations(metroStations);

        String expected = "";

        assertEquals(expected, outputStream.toString());
    }

    @Test
    void printNothingIfNullPassed() {
        printer.printMetroStations(null);

        String expected = "";

        assertEquals(expected, outputStream.toString());
    }

}