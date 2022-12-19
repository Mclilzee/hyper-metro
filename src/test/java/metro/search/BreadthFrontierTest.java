package metro.search;

import metro.MetroLine;
import metro.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class BreadthFrontierTest {

    Frontier frontier = new BreadthFrontier();

    @Test
    void addNode() {
        Node expected = new Node(new Station("test", 0));
        frontier.addNode(expected);

        Node actual = frontier.pollNode();

        assertEquals(expected,actual);
    }

    @Test
    void doesNotAddSameNodeMultipleTimes() {
        Node first = new Node(new Station("", 0));
        Node second = new Node(new Station("", 0));
        Node third = new Node(new Station("", 0));
        Node fourth = new Node(new Station("", 0));

        frontier.addNode(first);
        frontier.addNode(second);
        frontier.addNode(third);
        frontier.addNode(fourth);

        frontier.pollNode();

        assertTrue(frontier.isEmpty());
    }

    @Test
    void getNodeThrowsIfEmpty() {
        assertThrows(NoSuchElementException.class, () -> frontier.pollNode());
    }

    @Test
    void getNodeReturnsCorrectNode() {
        Station berlin = new Station("Berlin", 0);
        Node berlinNode = new Node(berlin);

        Station bremen = new Station("Bremen", 0);
        Node bremenNode = new Node(bremen);

        frontier.addNode(berlinNode);
        frontier.addNode(bremenNode);

        Node actual = frontier.pollNode();

        assertEquals(berlinNode, actual);
    }

    @Test
    void isEmpty() {
        assertTrue(frontier.isEmpty());
    }

    @Test
    void IsNotEmpty() {
        Node node = new Node(new Station("Test", 0));
        frontier.addNode(node);
        assertFalse(frontier.isEmpty());
    }

    @Test
    void isEmptyAfterPolling() {
        Node node = new Node(new Station("Test", 0));
        frontier.addNode(node);
        frontier.pollNode();
        assertTrue(frontier.isEmpty());
    }

    @Test
    void addNeighbors() {
        Station berlin = new Station("Berlin", 0);
        Station bremen = new Station("Bremen", 0);
        Station frankfurt = new Station("Frankfurt", 0);
        Station beirut = new Station("Beirut", 0);

        berlin.setNextStation(bremen);
        berlin.setPreviousStation(frankfurt);
        berlin.addLineConnection(new MetroLine("Test"), beirut);

        Node berlinNode = new Node(berlin);
        frontier.addNeighbors(berlinNode);

        assertDoesNotThrow(() -> frontier.pollNode());
        assertDoesNotThrow(() -> frontier.pollNode());
        assertDoesNotThrow(() -> frontier.pollNode());
        assertTrue(frontier.isEmpty());
    }

    @Test
    @DisplayName("Does not add neighbors if they are already added previously")
    void addNeighborsMultiple() {
        Station berlin = new Station("Berlin", 0);
        Station bremen = new Station("Bremen", 0);
        Station frankfurt = new Station("Frankfurt", 0);
        Station beirut = new Station("Beirut", 0);

        berlin.setNextStation(bremen);
        berlin.setPreviousStation(frankfurt);
        berlin.addLineConnection(new MetroLine("Test"), beirut);

        Node berlinNode = new Node(berlin);
        frontier.addNeighbors(berlinNode);
        frontier.addNeighbors(berlinNode);
        frontier.addNeighbors(berlinNode);

        frontier.pollNode();
        frontier.pollNode();
        frontier.pollNode();

        assertTrue(frontier.isEmpty());
    }

    @Test
    void addNeighborsSetsPrevCorrectly() {

        Station berlin = new Station("Berlin", 0);
        Station bremen = new Station("Bremen", 0);
        Station frankfurt = new Station("Frankfurt", 0);
        Station beirut = new Station("Beirut", 0);

        berlin.setNextStation(bremen);
        berlin.setPreviousStation(frankfurt);
        berlin.addLineConnection(new MetroLine("Test"), beirut);

        Node berlinNode = new Node(berlin);
        frontier.addNeighbors(berlinNode);

        assertEquals(berlinNode, frontier.pollNode().getPrev().orElseThrow());
        assertEquals(berlinNode, frontier.pollNode().getPrev().orElseThrow());
        assertEquals(berlinNode, frontier.pollNode().getPrev().orElseThrow());
    }

    @Test
    void clearNodes() {
        Node first = new Node(new Station("First", 0));
        Node second = new Node(new Station("Second", 0));
        Node third = new Node(new Station("Third", 0));
        Node fourth = new Node(new Station("Fourth", 0));

        frontier.addNode(first);
        frontier.addNode(second);
        frontier.addNode(third);
        frontier.addNode(fourth);

        frontier.clear();
        assertTrue(frontier.isEmpty());
    }

}