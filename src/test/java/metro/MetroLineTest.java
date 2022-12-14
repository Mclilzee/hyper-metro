package metro;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MetroLineTest {

    MetroLine metroLine = new MetroLine();

    @Test
    @DisplayName("Metro start with depot main station")
    void metroInit() {
        Station head = metroLine.getHead();

        String expectedHeadName = "depot";

        assertEquals(expectedHeadName, head.getName());
    }

    @Test
    @DisplayName("Returns complete stream of connected stations")
    void getConnectedStations() {
        metroLine.append("Berlin").append("Bremen").append("Beirut");

        List<Station> expected = List.of(
                new Station("depot"),
                new Station("Berlin"),
                new Station("Bremen"),
                new Station("Beirut")
                                        );

        assertEquals(expected, metroLine.stream().toList());
    }

    @Test
    @DisplayName("Append multiple stations connect correctly with next")
    void stationAppendNext() {
        metroLine.append("Berlin").append("Bremen");

        Station actualFirstStation = metroLine.getHead().getNextStation().orElseThrow();
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
        metroLine.append("Berlin").append("Bremen");

        Station actualLastStation = metroLine.getHead().getNextStation().orElseThrow()
                                             .getNextStation().orElseThrow();
        Station actualPreviousStation = actualLastStation.getPreviousStation().orElseThrow();

        String expectedLastStation = "Bremen";
        String expectedPreviousStation = "Berlin";

        assertEquals(expectedLastStation, actualLastStation.getName());
        assertEquals(expectedPreviousStation, actualPreviousStation.getName());
        assertTrue(metroLine.getHead().getPreviousStation().isEmpty());
    }

    @Test
    @DisplayName("Add station head set next correctly")
    void stationAddHeadNext() {
        metroLine.addHead("Berlin").addHead("Bremen");

        Station actualFirstStation = metroLine.getHead().getNextStation().orElseThrow();
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
        metroLine.addHead("Berlin").addHead("Bremen");

        Station actualLastStation = metroLine.getHead().getNextStation().orElseThrow()
                                             .getNextStation().orElseThrow();
        Station actualPreviousStation = actualLastStation.getPreviousStation().orElseThrow();

        String expectedLastStation = "Berlin";
        String expectedPreviousStation = "Bremen";

        assertEquals(expectedLastStation, actualLastStation.getName());
        assertEquals(expectedPreviousStation, actualPreviousStation.getName());
        assertTrue(metroLine.getHead().getPreviousStation().isEmpty());
    }

    @Test
    @DisplayName("Removing station set next correctly")
    void removeStationNext() {
        metroLine.append("Berlin").append("Bremen").append("Beirut");
        metroLine.removeStation("Bremen");

        Station actualFirstStation = metroLine.getHead().getNextStation().orElseThrow();
        Station actualNextStation = actualFirstStation.getNextStation().orElseThrow();

        String expectedFirstStation = "Berlin";
        String expectedNextStation = "Beirut";

        assertEquals(expectedFirstStation, actualFirstStation.getName());
        assertEquals(expectedNextStation, actualNextStation.getName());
        assertTrue(actualNextStation.getNextStation().isEmpty());
    }

    @Test
    @DisplayName("Does nothing if station not found")
    void removeStationNotFound() {

        metroLine.append("Berlin").append("Bremen").append("Beirut");
        metroLine.removeStation("Dongos");

        Station actualFirstStation = metroLine.getHead().getNextStation().orElseThrow();
        Station actualNextStation = actualFirstStation.getNextStation().orElseThrow();
        Station actualLastStation = actualNextStation.getNextStation().orElseThrow();

        String expectedFirstStation = "Berlin";
        String expectedNextStation = "Bremen";
        String expectedLastStation = "Beirut";

        assertEquals(expectedFirstStation, actualFirstStation.getName());
        assertEquals(expectedNextStation, actualNextStation.getName());
        assertEquals(expectedLastStation, actualLastStation.getName());
        assertTrue(actualLastStation.getNextStation().isEmpty());
    }

    @Test
    @DisplayName("Removing station set previous correctly")
    void removeStationPrevious() {
        metroLine.append("Berlin").append("Bremen").append("Beirut");
        metroLine.removeStation("Bremen");

        Station actualLastStation = metroLine.getHead().getNextStation().orElseThrow()
                                             .getNextStation()
                                             .orElseThrow();
        Station actualPreviousStation = actualLastStation.getPreviousStation().orElseThrow();

        String expectedLastStation = "Beirut";
        String expectedPreviousStation = "Berlin";

        assertEquals(expectedLastStation, actualLastStation.getName());
        assertEquals(expectedPreviousStation, actualPreviousStation.getName());
        assertTrue(metroLine.getHead().getPreviousStation().isEmpty());
    }

    @Test
    void addConnectionLine() {
        metroLine.append("Berlin");
        metroLine.addLineConnection("Berlin", "Lebanon", "Beirut");

        Station station = metroLine.getHead().getNextStation().orElseThrow();

        List<LineConnection> expected = List.of(new LineConnection("Lebanon", "Beirut"));

        assertEquals(expected, station.getLineConnections());
    }

    @Test
    void equalStations() {
        metroLine.append("Berlin").append("Bremen").append("Beirut");

        MetroLine newStation = new MetroLine();
        newStation.append("Berlin").append("Bremen").append("Beirut");

        assertEquals(metroLine, newStation);
    }

    @Test
    void hasSameHashCode() {
        metroLine.append("Berlin").append("Bremen").append("Beirut");

        MetroLine newStation = new MetroLine();
        newStation.append("Berlin").append("Bremen").append("Beirut");

        assertEquals(metroLine.hashCode(), newStation.hashCode());
    }

    @Test
    void notEqual() {
        metroLine.append("Berlin").append("Bremen").append("Frankfurt");

        MetroLine newStation = new MetroLine();
        newStation.append("Berlin").append("Bremen").append("Beirut");

        assertNotEquals(metroLine, newStation);
    }

    @Test
    void notEqualsNull() {
        assertNotEquals(metroLine, null);
    }

    @Test
    void notEqualWrongObject() {
        assertNotEquals(metroLine, new Object());
    }
}