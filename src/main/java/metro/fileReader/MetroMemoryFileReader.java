package metro.fileReader;

import metro.service.MetroMemoryService;
import metro.service.MetroService;

import java.nio.file.Path;

public class MetroMemoryFileReader implements MetroFileReader {

    @Override
    public MetroService loadMetroServiceFromFile(Path path) {
        if (path.toString()
                .endsWith(".json")) {
            return getJsonMetroService(path);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private MetroService getJsonMetroService(Path path) {
        var jsonMap = MetroJsonReader.parseMetroJson(path);
        MetroMemoryService service = new MetroMemoryService();
        if (jsonMap.isEmpty()) {
            System.out.println("Incorrect file");
            return service;
        }

        jsonMap.get().entrySet().stream()
                .map((entry) -> MetroLineFactory.createUnconnectedMetroLine(entry.getKey(), entry.getValue()))
                .forEach(service::addMetroLine);

        return service;
    }
}
