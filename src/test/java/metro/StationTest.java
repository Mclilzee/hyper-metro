package metro;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

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
    void previousStationIsEmpty() {
        assertTrue(station.getPreviousStation().isEmpty());
    }

    @Test
    @DisplayName("Previous station is set correctly")
    void previousStation() {
       Station frankfurt = new Station("Frankfurt");
       station.setPreviousStation(frankfurt);

       String expected = "Frankfurt";
       String previousStationName = station.getPreviousStation().get().getName();
       assertEquals(expected, previousStationName);
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



    @Test
    void initEmptyConnectionsList() {
        List<LineConnection> expected = List.of();

        assertEquals(expected, station.getLineConnections());
    }

    @Test
    void addLineConnections() {
        station.addLineConnection("Germany", "Berlin");
        station.addLineConnection("Lebanon", "Beirut");

        List<LineConnection> expected = List.of(
                new LineConnection("Germany", "Berlin"),
                new LineConnection("Lebanon", "Beirut")
                                               );


        assertEquals(expected, station.getLineConnections());
    }

    @Test
    void lineConnectionNotAddedIfExists() {

        station.addLineConnection("Germany", "Berlin");
        station.addLineConnection("Lebanon", "Beirut");
        station.addLineConnection("Lebanon", "Beirut");

        List<LineConnection> expected = List.of(
                new LineConnection("Germany", "Berlin"),
                new LineConnection("Lebanon", "Beirut")
                                               );


        assertEquals(expected, station.getLineConnections());
    }

    @Test
    void lineConnectionsListIsUnmodifiable() {
        station.addLineConnection("Germany", "Berlin");
        station.addLineConnection("Lebanon", "Beirut");

        assertThrows(UnsupportedOperationException.class, () -> station.getLineConnections().add(null));
    }

    @Test
    void equalsCorrect() {
        Station secondStation = new Station("Berlin");

        assertEquals(secondStation, station);
    }

    @Test
    void notEqual() {
        Station secondStation = new Station("Bremen");

        assertNotEquals(secondStation, station);
    }

    @Test
    @DisplayName("Not equal if connected stations are not the same")
    void notEqualConnectedStations() {
        Station secondStation = new Station("Berlin");
        secondStation.addLineConnection("Metro", "Frankfurt");

        assertNotEquals(secondStation, station);

    }

    @Test
    void containsSameHashcode() {
        Station secondStation = new Station("Berlin");

        assertEquals(secondStation.hashCode(), station.hashCode());
    }
}