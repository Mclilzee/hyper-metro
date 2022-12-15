package metro;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LineConnectionTest {

    MetroLine germany = new MetroLine("Germany");
    Station berlin = new Station("Berlin");
    LineConnection lineConnection = new LineConnection(germany, berlin);

    @Test
    void metroLineName() {
        String expected = "Germany";

        assertEquals(expected, lineConnection.metroLine().getName());
    }

    @Test
    void stationName() {
        String expected = "Berlin";

        assertEquals(expected, lineConnection.station().getName());
    }
}