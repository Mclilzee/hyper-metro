package metro;

public record LineConnection(String stationName, String metroStationsName) {

    public String getFullName() {
        return String.format("%s (%s)", stationName, metroStationsName);
    }
}
