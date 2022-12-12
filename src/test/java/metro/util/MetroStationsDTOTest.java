package metro.util;

import metro.MetroStations;
import metro.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MetroStationsDTOTest {

     MetroStationsDTO metroStationsDTO;

    @BeforeEach
    void setup() {
        Map<String, String> stationsMap = new HashMap<>();
        stationsMap.put("2", "Bremen");
        stationsMap.put("1", "Berlin");
        stationsMap.put("3", "Frankfurt");

        metroStationsDTO = new MetroStationsDTO("Metro", stationsMap);
    }

    @Test
    void hasCorrectName() {
        String expected = "Metro";

        assertEquals(expected, metroStationsDTO.getName());
    }
    @Test
    void hasCorrectMetroStations() {
        MetroStations expected = new MetroStations();
        Station first = new Station("Berlin");
        Station second = new Station("Bremen");
        Station third = new Station("Frankfurt");
        expected.add(first).add(second).add(third);

        assertEquals(expected, metroStationsDTO.getMetroStations());
    }
}