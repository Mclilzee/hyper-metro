package metro;

import metro.printing.LineConnectionsPrinter;
import metro.service.MetroService;
import metro.printing.MetroPrinter;

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
        Pattern pattern = Pattern.compile("^((/remove|/add-head|/append) (\".*\"|[^\"\\s]+) (\".*\"|[^\"\\s]+)" +
                        "|(/output) (\".*\"|[^\"\\s]+))$",
                Pattern.CASE_INSENSITIVE);

        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("/exit")) {
                return;
            }

            processInput(pattern.matcher(input));
        }
    }

    private void processInput(Matcher matcher) {
        if (matcher.find()) {
            parseInput(matcher);
        } else {
            System.out.println("Invalid command");
        }
    }

    private void parseInput(Matcher matcher) {
        String matcherString = matcher.group().toLowerCase();
        if (matcherString.startsWith("/output")) {
            printStation(matcher);
        } else if (matcherString.startsWith("/connect")) {
        } else {
            parseControllerInput(matcher);
        }

    }

    private void parseControllerInput(Matcher matcher) {
        String metroStationsName = removeQuotes(matcher.group(3));
        String stationName = removeQuotes(matcher.group(4));
        switch (matcher.group(2).toLowerCase()) {
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

    private void printStation(Matcher matcher) {
        String metroStationsName = removeQuotes(matcher.group(6));

        MetroPrinter printer = new LineConnectionsPrinter();
        MetroStations metroStations = metroService.getMetroStations(metroStationsName);
        System.out.println(printer.getMetroStationsPrintString(metroStations));
    }

    private String removeQuotes(String input) {
        return input.replaceAll("\"", "");
    }
}
