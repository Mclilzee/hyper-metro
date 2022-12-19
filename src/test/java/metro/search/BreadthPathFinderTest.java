package metro.search;

import metro.MetroLine;
import metro.Station;
import metro.search.frontier.FrontierFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BreadthPathFinderTest {

    BreadthPathFinder finder = new BreadthPathFinder(FrontierFactory.getBreadthFrontier());

    @Test
    void getShortestRoad() {
        Station berlin = new Station("Berlin", 0);
        Station bremen = new Station("Bremen", 0);
        berlin.setNextStation(bremen);

        Station frankfurt = new Station("Frankfurt", 0);
        bremen.setNextStation(frankfurt);

        List<Node> actual = finder.findPath(berlin, frankfurt);

        List<Node> expected = List.of(new Node(berlin), new Node(bremen), new Node(frankfurt));

        assertEquals(expected, actual);
    }

    @Test
    void getFastestRoute() {
        Station berlin = new Station("Berlin", 0);
        Station bremen = new Station("Bremen", 0);
        berlin.setNextStation(bremen);

        Station frankfurt = new Station("Frankfurt", 0);
        bremen.setNextStation(frankfurt);

        List<Node> actual = finder.findPath(berlin, frankfurt);

        List<Node> expected = List.of(new Node(berlin), new Node(bremen), new Node(frankfurt));

        assertEquals(expected, actual);
    }

    @Test
    void returnEmptyIfConnectionNotFound() {
        Station berlin = new Station("Berlin", 0);
        Station beirut = new Station("Beirut", 0);

        List<Node> actual = finder.findPath(berlin, beirut);

        List<Node> expected = List.of();
        assertEquals(expected, actual);
    }

    @Test
    void pathTestBothDirections() {
        Station berlin = new Station("Berlin", 0);
        Station bremen = new Station("Bremen", 0);
        bremen.setPreviousStation(berlin);

        Station frankfurt = new Station("Frankfurt", 0);
        frankfurt.setPreviousStation(bremen);

        List<Node> actual = finder.findPath(frankfurt, berlin);

        List<Node> expected = List.of(new Node(frankfurt), new Node(bremen), new Node(berlin));

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Ger correct lines if stations has connections")
    void correctStringLineConnections() {
        Station berlin = new Station("Berlin", 0);
        Station bremen = new Station("Bremen", 0);
        berlin.setNextStation(bremen);

        Station frankfurt = new Station("Frankfurt", 0);
        bremen.setNextStation(frankfurt);

        Station beirut = new Station("Beirut", 0);
        bremen.addLineConnection(new MetroLine(""), beirut);

        Station paris = new Station("Paris", 0);
        beirut.setNextStation(paris);


        List<Node> actual = finder.findPath(berlin, paris);

        List<Node> expected = List.of(new Node(berlin), new Node(bremen), new Node(beirut), new Node(paris));

        assertEquals(expected, actual);
    }

    @Test
    void testMultipleDirectionsAcrossLines() {
        Station berlin = new Station("Berlin", 0);
        Station bremen = new Station("Bremen", 0);
        berlin.setNextStation(bremen);

        Station nice = new Station("Nice", 0);
        bremen.addLineConnection(new MetroLine(""), nice);

        Station paris = new Station("Paris", 0);
        nice.setNextStation(paris);

        Station beirut = new Station("Beirut", 0);

        Station aramoun = new Station("Aramoun", 0);
        aramoun.setPreviousStation(beirut);
        paris.addLineConnection(new MetroLine(""), aramoun);

        Station matar = new Station("Matar", 0);
        aramoun.setNextStation(matar);

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
    void fastestRouteReturnCorrectOrder() {
        finder = new BreadthPathFinder(FrontierFactory.getGreedyBreadthFrontier());
        Station berlin = new Station("Berlin", 0);
        Station bremen = new Station("Bremen", 6);
        berlin.setNextStation(bremen);

        Station nice = new Station("Nice", 2);
        bremen.addLineConnection(new MetroLine(""), nice);
        berlin.addLineConnection(new MetroLine(""), nice);

        Station paris = new Station("Paris", 12);
        nice.setNextStation(paris);

        Station beirut = new Station("Beirut", 0);
        paris.addLineConnection(new MetroLine(""), beirut);
        paris.setNextStation(beirut);

        Station aramoun = new Station("Aramoun", 2);
        aramoun.setPreviousStation(beirut);
        beirut.setNextStation(aramoun);

        Station matar = new Station("Matar", 1);
        matar.setPreviousStation(aramoun);
        aramoun.setNextStation(matar);
        nice.addLineConnection(new MetroLine(""), matar);

        List<Node> actual = finder.findPath(berlin, beirut);

        List<Node> expected = List.of(new Node(berlin),
                new Node(nice),
                new Node(matar),
                new Node(aramoun),
                new Node(beirut));

        assertEquals(expected, actual);

    }

    @Test
    void canSearchMultipleTimes() {
        Station berlin = new Station("Berlin", 0);
        Station bremen = new Station("Bremen", 0);
        berlin.setNextStation(bremen);

        Station frankfurt = new Station("Frankfurt", 0);
        bremen.setNextStation(frankfurt);

        finder.findPath(berlin, frankfurt);
        List<Node> actual = finder.findPath(berlin, frankfurt);

        List<Node> expected = List.of(new Node(berlin), new Node(bremen), new Node(frankfurt));

        assertEquals(expected, actual);
    }

}