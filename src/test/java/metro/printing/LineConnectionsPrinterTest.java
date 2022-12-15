package metro.printing;

import metro.MetroLine;
import metro.Station;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LineConnectionsPrinterTest {

    MetroPrinter printer = new LineConnectionsPrinter();

    @Test
    void printStationsWithNoLineConnections() {
        MetroLine metroLine = new MetroLine("");
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
        MetroLine metroLine = new MetroLine("");

        Station berlin = new Station("Berlin");
        Station bremen = new Station("Bremen");
        Station beirut = new Station("Beirut");

        metroLine.append(berlin).append(bremen).append(beirut);


        MetroLine germany = new MetroLine("Germany");
        Station frankfurt = new Station("Frankfurt");
        metroLine.addLineConnection(bremen, germany, frankfurt);

        String actual = printer.getMetroLinePrintString(metroLine);
        String expected = """
                          depot
                          Berlin
                          Bremen - Frankfurt (Germany)
                          Beirut
                          depot""";

        assertEquals(expected, actual);
    }

    @Test
    void printStationWithMultipleConnections() {
        MetroLine metroLine = new MetroLine("");
        Station berlin = new Station("Berlin");
        Station bremen = new Station("Bremen");
        Station beirut = new Station("Beirut");

        metroLine.append(berlin).append(bremen).append(beirut);

        MetroLine germany = new MetroLine("Germany");
        Station frankfurt = new Station("Frankfurt");
        metroLine.addLineConnection(bremen, germany, frankfurt);

        MetroLine lebanon = new MetroLine("Lebanon");
        Station aramount = new Station("Aramount");
        metroLine.addLineConnection(bremen, lebanon, aramount);

        MetroLine france = new MetroLine("France");
        Station paris = new Station("Paris");
        metroLine.addLineConnection(beirut, france, paris);

        String actual = printer.getMetroLinePrintString(metroLine);
        String expected = """
                          depot
                          Berlin
                          Bremen - Frankfurt (Germany)
                          Bremen - Aramoun (Lebanon)
                          Beirut - Paris (France)
                          depot""";

        assertEquals(expected, actual);
    }

    @Test
    void printNothingIfMetroIsEmpty() {
        MetroLine metroLine = new MetroLine("");

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