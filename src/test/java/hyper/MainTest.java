package hyper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    static ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeAll
    static void init() {
        System.setOut(new PrintStream(outputStream));
    }

    @BeforeEach
    void setup() {
        outputStream.reset();
    }

    @Test
    void printErrorIfFileNotFound() {
        String[] testFile = {"nonexistance.txt"};
        Main.main(testFile);

        String expected = "Error! Such a file doesn't exist!" + System.lineSeparator();
        assertEquals(expected, outputStream.toString());
    }

    @Test
    @DisplayName("Prints nothing when file is empty text")
    void printNothing() {
        String[] testFile = {"src/test/java/hyper/empty-file.txt"};
        Main.main(testFile);

        String expected = "";
        assertEquals(expected, outputStream.toString());
    }

    @Test
    @DisplayName("Print the correct names in order")
    void printNamesOfStations() {
        String[] testFile = {"src/test/java/hyper/test.txt"};
        Main.main(testFile);

        String expected = String.format("depot - Owings Mills - Old Court%n" +
                          "Owings Mills - Old Court - Milford Mill%n" +
                          "Old Court - Milford Mill - Reiserstown Plaza%n" +
                          "Milford Mill - Reiserstown Plaza - depot%n");

        assertEquals(expected, outputStream.toString());
    }
}