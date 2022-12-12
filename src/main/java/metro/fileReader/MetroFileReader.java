package metro.fileReader;

import metro.service.MetroService;

import java.nio.file.Path;

public interface MetroFileReader {
    MetroService loadMetroFromFile(Path path);
}
