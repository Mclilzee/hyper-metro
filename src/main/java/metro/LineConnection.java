package metro;

public record LineConnection(String metroLineName, String stationName) {

    public String getFullName() {
        return String.format("%s (%s)", stationName, metroLineName);
    }
}
