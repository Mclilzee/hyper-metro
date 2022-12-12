package metro;

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

}