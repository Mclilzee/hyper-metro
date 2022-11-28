package hyper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {


    @Test
    void printErrorIfFileNotFound() {

    }

    @Test
    void main() {
        String[] testFile = {"src/text/java/hyper/test.txt"};
        Main.main(testFile);
    }
}