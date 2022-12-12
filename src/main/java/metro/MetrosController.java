package metro;

import metro.service.MetroService;

import java.util.Map;
import java.util.Scanner;

public class MetrosController {

    private Scanner scanner;
    private MetroService metroService;

    public MetrosController(Scanner scanner, MetroService metroService) {
        this.scanner = scanner;
        this.metroService = metroService;
    }

    public void start() {
    }
}
