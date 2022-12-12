package metro;

import java.io.File;
import java.util.Map;

public interface MetroReader {
    public Map<String, MetroStations> readMetroStations(File file);
}
