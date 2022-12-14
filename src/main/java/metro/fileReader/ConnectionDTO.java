package metro.fileReader;

import java.util.Objects;

public final class ConnectionDTO {

    private final String line;
    private final String station;

    public ConnectionDTO(String line, String station) {
        this.line = line;
        this.station = station;
    }

    public String getLine() {
        return line;
    }

    public String getStation() {
        return station;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConnectionDTO that = (ConnectionDTO) o;
        return Objects.equals(line, that.line) && Objects.equals(station, that.station);
    }

    @Override
    public int hashCode() {
        return Objects.hash(line, station);
    }
}
