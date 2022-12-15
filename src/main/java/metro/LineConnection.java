package metro;

public record LineConnection(MetroLine metroLine, Station station) {

    public String getFullName() {
        return String.format("%s (%s)", station.getName(), metroLine.getName());
    }
}
