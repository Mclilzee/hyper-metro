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
        metroStations.add(berlin)
                     .add(bremen)
                     .add(hamburg)
                     .add(beirut);

        ThreeStationsPrinter printer = new ThreeStationsPrinter();
        printer.printMetroStations(metroStations);

        String expected = """
        depot - Berlin - Bremen%n"
        Berlin - Bremen - Hamburg%n
        Bremen - Hamburg - Beirut%n
        Hamburg - Beirut - depot%n""".formatted();

        assertEquals(expected, outputStream.toString());
    }

    @Test
    @DisplayName("print nothing if stations unavailable")
    void getConnectedEmptyStations() {
        String expected = "";

        assertEquals(expected, outputStream.toString());
    }
}