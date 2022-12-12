package metro.util;

import metro.MetroStations;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MetroFileReaderTest {

    Path metroPath = Paths.get("src/test/java/metro/util/test.json");
    Path emptyFilePath = Paths.get("src/test/java/metro/util/empty-file.json");

    @Test
    void getEmptyMapIfFileIsEmpty() {
      Map<String, MetroStations> metroMap =  MetroFileReader.loadMetroFromFile(emptyFilePath);
      assertTrue(metroMap.isEmpty());
    }
}