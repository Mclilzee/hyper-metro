package metro.util;

import metro.MetroStations;
import metro.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MetroStationsFactoryTest {

    Map<String, String> stationsMap = new HashMap<>();

    @BeforeEach
    void setup() {
        stationsMap.put("2", "Bremen");
        stationsMap.put("1", "Berlin");
        stationsMap.put("3", "Frankfurt");
    }

    @Test
    void hasCorrectMetroStations() {
        MetroStations expected = new MetroStations();
        Station first = new Station("Berlin");
        Station second = new Station("Bremen");
        Station third = new Station("Frankfurt");
        expected.add(first).add(second).add(third);

        assertEquals(expected, MetroStationsFactory.createMetroStations(stationsMap));
    }
}