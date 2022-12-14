package metro;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class StationTest {

    Station station = new Station("Berlin", 0);

    @Test
    void hasCorrectName() {
        String expected = "Berlin";

        assertEquals(expected, station.getName());
    }

    @Test
    void timeInitAsZero() {
        assertEquals(0, station.getTime());
    }

    @Test
    void timeIsSetCorrectly() {
        station = new Station("Test", 2);
        assertEquals(2, station.getTime());
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
       Station frankfurt = new Station("Frankfurt", 0);
       station.setPreviousStation(frankfurt);

       String expected = "Frankfurt";
       String previousStationName = station.getPreviousStation().orElseThrow().getName();
       assertEquals(expected, previousStationName);
    }

    @Test
    @DisplayName("Next station is set correctly")
    void nextStation() {
        Station nextStation = new Station("Bremen", 0);
        station.setNextStation(nextStation);

        String expected = "Bremen";
        String nextStationName = station.getNextStation().orElseThrow().getName();
        assertEquals(expected, nextStationName);
    }



    @Test
    void initEmptyConnectionsList() {
        Set<LineConnection> expected = Set.of();

        assertEquals(expected, station.getLineConnections());
    }

    @Test
    void addLineConnections() {
        MetroLine germany = new MetroLine("Germany");
        Station berlin = new Station("Berlin", 0);
        station.addLineConnection(germany, berlin);

        MetroLine lebanon = new MetroLine("Lebanon");
        Station beirut = new Station("Beirut", 0);
        station.addLineConnection(lebanon, beirut);

        Set<LineConnection> expected = Set.of(
                new LineConnection(germany, berlin),
                new LineConnection(lebanon, beirut)
                                               );


        assertEquals(expected, station.getLineConnections());
    }

    @Test
    void lineConnectionNotAddedIfExists() {
        station.addLineConnection(new MetroLine("Germany"), new Station("Berlin", 0));
        station.addLineConnection(new MetroLine("Germany"), new Station("Berlin", 0));
        station.addLineConnection(new MetroLine("Germany"), new Station("Berlin", 0));
        station.addLineConnection(new MetroLine("Lebanon"), new Station( "Beirut", 0));
        station.addLineConnection(new MetroLine("Lebanon"), new Station( "Beirut", 0));

        assertEquals(2, station.getLineConnections().size());
    }

    @Test
    void lineConnectionsSetIsUnmodifiable() {
        assertThrows(UnsupportedOperationException.class, () -> station.getLineConnections().add(null));
    }

    @Test
    void equalsCorrect() {
        Station secondStation = new Station("Berlin", 0);

        assertEquals(secondStation, station);
    }

    @Test
    void notEqual() {
        Station secondStation = new Station("Bremen", 0);

        assertNotEquals(secondStation, station);
    }

    @Test
    void notEqualIfTimeDiffers() {
        Station secondStation = new Station("Berlin", 1);

        assertNotEquals(secondStation, station);
    }

    @Test
    @DisplayName("Not equal if connected stations are not the same")
    void notEqualConnectedStations() {
        Station secondStation = new Station("Berlin", 0);
        secondStation.addLineConnection(new MetroLine("Metro"), new Station("Frankfurt", 0));

        assertNotEquals(secondStation, station);

    }

    @Test
    void containsSameHashcode() {
        Station secondStation = new Station("Berlin", 0);

        assertEquals(secondStation.hashCode(), station.hashCode());
    }
}