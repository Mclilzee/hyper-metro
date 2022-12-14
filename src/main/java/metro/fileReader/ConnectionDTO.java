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
}
