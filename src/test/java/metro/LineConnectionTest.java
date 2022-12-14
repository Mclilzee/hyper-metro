package metro;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LineConnectionTest {

    LineConnection lineConnection = new LineConnection("Berlin", "Germany");

    @Test
    void metroLineName() {
        String expected = "Germany";

        assertEquals(expected, lineConnection.metroLineName());
    }

    @Test
    void stationName() {
        String expected = "Berlin";

        assertEquals(expected, lineConnection.stationName());
    }
}