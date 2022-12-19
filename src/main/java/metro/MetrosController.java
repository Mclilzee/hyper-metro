package metro;

import metro.printing.LineConnectionsPrinter;
import metro.service.MetroService;
import metro.printing.MetroPrinter;

import java.util.Optional;
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
        Pattern pattern = Pattern.compile(
                "^((/add-head|/append) (\".*\"|[^\"\\s]+) (\".*\"|[^\"\\s]+) (\\d+)" +
                        "|(/remove) (\".*\"|[^\"\\s]+) (\".*\"|[^\"\\s]+)" +
                        "|(/output) (\".*\"|[^\"\\s]+)" +
                        "|(/connect|/route) (\".*\"|[^\"\\s]+) (\".*\"|[^\"\\s]+) (\".*\"|[^\"\\s]+) (\".*\"|[^\"\\s]+))$",
                Pattern.CASE_INSENSITIVE
                                         );

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
        } else if (matcherString.startsWith("/connect") || matcherString.startsWith("/route")) {
            parseConnectionRouteInput(matcher);
        } else if (matcherString.startsWith("/remove")) {
            removeStation(matcher);
        } else {
            addStation(matcher);
        }

    }

    private void addStation(Matcher matcher) {
        String metroLineName = removeQuotes(matcher.group(3));
        String stationName = removeQuotes(matcher.group(4));
        int stationTime = Integer.parseInt(matcher.group(5));

        switch (matcher.group(2).toLowerCase()) {
            case "/append" -> metroService.appendStation(metroLineName, stationName, stationTime);
            case "/add-head" -> metroService.addHead(metroLineName, stationName, stationTime);
        }
    }

    private void removeStation(Matcher matcher) {
        String metroLineName = removeQuotes(matcher.group(7));
        String stationName = removeQuotes(matcher.group(8));
        metroService.removeStation(metroLineName, stationName);
    }

    private void printStation(Matcher matcher) {
        String metroLineName = removeQuotes(matcher.group(10));
        Optional<MetroLine> metroLine = metroService.getMetroLine(metroLineName);
        if (metroLine.isEmpty()) {
            return;
        }

        MetroPrinter printer = new LineConnectionsPrinter();
        System.out.println(printer.getMetroLinePrintString(metroLine.get()));
    }


    private void parseConnectionRouteInput(Matcher matcher) {
        String metroLineName = removeQuotes(matcher.group(12));
        String stationName = removeQuotes(matcher.group(13));
        String toMetroLine = removeQuotes(matcher.group(14));
        String toStation = removeQuotes(matcher.group(15));

        switch (matcher.group(11).toLowerCase()) {
            case "/connect" -> connectStations(metroLineName, stationName, toMetroLine, toStation);
            case "/route" -> printRoute(metroLineName, stationName, toMetroLine, toStation);
        }

    }

    private void connectStations(String metroLineName, String stationName, String toMetroLine, String toStation) {
        metroService.connectMetroLine(metroLineName, stationName, toMetroLine, toStation);
    }

    private void printRoute(String metroLineName, String stationName, String toMetroLine, String toStation) {
        System.out.println(metroService.findShortestPath(metroLineName, stationName, toMetroLine, toStation));
    }


    private String removeQuotes(String input) {
        return input.replaceAll("\"", "");
    }
}
