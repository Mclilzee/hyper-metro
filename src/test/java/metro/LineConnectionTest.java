package metro;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LineConnectionTest {

    MetroLine germany = new MetroLine("Germany");
    Station berlin = new Station("Berlin", 5);
    LineConnection lineConnection = new LineConnection(germany, berlin);

    @Test
    void getMetroLine() {
        assertEquals(germany, lineConnection.metroLine());
    }

    @Test
    void getStation() {
        assertEquals(berlin, lineConnection.station());
    }
}