package hyper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> metroList = readDataFromFile(args[0]);
    }

    private static List<String> readDataFromFile(String file) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            return List.of();
        } catch (IOException ex) {
            System.out.print("Error! Such a file doesn't exist!\n");
            return List.of();
        }
    }
}