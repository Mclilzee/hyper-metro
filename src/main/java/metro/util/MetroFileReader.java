package metro.util;

import com.google.gson.Gson;
import metro.MetroStations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class MetroFileReader {

    private static Gson gson = new Gson();

    public static Map<String, MetroStations> loadMetroFromFile(Path path) {
        if (path.toString().endsWith(".json")) {
            return getJsonMetro(path);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static Map<String, MetroStations> getJsonMetro(Path path) {
        String json = getJsonFromFile(path);
        MetroStationsDTO[] jsonFormat = gson.fromJson(json, MetroStationsDTO[].class);


        return null;
    }

    private static String getJsonFromFile(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            return "{}";
        }
    }
}
