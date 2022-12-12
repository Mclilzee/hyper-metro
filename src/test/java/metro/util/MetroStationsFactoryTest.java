package metro.util;

import metro.MetroStations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        expected.append("Berlin").append("Bremen").append("Frankfurt");

        assertEquals(expected, MetroStationsFactory.createMetroStations(stationsMap));
    }
}