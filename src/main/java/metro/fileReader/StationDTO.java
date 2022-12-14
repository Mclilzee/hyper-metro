package metro.fileReader;

import java.util.List;

public record StationDTO(String name, List<ConnectionDTO> transfer){

}
