package metro.fileReader;

import metro.service.MetroMemoryService;
import metro.service.MetroService;
import metro.util.MetroLineFactory;

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

        jsonMap.get()
           .forEach((key, value) -> service.putMetroLine(key, MetroLineFactory.createMetroLine(key, value)));

        return service;
    }
}
