package metro.search.frontier;

import metro.MetroLine;
import metro.Station;
import metro.search.Node;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class QueueFrontierTest {

    @Test
    void addNode() {
        Frontier frontier = FrontierFactory.getBreadthFrontier();
        Node expected = new Node(new Station("test", 0));
        frontier.addNode(expected);

        Node actual = frontier.pollNode();

        assertEquals(expected,actual);
    }

    @Test
    void doesNotAddSameNodeMultipleTimes() {
        Frontier frontier = FrontierFactory.getBreadthFrontier();
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
    void greedyFrontierReturnsCorrectOrder() {
        Frontier frontier = FrontierFactory.getGreedyBreadthFrontier();

        Station berlin = new Station("Berlin", 0);
        Station bremen = new Station("Bremen", 0);
        Station frankfurt = new Station("Frankfurt", 0);
        Station beirut = new Station("Beirut", 0);

        // should contain weight of 5
        Node berlinNode = new Node(berlin, "Transfer Line Included");

        Node bremenNode = new Node(bremen, 2);
        Node frankfurtNode = new Node(frankfurt, 1);
        Node beirutNode = new Node(beirut, 10);

        frontier.addNode(berlinNode);
        frontier.addNode(bremenNode);
        frontier.addNode(frankfurtNode);
        frontier.addNode(beirutNode);


        assertEquals(frankfurtNode, frontier.pollNode());
        assertEquals(bremenNode, frontier.pollNode());
        assertEquals(berlinNode, frontier.pollNode());
        assertEquals(beirutNode, frontier.pollNode());
    }

    @Test
    void getNodeThrowsIfEmpty() {
        Frontier frontier = FrontierFactory.getBreadthFrontier();
        assertThrows(NoSuchElementException.class, () -> frontier.pollNode());
    }

    @Test
    void getNodeReturnsCorrectNode() {
        Frontier frontier = FrontierFactory.getBreadthFrontier();
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
        Frontier frontier = FrontierFactory.getBreadthFrontier();
        assertTrue(frontier.isEmpty());
    }

    @Test
    void IsNotEmpty() {
        Frontier frontier = FrontierFactory.getBreadthFrontier();
        Node node = new Node(new Station("Test", 0));
        frontier.addNode(node);
        assertFalse(frontier.isEmpty());
    }

    @Test
    void isEmptyAfterPolling() {
        Frontier frontier = FrontierFactory.getBreadthFrontier();
        Node node = new Node(new Station("Test", 0));
        frontier.addNode(node);
        frontier.pollNode();
        assertTrue(frontier.isEmpty());
    }

    @Test
    void addNeighbors() {
        Frontier frontier = FrontierFactory.getBreadthFrontier();
        Station berlin = new Station("Berlin", 0);
        Station bremen = new Station("Bremen", 0);
        Station frankfurt = new Station("Frankfurt", 0);
        Station beirut = new Station("Beirut", 0);

        berlin.setNextStation(bremen);
        berlin.setPreviousStation(frankfurt);
        berlin.addLineConnection(new MetroLine("Test"), beirut);

        Node berlinNode = new Node(berlin);
        frontier.addNeighbors(berlinNode);

        assertDoesNotThrow(frontier::pollNode);
        assertDoesNotThrow(frontier::pollNode);
        assertDoesNotThrow(frontier::pollNode);
        assertTrue(frontier.isEmpty());
    }

    @Test
    @DisplayName("Does not add neighbors if they are already added previously")
    void addNeighborsMultiple() {
        Frontier frontier = FrontierFactory.getBreadthFrontier();
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
        Frontier frontier = FrontierFactory.getBreadthFrontier();

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
        Frontier frontier = FrontierFactory.getBreadthFrontier();
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

    @Test
    void clearVisitedNodes() {
        Frontier frontier = FrontierFactory.getBreadthFrontier();
        Node berlin = new Node(new Station("Berlin", 0));
        frontier.addNode(berlin);
        frontier.clear();

        frontier.addNode(berlin);

        assertFalse(frontier.isEmpty());
    }

}