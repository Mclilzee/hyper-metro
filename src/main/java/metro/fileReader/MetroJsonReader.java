package metro.fileReader;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

public class MetroJsonReader {

    private static final Gson gson = new Gson();
    private static final Type jsonFormat = new TypeToken<Map<String, Map<String, StationDTO>>>() {
    }.getType();

    static Optional<Map<String, Map<String, StationDTO>>> parseMetroJson(Path path) {
        try {
            Reader reader = Files.newBufferedReader(path);
            return Optional.ofNullable(gson.fromJson(reader, jsonFormat));
        } catch (JsonSyntaxException | IOException e) {
            return Optional.empty();
        }
    }
}
