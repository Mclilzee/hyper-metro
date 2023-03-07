package metro;

import metro.service.MetroService;

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
                        "|(/connect|/route|/fastest-route) (\".*\"|[^\"\\s]+) (\".*\"|[^\"\\s]+) (\".*\"|[^\"\\s]+) (\".*\"|[^\"\\s]+))$",
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
        } else if (matcherString.startsWith("/remove")) {
            removeStation(matcher);
        } else if (matcherString.startsWith("/append") || matcherString.startsWith("/add-head")){
            addStation(matcher);
        } else {
            parseRouteInput(matcher);
        }
    }

    private void addStation(Matcher matcher) {
        String metroLineName = removeQuotes(matcher.group(3));
        String stationName = removeQuotes(matcher.group(4));
        int stationTime = Integer.parseInt(matcher.group(5));

        switch (matcher.group(2).toLowerCase()) {
            case "/append" -> appendStation(metroLineName, stationName, stationTime);
            case "/add-head" -> addHead(metroLineName, stationName, stationTime);
        }
    }

    private void appendStation(String metroLineName, String stationName, int stationTime) {
        try {
            metroService.appendStation(metroLineName, stationName, stationTime);
            System.out.printf("Successfully appended station \"%s\" to Line \"%s\".%n", stationName, metroLineName);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void addHead(String metroLineName, String stationName, int stationTime) {
        try {
            metroService.addHead(metroLineName, stationName, stationTime);
            System.out.printf("Successfully added head station \"%s\" to Line \"%s\".%n", stationName, metroLineName);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void removeStation(Matcher matcher) {
        String metroLineName = removeQuotes(matcher.group(7));
        String stationName = removeQuotes(matcher.group(8));
        try {
            metroService.removeStation(metroLineName, stationName);
            System.out.printf("Successfully removed station \"%s\" to Line \"%s\".%n", stationName, metroLineName);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void printStation(Matcher matcher) {
        String metroLineName = removeQuotes(matcher.group(10));
        try {
            String information = metroService.getMetroLineInformation(metroLineName);
            System.out.println();
            System.out.println(information);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }


    private void parseRouteInput(Matcher matcher) {
        String metroLineName = removeQuotes(matcher.group(12));
        String stationName = removeQuotes(matcher.group(13));
        String toMetroLine = removeQuotes(matcher.group(14));
        String toStation = removeQuotes(matcher.group(15));

        switch (matcher.group(11).toLowerCase()) {
            case "/connect" -> connectStations(metroLineName, stationName, toMetroLine, toStation);
            case "/route" -> printShortestRoute(metroLineName, stationName, toMetroLine, toStation);
            case "/fastest-route" -> printFastestRoute(metroLineName, stationName, toMetroLine, toStation);
        }

    }

    private void connectStations(String metroLineName, String stationName, String toMetroLine, String toStation) {
        try {
            metroService.connectMetroLine(metroLineName, stationName, toMetroLine, toStation);
            System.out.printf("Successfully connected Lines %s and %s on stations %s and %s.%n", metroLineName, toMetroLine, stationName, toStation);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void printShortestRoute(String metroLineName, String stationName, String toMetroLine, String toStation) {
        try {
            String shortestRoute = metroService.findShortestPath(metroLineName, stationName, toMetroLine, toStation);
            System.out.println();
            System.out.println(shortestRoute);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private void printFastestRoute(String metroLineName, String stationName, String toMetroLine, String toStation) {
        try {
            String fastestRoute = metroService.findFastestPath(metroLineName, stationName, toMetroLine, toStation);
            System.out.println();
            System.out.println(fastestRoute);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private String removeQuotes(String input) {
        return input.replaceAll("\"", "");
    }
}
