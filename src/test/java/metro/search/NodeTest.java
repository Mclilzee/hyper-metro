package metro.search;

import metro.LineConnection;
import metro.MetroLine;
import metro.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    Node node = new Node(new Station("Berlin"));

    @Test
    void nodeHasCorrectStation() {
        String expected = "Berlin";
        assertEquals(expected, node.getStation().getName());
    }

    @Test
    void startWithFalseVisited() {
        boolean expected = false;
        assertEquals(expected, node.isVisited());
    }

    @Test
    void setVisited() {
        boolean expected = true;
        node.setVisited(true);
        assertEquals(expected, node.isVisited());
    }

    @Test
    void initTransferStation() {
        boolean expected = false;
        assertEquals(expected, node.isTransferStation());
    }

    @Test
    void transferStationSetCorrectly() {
        boolean expected = true;
        node.setTransferStation(true);
        assertEquals(expected, node.isTransferStation());
    }

    @Test
    @DisplayName("Prev returns empty if prev node doesn't exist")
    void prevReturnEmpty() {
        assertTrue(node.getPrev().isEmpty());
    }

    @Test
    @DisplayName("Prev returns correct value")
    void prevReturnsValue() {
        Node bremen = new Node(new Station("Bremen"));
        node.setPrev(bremen);

        assertEquals(bremen, node.getPrev().orElseThrow());
    }

    @Test
    void initWithNeighbors() {
        Set<Node> expected = Set.of();

        assertEquals(expected, node.getNeighbors());
    }

    @Test
    void setNeighborsCorrectly() {
        Station berlin = new Station("Berlin");
        Station bremen = new Station("Bremen");
        Station frankfurt = new Station("Frankfurt");
        Station beirut = new Station("Beirut");

        berlin.setNextStation(bremen);
        berlin.setPreviousStation(frankfurt);
        berlin.addLineConnection(new MetroLine(""), beirut);

        Node actual = new Node(berlin);

        Set<Node> expected = Set.of(new Node(bremen), new Node(frankfurt), new Node(beirut));
        assertEquals(expected, actual.getNeighbors());
    }

    @Test
    @DisplayName("Line connection neighbours are set as transfer stations")
    void lineConnectionNeighbors() {
        Station berlin = new Station("Berlin");
        Station frankfurt = new Station("Frankfurt");
        Station bremen = new Station("Beirut");

        berlin.addLineConnection(new MetroLine(""), bremen);
        berlin.addLineConnection(new MetroLine(""), frankfurt);

        Node actual = new Node(berlin);

        for (Node node : actual.getNeighbors()) {
            assertTrue(node.isTransferStation());
        }
    }

    @Test
    @DisplayName("None line connection neighbors transfer is boolean is false")
    void neighborsOnSameLine() {
        Station berlin = new Station("Berlin");

        Station bremen = new Station("Bremen");
        Station frankfurt = new Station("Frankfurt");
        berlin.setNextStation(bremen);
        berlin.setPreviousStation(frankfurt);

        Node actual = new Node(berlin);

        for (Node node : actual.getNeighbors()) {
            assertFalse(node.isTransferStation());
        }
    }

    @Test
    void equals() {
        Node expected = new Node(new Station("Berlin"));
        assertEquals(expected, node);
    }

    @Test
    void notEqual() {
        Node expected = new Node(new Station("Bremen"));
        assertNotEquals(expected, node);
    }


    @Test
    void equalsHashCode() {
        Node expected = new Node(new Station("Berlin"));
        assertEquals(expected.hashCode(), node.hashCode());
    }

    @Test
    void notEqualHashCode() {
        Node expected = new Node(new Station("Bremen"));
        assertNotEquals(expected.hashCode(), node.hashCode());
    }
}