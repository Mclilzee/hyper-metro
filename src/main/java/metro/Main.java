package metro;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        File file = new File(args[0]);
        if (!file.exists()) {
            System.out.println("Error! Such a file doesn't exist!");
        } else {
            new MetrosController(new Scanner(System.in), MetroReader.loadMetroFromFile(file)).start();
        }
    }
}