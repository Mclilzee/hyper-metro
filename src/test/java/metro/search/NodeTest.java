package metro.search;

import metro.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    Node node = new Node(new Station("Berlin"));

    @Test
    void nodeHasCorrectStation() {
        String expected = "Berlin";
        assertEquals(expected, node.getStation().getName());
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
}