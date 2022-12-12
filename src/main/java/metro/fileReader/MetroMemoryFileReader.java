package metro.fileReader;

import metro.service.MetroMemoryService;
import metro.service.MetroService;
import metro.util.MetroJsonReader;
import metro.util.MetroStationsFactory;

import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MetroMemoryFileReader implements MetroFileReader {

    @Override
    public MetroService loadMetroFromFile(Path path) {
        if (path.toString()
                .endsWith(".json")) {
            return getJsonMetroService(path);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private MetroService getJsonMetroService(Path path) {
        var map = MetroJsonReader.parseMetroJson(path);
        MetroMemoryService service = new MetroMemoryService();
        if (map.isEmpty()) {
            System.out.println("Incorrect file");
            return service;
        }

        for (var entrySet : map.get().entrySet()) {
            service.putMetroStation(entrySet.getKey(), MetroStationsFactory.createMetroStations(entrySet.getValue()));
        }

        return service;
    }
}
