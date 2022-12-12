package metro.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import metro.MetroStations;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class MetroFileReader {

    private static final Gson gson = new Gson();
    private static final Type jsonFormat = new TypeToken<Map<String, Map<String, String>>>() {
    }.getType();

    public static Map<String, MetroStations> loadMetroFromFile(Path path) {
        if (path.toString()
                .endsWith(".json")) {
            return getJsonMetro(path);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static Map<String, MetroStations> getJsonMetro(Path path) {
        var map = getJsonFromFile(path);
        if (map.isEmpty()) {
            System.out.println("Incorrect File");
            return new HashMap<>();
        }

        return map.get().entrySet()
                  .stream()
                  .collect(Collectors.toMap(Map.Entry::getKey, entry -> MetroStationsFactory.createMetroStations(entry.getValue())));
    }

    private static Optional<Map<String, Map<String, String>>> getJsonFromFile(Path path) {
        try {
            String json = Files.readString(path);
            return Optional.ofNullable(gson.fromJson(json, jsonFormat));
        } catch (JsonSyntaxException | IOException e) {
            return Optional.empty();
        }
    }
}
