package metro.printing;

import metro.MetroStations;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LineConnectionsPrinterTest {

    MetroPrinter printer = new LineConnectionsPrinter();

    @Test
    void printStationsWithNoLineConnections() {
        MetroStations metroStations = new MetroStations();
        metroStations.append("Berlin").append("Bremen").append("Beirut");

        String actual =printer.getMetroStationsPrintString(metroStations);
        String expected = """
                          depot
                          Berlin
                          Bremen
                          Beirut
                          depot""";

        assertEquals(expected, actual);
    }

    @Test
    void printStationWithLineConnections() {
        MetroStations metroStations = new MetroStations();
        metroStations.append("Berlin").append("Bremen").append("Beirut");
        metroStations.addLineConnection("Bremen", "Germany", "Beirut");

        String actual = printer.getMetroStationsPrintString(metroStations);
        String expected = """
                          depot
                          Berlin
                          Bremen - Beirut (Germany)
                          Beirut
                          depot""";

        assertEquals(expected, actual);
    }

    @Test
    void printStationWithMultipleConnections() {
        MetroStations metroStations = new MetroStations();
        metroStations.append("Berlin").append("Bremen").append("Beirut");
        metroStations.addLineConnection("Bremen", "Germany", "Beirut");
        metroStations.addLineConnection("Bremen", "Lebanon", "Aramoun");
        metroStations.addLineConnection("Beirut", "France", "Paris");

        String actual = printer.getMetroStationsPrintString(metroStations);
        String expected = """
                          depot
                          Berlin
                          Bremen - Beirut (Germany)
                          Bremen - Aramoun (Lebanon)
                          Beirut - Paris (France)
                          depot""";

        assertEquals(expected, actual);
    }

    @Test
    void printNothingIfMetroIsEmpty() {
        MetroStations metroStations = new MetroStations();

        String actual = printer.getMetroStationsPrintString(metroStations);
        String expected = "";

        assertEquals(expected, actual);
    }

    @Test
    void printNothingIfNullPassed() {
        String actual =printer.getMetroStationsPrintString(null);
        String expected = "";

        assertEquals(expected, actual);
    }

}