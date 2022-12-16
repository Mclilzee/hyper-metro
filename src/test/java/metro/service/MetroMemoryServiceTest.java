package metro.service;

import metro.LineConnection;
import metro.MetroLine;
import metro.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MetroMemoryServiceTest {

    MetroMemoryService metroMemoryService = new MetroMemoryService();

    @Test
    void addMetroLine() {
        MetroLine germany = new MetroLine("Germany");
        germany.append(new Station("Berlin"))
               .append(new Station("Bremen"))
               .append(new Station("Beirut"));

        metroMemoryService.addMetroLine(germany);

        assertTrue(metroMemoryService.getKeys()
                                     .contains("Germany"));
        assertEquals(germany, metroMemoryService.getMetroLine("Germany").orElseThrow());
    }

    @Test
    void addMetroStationsDoesNotPutNull() {
        metroMemoryService.addMetroLine(null);

        Set<String> expected = Set.of();
        assertEquals(expected, metroMemoryService.getKeys());
    }

    @Test
    void addMetroStationDoesNotOverwrite() {
        MetroLine germany = new MetroLine("Germany");
        metroMemoryService.addMetroLine(germany);

        MetroLine otherGermany = new MetroLine("Germany");
        otherGermany.append(new Station("Berlin"));
        metroMemoryService.addMetroLine(otherGermany);

        MetroLine actual = metroMemoryService.getMetroLine("Germany").orElseThrow();

        assertEquals(germany, actual);
        assertNotEquals(otherGermany, actual);
    }

    @Test
    void addMultipleMetroStations() {
        MetroLine germany = new MetroLine("Germany");
        metroMemoryService.addMetroLine(germany);

        MetroLine lebanon = new MetroLine("Lebanon");
        metroMemoryService.addMetroLine(lebanon);

        germany.append(new Station("Berlin"));

        assertEquals(germany, metroMemoryService.getMetroLine("Germany").orElseThrow());
        assertEquals(lebanon, metroMemoryService.getMetroLine("Lebanon").orElseThrow());
        assertNotEquals(metroMemoryService.getMetroLine("Germany"), metroMemoryService.getMetroLine("Beirut"));
    }

    @Test
    void appendStation() {
        metroMemoryService.addMetroLine(new MetroLine("Germany"));
        metroMemoryService.appendStation("Germany", "Berlin");

        MetroLine expected = new MetroLine("Germany");
        expected.append(new Station("Berlin"));

        assertEquals(expected, metroMemoryService.getMetroLine("Germany").orElseThrow());
    }

    @Test
    void addHead() {
        metroMemoryService.addMetroLine(new MetroLine("Germany"));
        metroMemoryService.addHead("Germany", "Berlin");

        MetroLine expected = new MetroLine("Germany");
        expected.addHead(new Station("Berlin"));

        assertEquals(expected, metroMemoryService.getMetroLine("Germany").orElseThrow());
    }

    @Test
    void removeStation() {
        metroMemoryService.addMetroLine(new MetroLine("Germany"));
        metroMemoryService.appendStation("Germany", "Berlin");
        metroMemoryService.appendStation("Germany", "Bremen");
        metroMemoryService.appendStation("Germany", "Beirut");

        metroMemoryService.removeStation("Germany", "Bremen");

        MetroLine expected = new MetroLine("Germany");
        expected.append(new Station("Berlin"))
                .append(new Station("Beirut"));

        assertEquals(expected, metroMemoryService.getMetroLine("Germany").orElseThrow());
    }

    @Test
    @DisplayName("Return empty key set if no keys present")
    void getMetroServiceEmptyKeySet() {
        Set<String> keys = metroMemoryService.getKeys();
        Set<String> expected = Set.of();

        assertEquals(expected, keys);
    }

    @Test
    @DisplayName("Return correct key set")
    void getMetroServiceKeySet() {
        metroMemoryService.addMetroLine(new MetroLine("Germany"));
        metroMemoryService.addMetroLine(new MetroLine("Lebanon"));

        Set<String> expected = Set.of("Germany", "Lebanon");

        assertEquals(expected, metroMemoryService.getKeys());
    }

    @Test
    @DisplayName("Key set is unmodifieble")
    void immutableKeySet() {
        assertThrows(UnsupportedOperationException.class, () -> metroMemoryService.getKeys()
                                                                                  .add("Key"));
    }

    @Test
    @DisplayName("Return empty value set if no metro lines exists")
    void emptyValueSet() {
        List<MetroLine> expected = List.of();

        assertEquals(expected, metroMemoryService.getValues());
    }

    @Test
    @DisplayName("Return correct value list if metro line exists")
    void correctValueSet() {
        metroMemoryService.addMetroLine(new MetroLine("Germany"));
        metroMemoryService.appendStation("Germany", "Berlin");
        metroMemoryService.appendStation("Germany", "Bremen");

        metroMemoryService.addMetroLine(new MetroLine("Lebanon"));
        metroMemoryService.appendStation("Lebanon", "Beirut");
        metroMemoryService.appendStation("Lebanon", "Aramoun");

        MetroLine firstExpected = new MetroLine("Germany");
        firstExpected.append(new Station("Berlin"))
                     .append(new Station("Bremen"));

        MetroLine secondExpected = new MetroLine("Lebanon");
        secondExpected.append(new Station("Beirut"))
                      .append(new Station("Aramoun"));

        List<MetroLine> expected = List.of(firstExpected, secondExpected);

        assertTrue(metroMemoryService.getValues().containsAll(expected));
    }

    @Test
    void connectMetrosCorrectly() {
        MetroLine germany = new MetroLine("Germany");
        metroMemoryService.addMetroLine(germany);
        metroMemoryService.appendStation("Germany", "Berlin");

        MetroLine lebanon = new MetroLine("Lebanon");
        metroMemoryService.addMetroLine(lebanon);
        metroMemoryService.appendStation("Lebanon", "Beirut");

        metroMemoryService.connectMetroLine("Germany", "Berlin", "Lebanon", "Beirut");

        Station berlin = germany.findStationByName("Berlin").orElseThrow();
        Station beirut = lebanon.findStationByName("Beirut").orElseThrow();

        Set<LineConnection> expected = Set.of(new LineConnection(lebanon, beirut));
        assertEquals(expected, berlin.getLineConnections());
    }

    @Test
    void connectBothWaysCorrectly() {
        MetroLine germany = new MetroLine("Germany");
        metroMemoryService.addMetroLine(germany);
        metroMemoryService.appendStation("Germany", "Berlin");

        MetroLine lebanon = new MetroLine("Lebanon");
        metroMemoryService.addMetroLine(lebanon);
        metroMemoryService.appendStation("Lebanon", "Beirut");

        metroMemoryService.connectMetroLine("Germany", "Berlin", "Lebanon", "Beirut");

        Station berlin = germany.findStationByName("Berlin").orElseThrow();
        Station beirut = lebanon.findStationByName("Beirut").orElseThrow();


        Set<LineConnection> expected = Set.of(new LineConnection(germany,berlin));
        assertEquals(expected, beirut.getLineConnections());
    }

    @Test
    void doesNotConnectIfMetroLineDoesntExist() {
        MetroLine germany = new MetroLine("Germany");
        metroMemoryService.addMetroLine(germany);
        metroMemoryService.appendStation("Germany", "Berlin");

        metroMemoryService.connectMetroLine("Germany", "Berlin", "Lebanon", "Beirut");

        Station berlin = germany.findStationByName("Berlin").orElseThrow();


        Set<LineConnection> expected = Set.of();
        assertEquals(expected, berlin.getLineConnections());
    }

    @Test
    void handleNoStationCorrectly() {
        MetroLine germany = new MetroLine("Germany");
        metroMemoryService.addMetroLine(germany);
        metroMemoryService.appendStation("Germany", "Berlin");

        MetroLine lebanon = new MetroLine("Lebanon");
        metroMemoryService.addMetroLine(lebanon);
        metroMemoryService.connectMetroLine("Germany", "Berlin", "Lebanon", "Beirut");

        Station berlin = germany.findStationByName("Berlin").orElseThrow();

        Set<LineConnection> expected = Set.of();
        assertEquals(expected, berlin.getLineConnections());
    }

    @Test
    void getShortestPath() {
        MetroLine germany = new MetroLine("Germany");
        Station berlin = new Station("Berlin");
        Station bremen = new Station("Bremen");
        Station frankfurt = new Station("Frankfurt");
        germany.append(berlin).append(bremen).append(frankfurt);


        MetroLine lebanon = new MetroLine("Lebanon");
        Station beirut = new Station("Beirut");
        Station aramoun = new Station("Aramoun");
        lebanon.append(beirut).append(aramoun);

        germany.addLineConnection(frankfurt, lebanon, beirut);

        metroMemoryService.addMetroLine(germany);
        metroMemoryService.addMetroLine(lebanon);


        String actual = metroMemoryService.findShortestPath("Germany", "Berlin", "Lebanon", "Aramoun");

        String expected = """
                          Berlin
                          Bremen
                          Frankfurt
                          Transition to line Lebanon
                          Beirut
                          Aramoun""";


        assertEquals(expected, actual);
    }

    @Test
    void getShortestPathReturnNotFound() {
        String actual = metroMemoryService.findShortestPath("Germany", "Berlin", "Lebanon", "Beirut");

        String expected = "No connection found";

        assertEquals(expected, actual);
    }

    @Test
    void getShortestPathReturnNotFoundStation() {
        MetroLine germany = new MetroLine("Germany");
        MetroLine lebanon = new MetroLine("Lebanon");

        String actual =  metroMemoryService.findShortestPath("Germany", "Berlin", "Lebanon", "Beirut");

        String expected = "No connection found";

        assertEquals(expected, actual);
    }

    @Test
    void getShortestPathNotFoundConnection() {
        MetroLine germany = new MetroLine("Germany");
        germany.append(new Station("Berlin"));
        MetroLine lebanon = new MetroLine("Lebanon");
        lebanon.append(new Station("Beirut"));

        String actual =  metroMemoryService.findShortestPath("Germany", "Berlin", "Lebanon", "Beirut");

        String expected = "No connection found";
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Return value list is unmodifiable")
    void unmodifiableValues() {
        assertThrows(UnsupportedOperationException.class, () -> metroMemoryService.getValues().add(new MetroLine("")));
    }
}