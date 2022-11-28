package hyper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MetroStationsTest {

    MetroStations metroStations = new MetroStations();

    @Test
    @DisplayName("Metro start with depot main station")
    void metroInit() {
        Station head = metroStations.getHead();

        String expectedHeadName = "depot";

        assertEquals(expectedHeadName, head.getName());
    }

    @Test
    @DisplayName("Adding station makes head reference it")
    void addStation() {
        Station berlin = new Station("Berlin");
        metroStations.add(berlin);

        Station nextStation = metroStations.getHead().getNextStation();
        String expected = "Berlin";

        assertEquals(expected, nextStation.getName());
    }

    @Test
    @DisplayName("Add multiple stations connect correctly")
    void addMultipleStations() {
        Station berlin = new Station("Berlin");
        Station bremen = new Station("Bremen");
        metroStations.add(berlin).add(bremen);

        Station nextStation = metroStations.getHead().getNextStation();
        Station finalStation = nextStation.getNextStation();
        String expected = "Bremen";

        assertEquals(expected, finalStation.getName());
    }

    @Test
    @DisplayName("Last station connects back to head")
    void lastStationConnects() {
        Station berlin = new Station("Berlin");
        metroStations.add(berlin);

        Station station = berlin.getNextStation();
        String expected = metroStations.getHead().getName();

        assertEquals(expected, station.getName());
    }
}