package metro.service;

import metro.MetroStations;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MetroMemoryServiceTest {

    MetroMemoryService metroMemoryService = new MetroMemoryService();

    @Test
    void addMetroStation() {
        metroMemoryService.addMetroStations("Germany");

        assertNotNull(metroMemoryService.getMetroStations("Germany"));
    }

    @Test
    void addMultipleMetroStations() {
        metroMemoryService.addMetroStations("Germany");
        metroMemoryService.addMetroStations("Beirut");

        MetroStations germany = metroMemoryService.getMetroStations("Germany");
        germany.append("Berlin");

        assertNotNull(metroMemoryService.getMetroStations("Germany"));
        assertNotNull(metroMemoryService.getMetroStations("Beirut"));
        assertNotEquals(metroMemoryService.getMetroStations("Germany"), metroMemoryService.getMetroStations("Beirut"));
    }

    @Test
    void addMetroStationDoesNotOverwrite() {
        metroMemoryService.addMetroStations("Germany");
        MetroStations metroStations = metroMemoryService.getMetroStations("Germany");
        metroStations.append("Berlin");

        metroMemoryService.addMetroStations("Germany");

        assertEquals(metroStations, metroMemoryService.getMetroStations("Germany"));
    }

    @Test
    void appendStation() {
        metroMemoryService.addMetroStations("Germany");
        metroMemoryService.appendStation("Germany", "Berlin");

        MetroStations expected = new MetroStations();
        expected.append("Berlin");

        assertEquals(expected, metroMemoryService.getMetroStations("Germany"));
    }

    @Test
    void addHead() {
        metroMemoryService.addMetroStations("Germany");
        metroMemoryService.addHead("Germany", "Berlin");

        MetroStations expected = new MetroStations();
        expected.addHead("Berlin");

        assertEquals(expected, metroMemoryService.getMetroStations("Germany"));
    }

    @Test
    void removeStation() {
        metroMemoryService.addMetroStations("Germany");
        metroMemoryService.appendStation("Germany", "Berlin");
        metroMemoryService.appendStation("Germany", "Bremen");
        metroMemoryService.appendStation("Germany", "Beirut");

        metroMemoryService.removeStation("Germany", "Bremen");

        MetroStations expected = new MetroStations();
        expected.append("Berlin").append("Beirut");

        assertEquals(expected, metroMemoryService.getMetroStations("Germany"));
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
        metroMemoryService.addMetroStations("Germany");
        metroMemoryService.addMetroStations("Lebanon");

        Set<String> expected = Set.of("Germany", "Lebanon");

        assertEquals(expected, metroMemoryService.getKeys());
    }

    @Test
    @DisplayName("Key set is unmodifieble")
    void immutableKeySet() {
        assertThrows(UnsupportedOperationException.class, () -> metroMemoryService.getKeys().add("Key"));
    }

    @Test
    @DisplayName("Return empty value set if no metro stations exists")
    void emptyValueSet() {
        List<MetroStations> expected = List.of();

        assertEquals(expected, metroMemoryService.getValues());
    }

    @Test
    @DisplayName("Return correct value list if metro stations exists")
    void correctValueSet() {
        metroMemoryService.addMetroStations("Germany");
        metroMemoryService.appendStation("Germany", "Berlin");
        metroMemoryService.appendStation("Germany", "Bremen");

        metroMemoryService.addMetroStations("Lebanon");
        metroMemoryService.appendStation("Lebanon", "Beirut");
        metroMemoryService.appendStation("Lebanon", "Aramoun");

        MetroStations firstExpected = new MetroStations();
        firstExpected.append("Berlin").append("Bremen");

        MetroStations secondExpected = new MetroStations();
        secondExpected.append("Beirut").append("Aramoun");

        List<MetroStations> expected = List.of(firstExpected, secondExpected);

        assertTrue(metroMemoryService.getValues().containsAll(expected));
    }

    @Test
    @DisplayName("Return value list is unmodifiable")
    void unmodifiableValues() {
        assertThrows(UnsupportedOperationException.class, () -> metroMemoryService.getValues().add(new MetroStations()));
    }
}