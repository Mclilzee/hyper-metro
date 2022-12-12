package metro.util;

import metro.MetroStations;
import metro.Station;
import org.junit.jupiter.api.Test;

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
}