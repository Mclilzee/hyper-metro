package metro.printing;

import metro.Station;
import metro.search.Node;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FastestPathPrinterTest {

    PathPrinter printer = new FastestPathPrinter();

    @Test
    void getCorrectPathString() {
        Station berlin = new Station("Berlin", 0);
        Station bremen = new Station("Bremen", 0);
        Station frankfurt = new Station("Frankfurt", 0);

        List<Node> nodes = List.of(new Node(berlin, 1), new Node(bremen, 2), new Node(frankfurt, 3));

        String actual = printer.getPathString(nodes);

        String expected = """
                          Berlin
                          Bremen
                          Frankfurt
                          Total: 6 minutes in the way""";

        assertEquals(expected, actual);
    }

    @Test
    void returnEmptyIfConnectionNotFound() {
        String actual = printer.getPathString(List.of());

        String expected = "";

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Ger correct lines if stations has connections")
    void correctStringLineConnections() {
        Station berlin = new Station("Berlin", 0);
        Station bremen = new Station("Bremen", 0);
        Station beirut = new Station("Beirut", 0);
        Station paris = new Station("Paris", 0);

        List<Node> nodes = List.of(new Node(berlin, 3),
                new Node(bremen, 2),
                new Node(beirut, "France"),
                new Node(paris, 10));

        String actual = printer.getPathString(nodes);

        String expected = """
                          Berlin
                          Bremen
                          Transition to line France
                          Beirut
                          Paris
                          Total: 20 minutes in the way""";

        assertEquals(expected, actual);
    }

}