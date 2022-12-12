package metro.util;

import metro.MetroStations;
import metro.Station;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ThreeStationsPrinterTest {

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
    @DisplayName("print stations list of three connected stations")
    void getConnectedThreeStationsList() {
        MetroStations metroStations = new MetroStations();
        Station berlin = new Station("Berlin");
        Station bremen = new Station("Bremen");
        Station hamburg = new Station("Hamburg");
        Station beirut = new Station("Beirut");
        metroStations.append(berlin)
                     .append(bremen)
                     .append(hamburg)
                     .append(beirut);

        ThreeStationsPrinter printer = new ThreeStationsPrinter();
        printer.printMetroStations(metroStations);

        String expected = "depot - Berlin - Bremen" + System.lineSeparator() +
        "Berlin - Bremen - Hamburg" + System.lineSeparator() +
        "Bremen - Hamburg - Beirut" + System.lineSeparator() +
        "Hamburg - Beirut - depot" + System.lineSeparator();

        assertEquals(expected, outputStream.toString());
    }

    @Test
    @DisplayName("print nothing if stations unavailable")
    void getConnectedEmptyStations() {
        MetroStations metroStations = new MetroStations();
        ThreeStationsPrinter printer = new ThreeStationsPrinter();
        printer.printMetroStations(metroStations);

        String expected = "";

        assertEquals(expected, outputStream.toString());
    }

    @Test
    void printNothingIfStationIsNull() {
        ThreeStationsPrinter printer = new ThreeStationsPrinter();
        printer.printMetroStations(null);
        String expected = "";

        assertEquals(expected, outputStream.toString());
    }
}