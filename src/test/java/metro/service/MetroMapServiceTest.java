package metro.service;

import metro.MetroStations;
import metro.service.MetroMapService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MetroMapServiceTest {

    MetroMapService metroMapService = new MetroMapService();

    @Test
    void addMetroStation() {
        metroMapService.addMetroStations("Germany");

        assertNotNull(metroMapService.getMetroStations("Germany"));
    }

    @Test
    void addMultipleMetroStations() {
        metroMapService.addMetroStations("Germany");
        metroMapService.addMetroStations("Beirut");

        MetroStations germany = metroMapService.getMetroStations("Germany");
        germany.append("Berlin");

        assertNotNull(metroMapService.getMetroStations("Germany"));
        assertNotNull(metroMapService.getMetroStations("Beirut"));
        assertNotEquals(metroMapService.getMetroStations("Germany"), metroMapService.getMetroStations("Beirut"));
    }

    @Test
    void addMetroStationDoesNotOverwrite() {
        metroMapService.addMetroStations("Germany");
        MetroStations metroStations = metroMapService.getMetroStations("Germany");
        metroStations.append("Berlin");

        metroMapService.addMetroStations("Germany");

        assertEquals(metroStations, metroMapService.getMetroStations("Germany"));
    }

    @Test
    void appendStation() {
        metroMapService.addMetroStations("Germany");
        metroMapService.appendStation("Germany", "Berlin");

        MetroStations expected = new MetroStations();
        expected.append("Berlin");

        assertEquals(expected, metroMapService.getMetroStations("Germany"));
    }

    @Test
    void addHead() {
        metroMapService.addMetroStations("Germany");
        metroMapService.addHead("Germany", "Berlin");

        MetroStations expected = new MetroStations();
        expected.addHead("Berlin");

        assertEquals(expected, metroMapService.getMetroStations("Germany"));
    }

    @Test
    void removeStation() {
        metroMapService.addMetroStations("Germany");
        metroMapService.appendStation("Germany", "Berlin");
        metroMapService.appendStation("Germany", "Bremen");
        metroMapService.appendStation("Germany", "Beirut");

        metroMapService.removeStation("Germany", "Bremen");

        MetroStations expected = new MetroStations();
        expected.append("Berlin").append("Beirut");

        assertEquals(expected, metroMapService.getMetroStations("Germany"));
    }

    @Test
    @DisplayName("Return empty key set if no keys present")
    void getMetroServiceEmptyKeySet() {
        Set<String> keys = metroMapService.getKeys();
        Set<String> expected = Set.of();

        assertEquals(expected, keys);
    }

    @Test
    @DisplayName("Return correct key set")
    void getMetroServiceKeySet() {
        metroMapService.addMetroStations("Germany");
        metroMapService.addMetroStations("Lebanon");

        Set<String> expected = Set.of("Germany", "Lebanon");

        assertEquals(expected, metroMapService.getKeys());
    }

    @Test
    @DisplayName("Key set is unmodifieble")
    void immutableKeySet() {
        assertThrows(UnsupportedOperationException.class, () -> metroMapService.getKeys().add("Key"));
    }

    @Test
    @DisplayName("Return empty value set if no metro stations exists")
    void emptyValueSet() {
        List<MetroStations> expected = List.of();

        assertEquals(expected, metroMapService.getValues());
    }

    @Test
    @DisplayName("Return correct value list if metro stations exists")
    void correctValueSet() {
        metroMapService.addMetroStations("Germany");
        metroMapService.appendStation("Germany", "Berlin");
        metroMapService.appendStation("Germany", "Bremen");

        metroMapService.addMetroStations("Lebanon");
        metroMapService.appendStation("Lebanon", "Beirut");
        metroMapService.appendStation("Lebanon", "Aramoun");

        MetroStations firstExpected = new MetroStations();
        firstExpected.append("Berlin").append("Bremen");

        MetroStations secondExpected = new MetroStations();
        secondExpected.append("Beirut").append("Aramoun");

        List<MetroStations> expected = List.of(firstExpected, secondExpected);

        assertTrue(metroMapService.getValues().containsAll(expected));
    }

    @Test
    @DisplayName("Return value list is unmodifiable")
    void unmodifiableValues() {
        assertThrows(UnsupportedOperationException.class, () -> metroMapService.getValues().add(new MetroStations()));
    }
}