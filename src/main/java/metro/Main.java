package metro;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        MetroStations metroStations = readDataFromFile(args[0]);
        metroStations.getThreeConnectedStations()
                     .forEach(Main::printStations);
    }

    private static MetroStations readDataFromFile(String file) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            MetroStations metroStations = new MetroStations();
            bufferedReader.lines()
                          .map(Station::new)
                          .forEach(metroStations::add);

            return metroStations;
        } catch (IOException ex) {
            System.out.println("Error! Such a file doesn't exist!");
            return new MetroStations();
        }
    }

    private static void printStations(Station[] stations) {
        String stationsInformation = Arrays.stream(stations)
                                           .map(Station::getName)
                                           .collect(Collectors.joining(" - "));
        System.out.println(stationsInformation);
    }
}