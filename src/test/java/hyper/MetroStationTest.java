package hyper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MetroStationTest {

    MetroStation metroStation = new MetroStation("Berlin");

    @Test
    void hasCorrectName() {
        String expected = "Berlin";

        assertEquals(expected, metroStation.name());
    }
}