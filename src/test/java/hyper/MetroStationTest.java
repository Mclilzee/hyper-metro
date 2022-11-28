package hyper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MetroStationTest {

    MetroStation metroStation = new MetroStation("Berlin");

    @Test
    void hasCorrectName() {
        String expected = "Berlin";

        assertEquals(expected, metroStation.getName());
    }

    @Test
    void nextStationIsNull() {
        assertNull(metroStation.getNextStation());
    }

    @Test
    @DisplayName("Next station is set correctly")
    void nextStation() {
        MetroStation nextStation = new MetroStation("Bremen");
        metroStation.setNextStation(nextStation);

        String expected = "Bremen";
        assertEquals(expected, metroStation.getNextStation().getName());
    }
}