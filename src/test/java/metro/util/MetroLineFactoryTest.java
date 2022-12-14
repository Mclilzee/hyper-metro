package metro.util;

import metro.MetroLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MetroLineFactoryTest {

    Map<String, String> stationsMap = new HashMap<>();

    @BeforeEach
    void setup() {
        stationsMap.put("2", "Bremen");
        stationsMap.put("1", "Berlin");
        stationsMap.put("3", "Frankfurt");
    }

    @Test
    void hasCorrectMetroStations() {
        MetroLine expected = new MetroLine();
        expected.append("Berlin").append("Bremen").append("Frankfurt");

        assertEquals(expected, MetroLineFactory.createMetroLine(stationsMap));
    }
}