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
    void addMetroStationDoesNotOverwrite() {
        Station berlin = new Station("Berlin");
        metroMapService.addMetroStations("Germany");
        MetroStations metroStations = metroMapService.getMetroStations("Germany");
        metroStations.append(berlin);

        metroMapService.addMetroStations("Germany");

        assertEquals(metroStations, metroMapService.getMetroStations("Germany"));
    }
}