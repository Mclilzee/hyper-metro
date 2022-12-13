package metro.printing;

import metro.MetroStations;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ThreeStationsPrinterTest {
    MetroPrinter printer = new ThreeStationsPrinter();

    @Test
    @DisplayName("print stations list of three connected stations")
    void getConnectedThreeStationsList() {
        MetroStations metroStations = new MetroStations();
        metroStations.append("Berlin")
                     .append("Bremen")
                     .append("Hamburg")
                     .append("Beirut");

        String actual = printer.getMetroStationsPrintString(metroStations);
        String expected = """
                          depot - Berlin - Bremen
                          Berlin - Bremen - Hamburg
                          Bremen - Hamburg - Beirut
                          Hamburg - Beirut - depot""";

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("print nothing if stations unavailable")
    void getConnectedEmptyStations() {
        MetroStations metroStations = new MetroStations();
        ThreeStationsPrinter printer = new ThreeStationsPrinter();

        String actual = printer.getMetroStationsPrintString(metroStations);
        String expected = "";

        assertEquals(expected, actual);
    }

    @Test
    void printNothingIfStationIsNull() {
        ThreeStationsPrinter printer = new ThreeStationsPrinter();

        String actual = printer.getMetroStationsPrintString(null);
        String expected = "";

        assertEquals(expected, actual);
    }
}