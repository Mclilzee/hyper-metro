package hyper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<String> metroList = readDataFromFile(args[0]);
        if (!metroList.isEmpty()) {
            metroList.add(0, "depot");
        }
    }

    private static List<String> readDataFromFile(String file) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            return bufferedReader.lines()
                                 .collect(Collectors.toCollection(LinkedList::new));
        } catch (IOException ex) {
            System.out.print("Error! Such a file doesn't exist!\n");
            return List.of();
        }
    }
}