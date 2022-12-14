package metro.printing;

import metro.MetroLine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LineConnectionsPrinterTest {

    MetroPrinter printer = new LineConnectionsPrinter();

    @Test
    void printStationsWithNoLineConnections() {
        MetroLine metroLine = new MetroLine();
        metroLine.append("Berlin").append("Bremen").append("Beirut");

        String actual =printer.getMetroLinePrintString(metroLine);
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
        MetroLine metroLine = new MetroLine();
        metroLine.append("Berlin").append("Bremen").append("Beirut");
        metroLine.addLineConnection("Bremen", "Germany", "Beirut");

        String actual = printer.getMetroLinePrintString(metroLine);
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
        MetroLine metroLine = new MetroLine();
        metroLine.append("Berlin").append("Bremen").append("Beirut");
        metroLine.addLineConnection("Bremen", "Germany", "Beirut");
        metroLine.addLineConnection("Bremen", "Lebanon", "Aramoun");
        metroLine.addLineConnection("Beirut", "France", "Paris");

        String actual = printer.getMetroLinePrintString(metroLine);
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
        MetroLine metroLine = new MetroLine();

        String actual = printer.getMetroLinePrintString(metroLine);
        String expected = "";

        assertEquals(expected, actual);
    }

    @Test
    void printNothingIfNullPassed() {
        String actual =printer.getMetroLinePrintString(null);
        String expected = "";

        assertEquals(expected, actual);
    }

}