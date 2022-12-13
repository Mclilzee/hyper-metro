package metro;

import metro.service.MetroService;
import metro.printing.MetroPrinter;
import metro.printing.ThreeStationsPrinter;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MetrosController {

    private final Scanner scanner;
    private final MetroService metroService;

    public MetrosController(Scanner scanner, MetroService metroService) {
        this.scanner = scanner;
        this.metroService = metroService;
    }

    public void start() {
        Pattern outputPattern = Pattern.compile("^(/output) (\".*\"|[^\"\\s]+)$", Pattern.CASE_INSENSITIVE);
        Pattern controlPattern = Pattern.compile("^(/remove|/add-head|/append) (\".*\"|[^\"\\s]+) (\".*\"|[^\"\\s]+)$",
                Pattern.CASE_INSENSITIVE);

        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("/exit")) {
                return;
            }

            Matcher matcher = outputPattern.matcher(input);
            if (matcher.find()) {
                String metroStationsName = matcher.group(2).replaceAll("\"", "");
                printStation(metroStationsName);
                continue;
            }

            parseControllerInput(controlPattern.matcher(input));
        }
    }

    private void parseControllerInput(Matcher matcher) {
        if (!matcher.find()) {
            System.out.println("Invalid command");
            return;
        }

        String metroStationsName = matcher.group(2).replaceAll("\"", "");
        String stationName = matcher.group(3).replaceAll("\"", "");
        switch (matcher.group(1).toLowerCase()) {
            case "/append" -> appendStation(metroStationsName, stationName);
            case "/remove" -> removeStation(metroStationsName, stationName);
            case "/add-head" -> addHeadStation(metroStationsName, stationName);
        }
    }

    private void appendStation(String metroStationName, String stationName) {
        metroService.appendStation(metroStationName, stationName);
    }

    private void addHeadStation(String metroStationsName, String stationName) {
        metroService.addHead(metroStationsName, stationName);
    }

    private void removeStation(String metroStationName, String stationName) {
        metroService.removeStation(metroStationName, stationName);
    }

    private void printStation(String metroStationsName) {
        MetroPrinter printer = new ThreeStationsPrinter();
        printer.printMetroStations(metroService.getMetroStations(metroStationsName));
    }

    private String removeQuotes(String input) {
        return input.replaceAll("\"", "");
    }
}
