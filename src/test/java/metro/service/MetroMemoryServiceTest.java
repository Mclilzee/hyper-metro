package metro.service;

import metro.LineConnection;
import metro.MetroLine;
import metro.Station;
import metro.printing.LineConnectionsPrinter;
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
        germany.append(new Station("Berlin", 0))
               .append(new Station("Bremen", 0))
               .append(new Station("Beirut", 0));

        metroMemoryService.addMetroLine(germany);

        assertTrue(metroMemoryService.getKeys()
                                     .contains("Germany"));
        assertTrue(metroMemoryService.getValues().contains(germany));
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
        otherGermany.append(new Station("Berlin", 0));
        metroMemoryService.addMetroLine(otherGermany);

        MetroLine actual = metroMemoryService.getValues().get(0);

        assertEquals(germany, actual);
        assertNotEquals(otherGermany, actual);
    }

    @Test
    void addMultipleMetroStations() {
        MetroLine germany = new MetroLine("Germany");
        metroMemoryService.addMetroLine(germany);

        MetroLine lebanon = new MetroLine("Lebanon");
        metroMemoryService.addMetroLine(lebanon);

        germany.append(new Station("Berlin", 0));

        List<MetroLine> actual = metroMemoryService.getValues();

        List<MetroLine> expected = List.of(germany, lebanon);

        assertTrue(actual.containsAll(expected));
    }

    @Test
    void appendStation() {
        metroMemoryService.addMetroLine(new MetroLine("Germany"));
        metroMemoryService.appendStation("Germany", "Berlin", 1);

        MetroLine expected = new MetroLine("Germany");
        expected.append(new Station("Berlin", 1));

        assertTrue(metroMemoryService.getValues().contains(expected));
    }

    @Test
    void addHead() {
        metroMemoryService.addMetroLine(new MetroLine("Germany"));
        metroMemoryService.addHead("Germany", "Berlin", 2);

        MetroLine expected = new MetroLine("Germany");
        expected.addHead(new Station("Berlin", 2));

        assertTrue(metroMemoryService.getValues().contains(expected));
    }

    @Test
    void removeStation() {
        metroMemoryService.addMetroLine(new MetroLine("Germany"));
        metroMemoryService.appendStation("Germany", "Berlin", 1);
        metroMemoryService.appendStation("Germany", "Bremen", 2);
        metroMemoryService.appendStation("Germany", "Beirut", 3);

        metroMemoryService.removeStation("Germany", "Bremen");

        MetroLine expected = new MetroLine("Germany");
        expected.append(new Station("Berlin", 1))
                .append(new Station("Beirut", 3));

        assertTrue(metroMemoryService.getValues().contains(expected));
    }

    @Test
    void getInformationString() {
        MetroLine germany = new MetroLine("Germany");
        metroMemoryService.addMetroLine(germany);
        metroMemoryService.appendStation("Germany", "Berlin", 1);
        metroMemoryService.appendStation("Germany", "Bremen", 2);
        metroMemoryService.appendStation("Germany", "Beirut", 3);

        String actual = metroMemoryService.getMetroLineInformation("Germany");

        String expected = new LineConnectionsPrinter().getMetroLinePrintString(germany);

        assertEquals(expected, actual);
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
    @DisplayName("Return empty value set if no metro lines exists")
    void emptyValueSet() {
        List<MetroLine> expected = List.of();

        assertEquals(expected, metroMemoryService.getValues());
    }

    @Test
    @DisplayName("Return correct value list if metro line exists")
    void correctValueSet() {
        metroMemoryService.addMetroLine(new MetroLine("Germany"));
        metroMemoryService.appendStation("Germany", "Berlin", 1);
        metroMemoryService.appendStation("Germany", "Bremen", 2);

        metroMemoryService.addMetroLine(new MetroLine("Lebanon"));
        metroMemoryService.appendStation("Lebanon", "Beirut", 3);
        metroMemoryService.appendStation("Lebanon", "Aramoun", 4);

        MetroLine firstExpected = new MetroLine("Germany");
        firstExpected.append(new Station("Berlin", 1))
                     .append(new Station("Bremen", 2));

        MetroLine secondExpected = new MetroLine("Lebanon");
        secondExpected.append(new Station("Beirut", 3))
                      .append(new Station("Aramoun", 4));

        List<MetroLine> expected = List.of(firstExpected, secondExpected);

        assertTrue(metroMemoryService.getValues().containsAll(expected));
    }

    @Test
    void connectMetrosCorrectly() {
        MetroLine germany = new MetroLine("Germany");
        metroMemoryService.addMetroLine(germany);
        metroMemoryService.appendStation("Germany", "Berlin", 0);

        MetroLine lebanon = new MetroLine("Lebanon");
        metroMemoryService.addMetroLine(lebanon);
        metroMemoryService.appendStation("Lebanon", "Beirut", 0);

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
        metroMemoryService.appendStation("Germany", "Berlin", 0);

        MetroLine lebanon = new MetroLine("Lebanon");
        metroMemoryService.addMetroLine(lebanon);
        metroMemoryService.appendStation("Lebanon", "Beirut", 0);

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
        metroMemoryService.appendStation("Germany", "Berlin", 0);

        metroMemoryService.connectMetroLine("Germany", "Berlin", "Lebanon", "Beirut");

        Station berlin = germany.findStationByName("Berlin").orElseThrow();


        Set<LineConnection> expected = Set.of();
        assertEquals(expected, berlin.getLineConnections());
    }

    @Test
    void handleNoStationCorrectly() {
        MetroLine germany = new MetroLine("Germany");
        metroMemoryService.addMetroLine(germany);
        metroMemoryService.appendStation("Germany", "Berlin", 0);

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
        Station berlin = new Station("Berlin", 0);
        Station bremen = new Station("Bremen", 0);
        Station frankfurt = new Station("Frankfurt", 0);
        germany.append(berlin).append(bremen).append(frankfurt);


        MetroLine lebanon = new MetroLine("Lebanon");
        Station beirut = new Station("Beirut", 0);
        Station aramoun = new Station("Aramoun", 0);
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
    void getFastestPath() {
        MetroLine germany = new MetroLine("Germany");
        Station berlin = new Station("Berlin", 2);
        Station bremen = new Station("Bremen", 5);
        Station frankfurt = new Station("Frankfurt", 5);
        germany.append(berlin).append(bremen).append(frankfurt);


        MetroLine lebanon = new MetroLine("Lebanon");
        Station beirut = new Station("Beirut", 3);
        Station aramoun = new Station("Aramoun", 5);
        lebanon.append(beirut).append(aramoun);

        germany.addLineConnection(frankfurt, lebanon, beirut);

        metroMemoryService.addMetroLine(germany);
        metroMemoryService.addMetroLine(lebanon);


        String actual = metroMemoryService.findFastestPath("Germany", "Berlin", "Lebanon", "Aramoun");

        String expected = """
                          Berlin
                          Bremen
                          Frankfurt
                          Transition to line Lebanon
                          Beirut
                          Aramoun
                          Total: 20 minutes in the way""";


        assertEquals(expected, actual);
    }

    @Test
    void getShortestPathReturnNotFound() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> metroMemoryService.findShortestPath("Germany",
                        "Berlin",
                        "Lebanon",
                        "Beirut"));

        String actual = exception.getMessage();
        String expected = "No metro line with name Germany";

        assertEquals(expected, actual);
    }

    @Test
    void getShortestPathThrowsNoStation() {
        metroMemoryService.addMetroLine(new MetroLine("Germany"));
        metroMemoryService.addMetroLine(new MetroLine("Lebanon"));

        Exception exception = assertThrows(IllegalArgumentException.class,
                () ->  metroMemoryService.findShortestPath("Germany",
                "Berlin",
                "Lebanon",
                "Beirut"));

        String actual = exception.getMessage();
        String expected = "No station with name Berlin found";

        assertEquals(expected, actual);
    }

    @Test
    void getShortestPathNotFoundConnection() {
        MetroLine germany = new MetroLine("Germany");
        germany.append(new Station("Berlin", 0));
        MetroLine lebanon = new MetroLine("Lebanon");
        lebanon.append(new Station("Beirut", 0));

        metroMemoryService.addMetroLine(germany);
        metroMemoryService.addMetroLine(lebanon);
        String actual =  metroMemoryService.findShortestPath("Germany", "Berlin", "Lebanon", "Beirut");

        String expected = "";

        assertEquals(expected, actual);
    }

    @Test
    void getFastestPathNotFoundConnection() {
        MetroLine germany = new MetroLine("Germany");
        germany.append(new Station("Berlin", 0));
        MetroLine lebanon = new MetroLine("Lebanon");
        lebanon.append(new Station("Beirut", 0));

        metroMemoryService.addMetroLine(germany);
        metroMemoryService.addMetroLine(lebanon);
        String actual =  metroMemoryService.findShortestPath("Germany", "Berlin", "Lebanon", "Beirut");

        String expected = "";

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Return value list is unmodifiable")
    void unmodifiableValues() {
        assertThrows(UnsupportedOperationException.class, () -> metroMemoryService.getValues().add(new MetroLine("")));
    }
}