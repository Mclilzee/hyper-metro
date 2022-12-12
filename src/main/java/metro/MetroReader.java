package metro;

import java.io.File;
import java.util.Map;

public class MetroReader {
    public static Map<String, MetroStations> loadMetroFromFile(File file) {
        if (file.getName().endsWith(".json")) {
            return getJsonMetro(file);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static Map<String, MetroStations> getJsonMetro(File file) {
        return null;
    }
}
