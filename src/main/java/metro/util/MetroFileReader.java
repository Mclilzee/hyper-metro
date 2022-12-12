package metro.util;

import com.google.gson.Gson;
import metro.MetroStations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
        Map<String, Map<String, String>> metroStations = gson.fromJson(json, Map.class);

        if (metroStations == null) {
            System.out.println("Incorrect File");
            return new HashMap<>();
        } else {
            return metroStations.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, entry -> MetroStationsFactory.createMetroStations(entry.getValue())));
        }
    }

    private static String getJsonFromFile(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            return "{}";
        }
    }
}
