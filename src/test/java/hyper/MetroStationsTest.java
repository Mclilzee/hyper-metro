package hyper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

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

        Station nextStation = metroStations.getHead().getNextStation().get();
        String expected = "Berlin";

        assertEquals(expected, nextStation.getName());
    }

    @Test
    @DisplayName("Add multiple stations connect correctly")
    void addMultipleStations() {
        Station berlin = new Station("Berlin");
        Station bremen = new Station("Bremen");
        metroStations.add(berlin).add(bremen);

        Station nextStation = metroStations.getHead().getNextStation().get();
        Station finalStation = nextStation.getNextStation().get();
        String expected = "Bremen";

        assertEquals(expected, finalStation.getName());
    }

    @Test
    @DisplayName("Last station connects back to head")
    void lastStationConnects() {
        Station berlin = new Station("Berlin");
        metroStations.add(berlin);

        Station station = berlin.getNextStation().get();
        String expected = metroStations.getHead().getName();

        assertEquals(expected, station.getName());
    }

    @Test
    @DisplayName("Get stations list of three connected stations")
    void getConnectedThreeStationsList() {
        Station head = metroStations.getHead();
        Station berlin = new Station("Berlin");
        Station bremen = new Station("Bremen");
        Station hamburg = new Station("Hamburg");
        Station beirut = new Station("Beirut");
        metroStations.add(berlin).add(bremen).add(hamburg).add(beirut);

        List<Station[]> actual = metroStations.getThreeConnectedStations();
        List<Station[]> expected = List.of(new Station[]{head, berlin, bremen},
                new Station[]{berlin, bremen, hamburg},
                new Station[]{bremen, hamburg, beirut},
                new Station[]{hamburg, beirut, head});

        for (int i = 0; i < expected.size(); i++) {
            assertArrayEquals(expected.get(i), actual.get(i));
        }

    }

    @Test
    @DisplayName("Return empty list if stations unavailable")
    void getConnectedEmptyStations() {
        List<Station[]> expected = List.of();

        assertEquals(expected, metroStations.getThreeConnectedStations());
    }
}