package metro;

import java.util.Map;
import java.util.Scanner;

public class MetrosController {

    private Scanner scanner;
    private Map<String, MetroStations> stations;

    public MetrosController(Scanner scanner, Map<String, MetroStations> stations) {
        this.scanner = scanner;
        this.stations = stations;
    }

    public void start() {
    }
}
