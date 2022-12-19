package metro.fileReader;

import metro.MetroLine;
import metro.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MetroLineFactoryTest {

    Map<String, StationDTO> stationsMap = new HashMap<>();

    @BeforeEach
    void setup() {
        stationsMap.put("2", new StationDTO("Bremen", List.of(), 1));
        stationsMap.put("1", new StationDTO("Berlin", List.of(), 1));
        stationsMap.put("3", new StationDTO("Frankfurt", List.of(), 1));
    }

    @Test
    void hasCorrectMetroStationsOrder() {
        MetroLine expected = new MetroLine("Germany");
        expected.append(new Station("Berlin", 1)).append(new Station("Bremen", 1)).append(new Station("Frankfurt", 1));

        assertEquals(expected, MetroLineFactory.createUnconnectedMetroLine("Germany", stationsMap));
    }
}