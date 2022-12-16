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