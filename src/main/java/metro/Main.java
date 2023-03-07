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
            System.out.printf("Loading metro: %s%n", path);
            MetroFileReader reader = new MetroMemoryFileReader();
            MetrosController controller = new MetrosController(new Scanner(System.in), reader.loadMetroServiceFromFile(path));
            System.out.println("Metro Ready, Check README.md for commands");
            controller.start();
        } else {
            System.out.println("Error! Such a file doesn't exist!");
        }
    }
}