package metro;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
    void appendStation() {
        Station berlin = new Station("Berlin");
        metroStations.append(berlin);

        Station nextStation = metroStations.getHead().getNextStation().get();
        String expected = "Berlin";

        assertEquals(expected, nextStation.getName());
    }

    @Test
    @DisplayName("Add multiple stations connect correctly with next")
    void stationsSetNextCorrectly() {
        Station berlin = new Station("Berlin");
        Station bremen = new Station("Bremen");
        metroStations.append(berlin).append(bremen);

        Station nextStation = metroStations.getHead().getNextStation().get();
        Station finalStation = nextStation.getNextStation().get();
        String expected = "Bremen";

        assertEquals(expected, finalStation.getName());
    }

    @Test
    @DisplayName("Add multiple stations connect correctly with previous")
    void stationsPreviousConnect() {
        Station berlin = new Station("Berlin");
        Station bremen = new Station("Bremen");
        metroStations.append(berlin).append(bremen);

        String actual = bremen.getPreviousStation().get().getName();
        String expected = "Berlin";

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Add head of station")
    void addStationHead() {

    }

    @Test
    void equalStations() {
        Station berlin = new Station("Berlin");
        Station bremen = new Station("Bremen");
        Station beirut = new Station("Beirut");
        metroStations.append(berlin).append(bremen).append(beirut);

        MetroStations newStation = new MetroStations();
        Station berlin2 = new Station("Berlin");
        Station bremen2 = new Station("Bremen");
        Station beirut2 = new Station("Beirut");
        newStation.append(berlin2).append(bremen2).append(beirut2);

        assertEquals(metroStations, newStation);
    }

    @Test
    void notEqual() {
        Station berlin = new Station("Berlin");
        Station bremen = new Station("Bremen");
        Station frankfurt = new Station("Frankfurt");
        metroStations.append(berlin).append(bremen).append(frankfurt);

        MetroStations newStation = new MetroStations();
        Station berlin2 = new Station("Berlin");
        Station bremen2 = new Station("Bremen");
        Station beirut2 = new Station("Beirut");
        newStation.append(berlin2).append(bremen2).append(beirut2);

        assertNotEquals(metroStations, newStation);
    }
}