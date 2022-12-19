package metro.printing;

import metro.MetroLine;
import metro.Station;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LineConnectionsPrinterTest {

    MetroPrinter printer = new LineConnectionsPrinter();

    @Test
    void printStationsWithNoLineConnections() {
        MetroLine metroLine = new MetroLine("");
        metroLine.append(new Station("Berlin", 0)).append(new Station("Bremen", 0)).append(new Station("Beirut", 0));

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

        Station berlin = new Station("Berlin", 0);
        Station bremen = new Station("Bremen", 0);
        Station beirut = new Station("Beirut", 0);

        metroLine.append(berlin).append(bremen).append(beirut);


        MetroLine germany = new MetroLine("Germany");
        Station frankfurt = new Station("Frankfurt", 0);
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
        Station berlin = new Station("Berlin", 0);
        Station bremen = new Station("Bremen", 0);
        Station beirut = new Station("Beirut", 0);

        metroLine.append(berlin).append(bremen).append(beirut);

        MetroLine germany = new MetroLine("Germany");
        Station frankfurt = new Station("Frankfurt", 0);
        metroLine.addLineConnection(bremen, germany, frankfurt);

        MetroLine lebanon = new MetroLine("Lebanon");
        Station aramount = new Station("Aramount", 0);
        metroLine.addLineConnection(bremen, lebanon, aramount);

        MetroLine france = new MetroLine("France");
        Station paris = new Station("Paris", 0);
        metroLine.addLineConnection(beirut, france, paris);

        String actual = printer.getMetroLinePrintString(metroLine);
        Pattern expectedPattern = Pattern.compile("""
                          depot
                          Berlin
                          (Bremen - (Frankfurt \\(Germany\\)|Aramount \\(Lebanon\\))\\n?){2}
                          Beirut - Paris \\(France\\)
                          depot""");

        assertTrue(expectedPattern.matcher(actual).matches());
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