package metro;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

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
    void appendStation() {
        Station berlin = new Station("Berlin");
        metroStations.append(berlin);

        Station nextStation = metroStations.getHead().getNextStation().get();
        String expected = "Berlin";

        assertEquals(expected, nextStation.getName());
    }


    @Test
    @DisplayName("Add head of station")
    void addStationHead() {
        Station berlin = new Station("Berlin");
        Station bremen = new Station("Bremen");
        Station beirut = new Station("Beirut");
        metroStations.append(berlin).append(bremen);
        metroStations.addHead(beirut);

        String expected = "Beirut";
        String actual = metroStations.getHead().getNextStation().get().getName();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Append multiple stations connect correctly with next")
    void stationsSetNextCorrectly() {
        Station berlin = new Station("Berlin");
        Station bremen = new Station("Bremen");
        metroStations.append(berlin).append(bremen);

        assertEquals(metroStations.getHead().getNextStation(), Optional.of(berlin));
        assertEquals(berlin.getNextStation(), Optional.of(bremen));
        assertTrue(bremen.getNextStation().isEmpty());
    }

    @Test
    @DisplayName("Append multiple stations connect correctly with previous")
    void stationAppendPrevious() {
        Station berlin = new Station("Berlin");
        Station bremen = new Station("Bremen");
        metroStations.append(berlin).append(bremen);

        assertEquals(bremen.getPreviousStation(), Optional.of(berlin));
        assertEquals(berlin.getPreviousStation(), Optional.of(metroStations.getHead()));
        assertTrue(metroStations.getHead().getPreviousStation().isEmpty());
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