package metro;

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
    @DisplayName("Add multiple stations connect correctly with next")
    void stationsSetNextCorrectly() {
        Station berlin = new Station("Berlin");
        Station bremen = new Station("Bremen");
        metroStations.add(berlin).add(bremen);

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
        metroStations.add(berlin).add(bremen);

        String actual = bremen.getPreviousStation().get().getName();
        String expected = "Berlin";

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Get stations list of three connected stations")
    void getConnectedThreeStationsList() {
        Station berlin = new Station("Berlin");
        Station bremen = new Station("Bremen");
        Station hamburg = new Station("Hamburg");
        Station beirut = new Station("Beirut");
        metroStations.add(berlin).add(bremen).add(hamburg).add(beirut);

        List<String> actual = metroStations.getThreeConnectedStations();
        List<String> expected = List.of(
                "depot - Berlin - Bremen",
                "Berlin - Bremen - Hamburg",
                "Bremen - Hamburg - Beirut",
                "Hamburg - Beirut - depot"
                );
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Return empty list if stations unavailable")
    void getConnectedEmptyStations() {
        List<String> expected = List.of();
        assertEquals(expected, metroStations.getThreeConnectedStations());
    }
}