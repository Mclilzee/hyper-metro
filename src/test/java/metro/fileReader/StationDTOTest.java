package metro.fileReader;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StationDTOTest {

    Gson gson = new Gson();
    String jsonString = """
                        {
                        "name" :"Baker street",
                        "transfer":[{
                        "line": "Hammersmith-and-City",
                        "station": "Baker street"
                        }]
                        }
                        """;

    @Test
    void getName() {
        StationDTO stationDTO = gson.fromJson(jsonString, StationDTO.class);

        String expected = "Baker street";

        assertEquals(expected, stationDTO.getName());
    }

    @Test
    void getTransfer() {
        StationDTO stationDTO = gson.fromJson(jsonString, StationDTO.class);

        List<ConnectionDTO> expected = List.of(new ConnectionDTO("Hammersmith-and-City", "Baker street"));

        assertEquals(expected, stationDTO.getTransfer());
    }

    @Test
    void getTransferNull() {
        jsonString = """
                        {
                        "name" :"Baker street",
                        "transfer":null
                        }
                        """;

        StationDTO stationDTO = gson.fromJson(jsonString, StationDTO.class);

        List<ConnectionDTO> expected = List.of();

        assertEquals(expected, stationDTO.getTransfer());
    }

    @Test
    void setMultipleTransfers() {
        jsonString = """
                     {
                      "name" :"Baker street",
                      "transfer":[
                        {
                        "line": "Hammersmith-and-City",
                        "station": "Baker street"
                        },
                        {
                        "line": "Germany",
                        "station": "Berlin"
                        }
                      ]
                     }
                     """;

        StationDTO stationDTO = gson.fromJson(jsonString, StationDTO.class);

        List<ConnectionDTO> expected = List.of(
                new ConnectionDTO("Hammersmith-and-City", "Baker street"),
                new ConnectionDTO("Germany", "Berlin")
                                              );

        assertEquals(expected, stationDTO.getTransfer());
    }

    @Test
    void equals() {
        StationDTO stationDTO = new StationDTO("Berlin", List.of());
        StationDTO otherStationDTO = new StationDTO("Berlin", List.of());

        assertEquals(stationDTO, otherStationDTO);
    }

    @Test
    void equalsWithTransfers() {
        List<ConnectionDTO> firstConnections = List.of(
                new ConnectionDTO("Germany", "Berlin"),
                new ConnectionDTO("Germany", "Bremen")
                                                      );
        List<ConnectionDTO> secondConnections = List.of(
                new ConnectionDTO("Germany", "Berlin"),
                new ConnectionDTO("Germany", "Bremen")
                                                      );

        StationDTO stationDTO = new StationDTO("Berlin", firstConnections);
        StationDTO otherStationDTO = new StationDTO("Berlin", secondConnections);

        assertEquals(stationDTO, otherStationDTO);
    }
}