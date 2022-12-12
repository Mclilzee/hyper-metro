package metro.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MetroStationsMapDTOTest {

     MetroStationsMapDTO metroStationsMapDTO;

    @BeforeEach
    void setup() {
        Map<String, String> stationsMap = new HashMap<>();
        stationsMap.put("2", "Second station");
        stationsMap.put("1", "First station");
        stationsMap.put("3", "Third station");

        metroStationsMapDTO = new MetroStationsMapDTO("Metro", stationsMap);
    }

    @Test
    void metroHasCorrectName() {
        String expected = "Metro";

        assertEquals(expected, metroStationsMapDTO.getName());
    }
}