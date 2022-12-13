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
    @DisplayName("Returns complete list of connected stations")
    void getConnectedStations() {
        metroStations.append("Berlin").append("Bremen").append("Beirut");

        List<Station> expected = List.of(
                new Station("depot"),
                new Station("Berlin"),
                new Station("Bremen"),
                new Station("Beirut")
                                        );

        assertEquals(expected, metroStations.getStationsConnection());
    }

    @Test
    @DisplayName("Append multiple stations connect correctly with next")
    void stationAppendNext() {
        metroStations.append("Berlin").append("Bremen");

        Station actualFirstStation = metroStations.getHead().getNextStation().orElseThrow();
        Station actualNextStation = actualFirstStation.getNextStation().orElseThrow();

        String expectedFirstStation = "Berlin";
        String expectedNextStation = "Bremen";

        assertEquals(expectedFirstStation, actualFirstStation.getName());
        assertEquals(expectedNextStation, actualNextStation.getName());
        assertTrue(actualNextStation.getNextStation().isEmpty());
    }

    @Test
    @DisplayName("Append multiple stations connect correctly with previous")
    void stationAppendPrevious() {
        metroStations.append("Berlin").append("Bremen");

        Station actualLastStation = metroStations.getHead().getNextStation().orElseThrow()
                                                 .getNextStation().orElseThrow();
        Station actualPreviousStation = actualLastStation.getPreviousStation().orElseThrow();

        String expectedLastStation = "Bremen";
        String expectedPreviousStation = "Berlin";

        assertEquals(expectedLastStation, actualLastStation.getName());
        assertEquals(expectedPreviousStation, actualPreviousStation.getName());
        assertTrue(metroStations.getHead().getPreviousStation().isEmpty());
    }

    @Test
    @DisplayName("Add station head set next correctly")
    void stationAddHeadNext() {
        metroStations.addHead("Berlin").addHead("Bremen");

        Station actualFirstStation = metroStations.getHead().getNextStation().orElseThrow();
        Station actualNextStation = actualFirstStation.getNextStation().orElseThrow();

        String expectedFirstStation = "Bremen";
        String expectedNextStation = "Berlin";

        assertEquals(expectedFirstStation, actualFirstStation.getName());
        assertEquals(expectedNextStation, actualNextStation.getName());
        assertTrue(actualNextStation.getNextStation().isEmpty());
    }

    @Test
    @DisplayName("Add station head set previous correctly")
    void stationAddHeadPrevious() {
        metroStations.addHead("Berlin").addHead("Bremen");

        Station actualLastStation = metroStations.getHead().getNextStation().orElseThrow()
                                                 .getNextStation().orElseThrow();
        Station actualPreviousStation = actualLastStation.getPreviousStation().orElseThrow();

        String expectedLastStation = "Berlin";
        String expectedPreviousStation = "Bremen";

        assertEquals(expectedLastStation, actualLastStation.getName());
        assertEquals(expectedPreviousStation, actualPreviousStation.getName());
        assertTrue(metroStations.getHead().getPreviousStation().isEmpty());
    }

    @Test
    @DisplayName("Removing station set next correctly")
    void removeStationNext() {
        metroStations.append("Berlin").append("Bremen").append("Beirut");
        metroStations.removeStation("Bremen");

        Station actualFirstStation = metroStations.getHead().getNextStation().orElseThrow();
        Station actualNextStation = actualFirstStation.getNextStation().orElseThrow();

        String expectedFirstStation = "Berlin";
        String expectedNextStation = "Beirut";

        assertEquals(expectedFirstStation, actualFirstStation.getName());
        assertEquals(expectedNextStation, actualNextStation.getName());
        assertTrue(actualNextStation.getNextStation().isEmpty());
    }

    @Test
    @DisplayName("Removing station set previous correctly")
    void removeStationPrevious() {
        metroStations.append("Berlin").append("Bremen").append("Beirut");
        metroStations.removeStation("Bremen");

        Station actualLastStation = metroStations.getHead().getNextStation().orElseThrow()
                                                 .getNextStation()
                                                 .orElseThrow();
        Station actualPreviousStation = actualLastStation.getPreviousStation().orElseThrow();

        String expectedLastStation = "Beirut";
        String expectedPreviousStation = "Berlin";

        assertEquals(expectedLastStation, actualLastStation.getName());
        assertEquals(expectedPreviousStation, actualPreviousStation.getName());
        assertTrue(metroStations.getHead().getPreviousStation().isEmpty());
    }

    @Test
    void equalStations() {
        metroStations.append("Berlin").append("Bremen").append("Beirut");

        MetroStations newStation = new MetroStations();
        newStation.append("Berlin").append("Bremen").append("Beirut");

        assertEquals(metroStations, newStation);
    }

    @Test
    void notEqual() {
        metroStations.append("Berlin").append("Bremen").append("Frankfurt");

        MetroStations newStation = new MetroStations();
        newStation.append("Berlin").append("Bremen").append("Beirut");

        assertNotEquals(metroStations, newStation);
    }

    @Test
    void addConnectionLine() {
       metroStations.append("Berlin");
       metroStations.addLineConnection("Berlin", "Lebanon", "Beirut");

       Station station = metroStations.getHead().getNextStation().orElseThrow();

       List<LineConnection> expected = List.of(new LineConnection("Lebanon", "Beirut"));

       assertEquals(expected, station.getLineConnections());
    }
}