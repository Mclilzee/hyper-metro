package metro.service;

import metro.MetroLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MetroMemoryServiceTest {

    MetroMemoryService metroMemoryService = new MetroMemoryService();

    @Test
    void addMetroStation() {
        metroMemoryService.addMetroLine("Germany");

        assertNotNull(metroMemoryService.getMetroLine("Germany"));
    }

    @Test
    void addMultipleMetroStations() {
        metroMemoryService.addMetroLine("Germany");
        metroMemoryService.addMetroLine("Beirut");

        MetroLine germany = metroMemoryService.getMetroLine("Germany");
        germany.append("Berlin");

        assertNotNull(metroMemoryService.getMetroLine("Germany"));
        assertNotNull(metroMemoryService.getMetroLine("Beirut"));
        assertNotEquals(metroMemoryService.getMetroLine("Germany"), metroMemoryService.getMetroLine("Beirut"));
    }

    @Test
    void addMetroStationDoesNotOverwrite() {
        metroMemoryService.addMetroLine("Germany");
        MetroLine metroStations = metroMemoryService.getMetroLine("Germany");
        metroStations.append("Berlin");

        metroMemoryService.addMetroLine("Germany");

        assertEquals(metroStations, metroMemoryService.getMetroLine("Germany"));
    }

    @Test
    void appendStation() {
        metroMemoryService.addMetroLine("Germany");
        metroMemoryService.appendStation("Germany", "Berlin");

        MetroLine expected = new MetroLine();
        expected.append("Berlin");

        assertEquals(expected, metroMemoryService.getMetroLine("Germany"));
    }

    @Test
    void addHead() {
        metroMemoryService.addMetroLine("Germany");
        metroMemoryService.addHead("Germany", "Berlin");

        MetroLine expected = new MetroLine();
        expected.addHead("Berlin");

        assertEquals(expected, metroMemoryService.getMetroLine("Germany"));
    }

    @Test
    void removeStation() {
        metroMemoryService.addMetroLine("Germany");
        metroMemoryService.appendStation("Germany", "Berlin");
        metroMemoryService.appendStation("Germany", "Bremen");
        metroMemoryService.appendStation("Germany", "Beirut");

        metroMemoryService.removeStation("Germany", "Bremen");

        MetroLine expected = new MetroLine();
        expected.append("Berlin")
                .append("Beirut");

        assertEquals(expected, metroMemoryService.getMetroLine("Germany"));
    }

    @Test
    void putMetroStations() {
        MetroLine metroStations = new MetroLine();
        metroStations.append("Berlin")
                     .append("Bremen")
                     .append("Beirut");

        metroMemoryService.putMetroLine("Germany", metroStations);

        assertTrue(metroMemoryService.getKeys()
                                     .contains("Germany"));
        assertEquals(metroMemoryService.getMetroLine("Germany"), metroStations);
    }

    @Test
    void putMetroStationDoesNotOverwrite() {
        MetroLine metroStations = new MetroLine();
        metroStations.append("Berlin")
                     .append("Bremen")
                     .append("Beirut");

        metroMemoryService.addMetroLine("Germany");
        metroMemoryService.putMetroLine("Germany", metroStations);

        assertNotEquals(metroMemoryService.getMetroLine("Germany"), metroStations);
    }

    @Test
    void putMetroStationsDoesNotPutNull() {
        metroMemoryService.putMetroLine("Germany", null);

        Set<String> expected = Set.of();
        assertEquals(expected, metroMemoryService.getKeys());
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
        metroMemoryService.addMetroLine("Germany");
        metroMemoryService.addMetroLine("Lebanon");

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
    @DisplayName("Return empty value set if no metro stations exists")
    void emptyValueSet() {
        List<MetroLine> expected = List.of();

        assertEquals(expected, metroMemoryService.getValues());
    }

    @Test
    @DisplayName("Return correct value list if metro stations exists")
    void correctValueSet() {
        metroMemoryService.addMetroLine("Germany");
        metroMemoryService.appendStation("Germany", "Berlin");
        metroMemoryService.appendStation("Germany", "Bremen");

        metroMemoryService.addMetroLine("Lebanon");
        metroMemoryService.appendStation("Lebanon", "Beirut");
        metroMemoryService.appendStation("Lebanon", "Aramoun");

        MetroLine firstExpected = new MetroLine();
        firstExpected.append("Berlin")
                     .append("Bremen");

        MetroLine secondExpected = new MetroLine();
        secondExpected.append("Beirut")
                      .append("Aramoun");

        List<MetroLine> expected = List.of(firstExpected, secondExpected);

        assertTrue(metroMemoryService.getValues()
                                     .containsAll(expected));
    }

    @Test
    @DisplayName("Return value list is unmodifiable")
    void unmodifiableValues() {
        assertThrows(UnsupportedOperationException.class, () -> metroMemoryService.getValues()
                                                                                  .add(new MetroLine()));
    }
}