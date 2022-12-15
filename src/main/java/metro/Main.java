package metro;

import metro.fileReader.MetroFileReader;
import metro.fileReader.MetroMemoryFileReader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Path path = Paths.get(args[0]);
        if (Files.exists(path)) {
            MetroFileReader reader = new MetroMemoryFileReader();
            new MetrosController(new Scanner(System.in), reader.loadMetroServiceFromFile(path)).start();
        } else {
            System.out.println("Error! Such a file doesn't exist!");
        }
    }
}