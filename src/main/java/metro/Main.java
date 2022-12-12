package metro;

import metro.util.MetroFileReader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Path path = Paths.get(args[0]);
        if (Files.exists(path)) {
            System.out.println("Error! Such a file doesn't exist!");
        } else {
            new MetrosController(new Scanner(System.in), MetroFileReader.loadMetroFromFile(path)).start();
        }
    }
}