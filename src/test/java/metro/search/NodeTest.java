package metro.search;

import metro.Station;
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
}