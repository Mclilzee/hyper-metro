package metro.search;

import metro.MetroLine;
import metro.Station;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PathFinderTest {

    @Test
    void printCorrectPath() {
        MetroLine germany = new MetroLine("Germany");
        Station berlin = new Station("Berlin");
        Station bremen = new Station("Bremen");
        Station frankfurt = new Station("Frankfurt");
        germany.append(berlin).append(bremen).append(frankfurt);

        PathFinder finder = new PathFinder(berlin, frankfurt);
        String actual = finder.getShortestPath();

        String expected = "Berlin" + System.lineSeparator() +
                          "Bremen" + System.lineSeparator() +
                          "Frankfurt" + System.lineSeparator();

        assertEquals(expected, actual);
    }

}