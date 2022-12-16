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
        Station berlin = new Station("Berlin");
        Station bremen = new Station("Bremen");
        Station frankfurt = new Station("Frankfurt");
        germany.append(berlin).append(bremen).append(frankfurt);

        ShortestPathFinder finder = new ShortestPathFinder();
        String actual = finder.findPathString(berlin, frankfurt);

        String expected = """
                          Berlin
                          Bremen
                          Frankfurt""";
        assertEquals(expected, actual);
    }

    @Test
    void pathTestBothDirections() {
        MetroLine germany = new MetroLine("Germany");
        Station berlin = new Station("Berlin");
        Station bremen = new Station("Bremen");
        Station frankfurt = new Station("Frankfurt");
        Station beirut = new Station("Beirut");
        germany.append(berlin).append(bremen).append(frankfurt).append(beirut);

        ShortestPathFinder finder = new ShortestPathFinder();
        String actual = finder.findPathString(frankfurt, berlin);

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
        Station berlin = new Station("Berlin");
        Station bremen = new Station("Bremen");
        Station frankfurt = new Station("Frankfurt");
        germany.append(berlin).append(bremen).append(frankfurt);

        MetroLine france = new MetroLine("France");
        Station beirut = new Station("Beirut");
        Station paris = new Station("Paris");
        france.append(beirut).append(paris);

        germany.addLineConnection(bremen, france, beirut);

        ShortestPathFinder finder = new ShortestPathFinder();
        String actual = finder.findPathString(berlin, paris);

        String expected = """
                          Berlin
                          Bremen
                          Transition to line France
                          Beirut
                          Paris""";
        assertEquals(expected, actual);
    }
}