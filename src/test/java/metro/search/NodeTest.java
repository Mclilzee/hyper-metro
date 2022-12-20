package metro.search;

import metro.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    Node node = new Node(new Station("Berlin", 0));

    @Test
    void nodeHasCorrectStation() {
        String expected = "Berlin";
        assertEquals(expected, node.getName());
    }

    @Test
    void hasWeightInitToZero() {
        int expected = 0;
        assertEquals(expected, node.getWeight());
    }

    @Test
    void setWeightCorrectly() {
        Station berlin = new Station("Berlin", 0);
        node = new Node(berlin, 2);
        int expected = 2;
        assertEquals(expected, node.getWeight());
    }

    @Test
    void nodesAreComparable() {
        Station berlin = new Station("Berlin", 0);
        Station bremen = new Station("Bremen", 0);
        Station frankfurt = new Station("Frankfurt", 0);
        Station beirut = new Station("Beirut", 0);

        Node berlinNode = new Node(berlin, 5);
        Node bremenNode = new Node(bremen, 4);
        Node frankfurtNode = new Node(frankfurt, 10);
        Node beirutNode = new Node(beirut, 5);

        assertTrue(berlinNode.compareTo(bremenNode) > 0);
        assertTrue(berlinNode.compareTo(frankfurtNode) < 0);
        assertEquals(0, berlinNode.compareTo(beirutNode));
    }

    @Test
    void weightOfTransferNodeIsInit() {
        Station station = new Station("Berlin", 0);
        node = new Node(station, "Line Transfer", 2);

        int expected = 7;
        assertEquals(expected, node.getWeight());

    }

    @Test
    void initTransferLine() {
        assertTrue(node.getTransferLine().isEmpty());
    }

    @Test
    void transferLineSetCorrectly() {
        node = new Node(new Station("", 0), "Transfer test", 0);
        String expected = "Transfer test";
        assertEquals(expected, node.getTransferLine().orElseThrow());
    }

    @Test
    @DisplayName("Prev returns empty if prev node doesn't exist")
    void prevReturnEmpty() {
        assertTrue(node.getPrev().isEmpty());
    }

    @Test
    @DisplayName("Prev returns correct value")
    void prevReturnsValue() {
        Node bremen = new Node(new Station("Bremen", 0));
        node.setPrev(bremen);

        assertEquals(bremen, node.getPrev().orElseThrow());
    }

    @Test
    void equals() {
        Node expected = new Node(new Station("Berlin", 0));
        assertEquals(expected, node);
    }

    @Test
    void notEqual() {
        Node expected = new Node(new Station("Bremen", 0));
        assertNotEquals(expected, node);
    }


    @Test
    void equalsHashCode() {
        Node expected = new Node(new Station("Berlin", 0));
        assertEquals(expected.hashCode(), node.hashCode());
    }

    @Test
    void notEqualHashCode() {
        Node expected = new Node(new Station("Bremen", 0));
        assertNotEquals(expected.hashCode(), node.hashCode());
    }
}