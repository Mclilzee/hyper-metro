package metro.printing;

import metro.MetroLine;
import metro.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ThreeStationsPrinterTest {
    MetroPrinter printer = new ThreeStationsPrinter();

    @Test
    @DisplayName("print stations list of three connected stations")
    void getConnectedThreeStationsList() {
        MetroLine metroLine = new MetroLine("");
        Station berlin = new Station("Berlin");
        Station bremen = new Station("Bremen");
        Station hamburg = new Station("Hamburg");
        Station beirut = new Station("Beirut");
        metroLine.append(berlin)
                 .append(bremen)
                 .append(hamburg)
                 .append(beirut);

        String actual = printer.getMetroLinePrintString(metroLine);
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
        MetroLine metroLine = new MetroLine("");
        ThreeStationsPrinter printer = new ThreeStationsPrinter();

        String actual = printer.getMetroLinePrintString(metroLine);
        String expected = "";

        assertEquals(expected, actual);
    }

    @Test
    void printNothingIfStationIsNull() {
        ThreeStationsPrinter printer = new ThreeStationsPrinter();

        String actual = printer.getMetroLinePrintString(null);
        String expected = "";

        assertEquals(expected, actual);
    }
}