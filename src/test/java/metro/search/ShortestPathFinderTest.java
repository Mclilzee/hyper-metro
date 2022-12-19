package metro.search;

import metro.MetroLine;
import metro.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.BreakIterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShortestPathFinderTest {

    @Test
    void getCorrectPathString() {
        ShortestPathFinder finder = new ShortestPathFinder();
        MetroLine germany = new MetroLine("Germany");
        Station berlin = new Station("Berlin", 0);
        Station bremen = new Station("Bremen", 0);
        Station frankfurt = new Station("Frankfurt", 0);
        germany.append(berlin).append(bremen).append(frankfurt);

        List<Node> actual = finder.findPath(berlin, frankfurt);

        List<Node> expected = List.of(new Node(berlin), new Node(bremen), new Node(frankfurt));

        assertEquals(expected, actual);
    }

    @Test
    void returnEmptyIfConnectionNotFound() {
        ShortestPathFinder finder = new ShortestPathFinder();
        MetroLine germany = new MetroLine("Germany");
        Station berlin = new Station("Berlin", 0);
        Station bremen = new Station("Bremen", 0);
        Station frankfurt = new Station("Frankfurt", 0);
        germany.append(berlin).append(bremen).append(frankfurt);

        Station beirut = new Station("Beirut", 0);

        List<Node> actual = finder.findPath(berlin, beirut);

        List<Node> expected = List.of();
        assertEquals(expected, actual);
    }

    @Test
    void pathTestBothDirections() {
        ShortestPathFinder finder = new ShortestPathFinder();

        MetroLine germany = new MetroLine("Germany");
        Station berlin = new Station("Berlin", 0);
        Station bremen = new Station("Bremen", 0);
        Station frankfurt = new Station("Frankfurt", 0);
        Station beirut = new Station("Beirut", 0);
        germany.append(berlin).append(bremen).append(frankfurt).append(beirut);

        List<Node> actual = finder.findPath(frankfurt, berlin);

        List<Node> expected = List.of(new Node(frankfurt), new Node(bremen), new Node(berlin));

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Ger correct lines if stations has connections")
    void correctStringLineConnections() {
        ShortestPathFinder finder = new ShortestPathFinder();

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

        List<Node> actual = finder.findPath(berlin, paris);

        List<Node> expected = List.of(new Node(berlin), new Node(bremen), new Node(beirut), new Node(paris));

        assertEquals(expected, actual);
    }

    @Test
    void testMultipleDirectionsAcrossLines() {
        ShortestPathFinder finder = new ShortestPathFinder();

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
        Station aramoun = new Station("Aramoun", 0);
        Station matar = new Station("Matar", 0);
        lebanon.append(beirut).append(aramoun).append(matar);

        germany.addLineConnection(bremen, france, nice);
        france.addLineConnection(paris, lebanon, aramoun);

        List<Node> actual = finder.findPath(berlin, beirut);

        List<Node> expected = List.of(new Node(berlin),
                new Node(bremen),
                new Node(nice),
                new Node(paris),
                new Node(aramoun),
                new Node(beirut));

        assertEquals(expected, actual);

    }

    @Test
    void canSearchMultipleTimes() {
        ShortestPathFinder finder = new ShortestPathFinder();

        MetroLine germany = new MetroLine("Germany");
        Station berlin = new Station("Berlin", 0);
        Station bremen = new Station("Bremen", 0);
        Station frankfurt = new Station("Frankfurt", 0);
        germany.append(berlin).append(bremen).append(frankfurt);

        finder.findPath(berlin, frankfurt);
        List<Node> actual = finder.findPath(berlin, frankfurt);

        List<Node> expected = List.of(new Node(berlin), new Node(bremen), new Node(frankfurt));

        assertEquals(expected, actual);
    }

}