package metro.fileReader;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

public class MetroJsonReader {

    private static final Gson gson = new Gson();
    private static final Type jsonFormat = new TypeToken<Map<String, Map<String, String>>>() {
    }.getType();

    static Optional<Map<String, Map<String, String>>> parseMetroJson(Path path) {
        try {
            String json = Files.readString(path);
            return Optional.ofNullable(gson.fromJson(json, jsonFormat));
        } catch (JsonSyntaxException | IOException e) {
            return Optional.empty();
        }
    }
}
