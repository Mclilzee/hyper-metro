package metro.fileReader;

import java.util.List;
import java.util.Objects;

public final class StationDTO {

    private final String name;
    private final List<ConnectionDTO> transfer;

    public StationDTO(String name, List<ConnectionDTO> transfer) {
        this.name = name;
        this.transfer = transfer ;
    }

    public String getName() {
        return name;
    }

    public List<ConnectionDTO> getTransfer() {
        return transfer == null ? List.of() : transfer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StationDTO that = (StationDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(transfer, that.transfer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, transfer);
    }
}
