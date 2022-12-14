package metro.fileReader;

import java.util.List;

public final class StationDTO {

    private final String name;
    private final List<ConnectionDTO> transfer;

    public StationDTO(String name, List<ConnectionDTO> transfer) {
        this.name = name;
        this.transfer = transfer;
    }

    public String getName() {
        return name;
    }

    public List<ConnectionDTO> getTransfer() {
        return transfer;
    }
}
