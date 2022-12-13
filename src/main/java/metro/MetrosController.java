package metro;

import metro.service.MetroService;
import metro.util.MetroPrinter;
import metro.util.ThreeStationsPrinter;

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
        Pattern outputPattern = Pattern.compile("^(\\\\output) (\".*\"|[^\"\\s]+)$", Pattern.CASE_INSENSITIVE);
        Pattern controlPattern = Pattern.compile("^(\\\\remove|\\\\add-head|\\\\append) (\".*\"|[^\"\\s]+) (\".*\"|[^\"\\s]+)$",
                Pattern.CASE_INSENSITIVE);

        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("\\exit")) {
                return;
            }

            Matcher matcher = outputPattern.matcher(input);
            if (matcher.find()) {
                printStation(matcher.group(2));
                continue;
            }

            parseControllerInput(controlPattern.matcher(input));
        }
    }

    private void parseControllerInput(Matcher matcher) {
        if (!matcher.find()) {
            System.out.println("Invalid command");
        }

        switch (matcher.group(1).toLowerCase()) {
            case "\\append" -> appendStation(matcher.group(2), matcher.group(3));
        }
    }

    private void appendStation(String metroStationName, String stationName) {
        String parseMetroStationsName = metroStationName.replaceAll("\"", "");
        String parseStationsName = stationName.replaceAll("\"", "");
        metroService.appendStation(parseMetroStationsName, parseStationsName);
    }

    private void printStation(String input) {
        String parseInput = input.replaceAll("\"", "");
        MetroPrinter printer = new ThreeStationsPrinter();
        printer.printMetroStations(metroService.getMetroStations(parseInput));
    }
}
