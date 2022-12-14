package metro;

public record LineConnection(String stationName, String metroLineName) {

    public String getFullName() {
        return String.format("%s (%s)", stationName, metroLineName);
    }
}
