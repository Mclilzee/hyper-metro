package metro.util;

import metro.MetroLine;
import metro.fileReader.StationDTO;
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
        stationsMap.put("2", new StationDTO("Bremen", List.of()));
        stationsMap.put("1", new StationDTO("Berlin", List.of()));
        stationsMap.put("3", new StationDTO("Frankfurt", List.of()));
    }

    @Test
    void hasCorrectMetroStationsOrder() {
        MetroLine expected = new MetroLine("Germany");
        expected.append("Berlin").append("Bremen").append("Frankfurt");

        assertEquals(expected, MetroLineFactory.createMetroLine("Germany", stationsMap));
    }
}