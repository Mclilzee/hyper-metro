package hyper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StationTest {

    Station station = new Station("Berlin");

    @Test
    void hasCorrectName() {
        String expected = "Berlin";

        assertEquals(expected, station.getName());
    }

    @Test
    void nextStationIsNull() {
        assertNull(station.getNextStation());
    }

    @Test
    @DisplayName("Next station is set correctly")
    void nextStation() {
        Station nextStation = new Station("Bremen");
        station.setNextStation(nextStation);

        String expected = "Bremen";
        assertEquals(expected, station.getNextStation().getName());
    }
}