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
    void nextStationIsEmpty() {
        assertTrue(station.getNextStation().isEmpty());
    }

    @Test
    @DisplayName("Next station is set correctly")
    void nextStation() {
        Station nextStation = new Station("Bremen");
        station.setNextStation(nextStation);

        String expected = "Bremen";
        String nextStationName = station.getNextStation().get().getName();
        assertEquals(expected, nextStationName);
    }
}