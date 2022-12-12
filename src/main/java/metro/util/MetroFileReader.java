package metro.util;

import com.google.gson.Gson;
import metro.MetroStations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
        MetroStationsDTO[] metroStationsDTO = gson.fromJson(json, MetroStationsDTO[].class);

        if (metroStationsDTO == null) {
            System.out.println("Incorrect File");
            return new HashMap<>();
        } else {
            return Arrays.stream(metroStationsDTO)
                    .collect(Collectors.toMap(MetroStationsDTO::getName, MetroStationsDTO::getMetroStations));
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
