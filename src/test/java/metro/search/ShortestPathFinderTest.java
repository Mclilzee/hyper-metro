package metro.search;

import metro.MetroLine;
import metro.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShortestPathFinderTest {

    @Test
    void getCorrectPathString() {
        MetroLine germany = new MetroLine("Germany");
        Station berlin = new Station("Berlin", 0);
        Station bremen = new Station("Bremen", 0);
        Station frankfurt = new Station("Frankfurt", 0);
        germany.append(berlin).append(bremen).append(frankfurt);

        ShortestPathFinder finder = new ShortestPathFinder();
        String actual = finder.findPathString(berlin, frankfurt).orElseThrow();

        String expected = """
                          Berlin
                          Bremen
                          Frankfurt""";
        assertEquals(expected, actual);
    }

    @Test
    void returnEmptyIfConnectionNotFound() {

        MetroLine germany = new MetroLine("Germany");
        Station berlin = new Station("Berlin", 0);
        Station bremen = new Station("Bremen", 0);
        Station frankfurt = new Station("Frankfurt", 0);
        germany.append(berlin).append(bremen).append(frankfurt);


        ShortestPathFinder finder = new ShortestPathFinder();

        Station beirut = new Station("Beirut", 0);
        assertTrue(finder.findPathString(berlin, beirut).isEmpty());
    }

    @Test
    void pathTestBothDirections() {
        MetroLine germany = new MetroLine("Germany");
        Station berlin = new Station("Berlin", 0);
        Station bremen = new Station("Bremen", 0);
        Station frankfurt = new Station("Frankfurt", 0);
        Station beirut = new Station("Beirut", 0);
        germany.append(berlin).append(bremen).append(frankfurt).append(beirut);

        ShortestPathFinder finder = new ShortestPathFinder();
        String actual = finder.findPathString(frankfurt, berlin).orElseThrow();

        String expected = """
                          Frankfurt
                          Bremen
                          Berlin""";
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Ger correct lines if stations has connections")
    void correctStringLineConnections() {
        MetroLine germany = new MetroLine("Germany");
        Station berlin = new Station("Berlin", 0);
        Station bremen = new Station("Bremen", 0);
        Station frankfurt = new Station("Frankfurt", 0);
        germany.append(berlin).append(bremen).append(frankfurt);

        MetroLine france = new MetroLine("France");
        Station beirut = new Station("Beirut", 0);
        Station paris = new Station("Paris", 0);
        france.append(beirut).append(paris);

        germany.addLineConnection(bremen, france, beirut);

        ShortestPathFinder finder = new ShortestPathFinder();
        String actual = finder.findPathString(berlin, paris).orElseThrow();

        String expected = """
                          Berlin
                          Bremen
                          Transition to line France
                          Beirut
                          Paris""";
        assertEquals(expected, actual);
    }

    @Test
    void testMultipleDirectionsAcrossLines() {
        MetroLine germany = new MetroLine("Germany");
        Station berlin = new Station("Berlin", 0);
        Station bremen = new Station("Bremen", 0);
        Station frankfurt = new Station("Frankfurt", 0);
        germany.append(berlin).append(bremen).append(frankfurt);

        MetroLine france = new MetroLine("France");
        Station nice = new Station("Nice", 0);
        Station paris = new Station("Paris", 0);
        france.append(nice).append(paris);

        MetroLine lebanon = new MetroLine("Lebanon");
        Station beirut = new Station("Beirut", 0);
        Station aramount = new Station("Aramoun", 0);
        Station matar = new Station("Matar", 0);
        lebanon.append(beirut).append(aramount).append(matar);

        germany.addLineConnection(bremen, france, nice);
        france.addLineConnection(paris, lebanon, aramount);

        ShortestPathFinder finder = new ShortestPathFinder();
        String actual = finder.findPathString(berlin, beirut).orElseThrow();

        String expected = """
                          Berlin
                          Bremen
                          Transition to line France
                          Nice
                          Paris
                          Transition to line Lebanon
                          Aramoun
                          Beirut""";
        assertEquals(expected, actual);

    }

    @Test
    void canSearchMultipleTimes() {
        MetroLine germany = new MetroLine("Germany");
        Station berlin = new Station("Berlin", 0);
        Station bremen = new Station("Bremen", 0);
        Station frankfurt = new Station("Frankfurt", 0);
        germany.append(berlin).append(bremen).append(frankfurt);

        ShortestPathFinder finder = new ShortestPathFinder();
        finder.findPathString(berlin, frankfurt);
        String actual = finder.findPathString(berlin, frankfurt).orElseThrow();

        String expected = """
                          Berlin
                          Bremen
                          Frankfurt""";
        assertEquals(expected, actual);
    }

}