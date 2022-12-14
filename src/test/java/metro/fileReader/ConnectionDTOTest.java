package metro.fileReader;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConnectionDTOTest {

    Gson gson = new Gson();
    String jsonString = """
                        {
                        "line": "Metro_Railway",
                        "station": "Baker street"
                        }""";

    @Test
    void parseJson() {
        ConnectionDTO connectionDTO = gson.fromJson(jsonString, ConnectionDTO.class);

        assertEquals(connectionDTO.getLine(), "Metro_Railway");
        assertEquals(connectionDTO.getStation(), "Baker street");
    }

}