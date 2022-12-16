package metro.printing;

import metro.MetroLine;
import metro.Station;
import metro.search.FindShortestPath;
import metro.search.PathFinder;
import org.junit.jupiter.api.Test;

class FindShortestPathTest {

    PathFinder finder = new FindShortestPath();

    @Test
    void printCorrectPath() {
        MetroLine germany = new MetroLine("Germany");
        Station berlin = new Station("Berlin");
        Station bremen = new Station("Bremen");
        Station frankfurt = new Station("Frankfurt");
        germany.append(berlin).append(bremen).append(frankfurt);

        finder.getShortestPathString(berlin, frankfurt);

        String expected = "Berlin" + System.lineSeparator() +
                          "Bremen" + System.lineSeparator() +
                          "Frankfurt" + System.lineSeparator();

    }

}