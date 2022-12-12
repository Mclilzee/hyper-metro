package metro;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        MetroStations metroStations = readDataFromFile(args[0]);
        metroStations.getThreeConnectedStations()
                     .forEach(System.out::println);
    }

    private static MetroStations readDataFromFile(String filePath) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
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
}