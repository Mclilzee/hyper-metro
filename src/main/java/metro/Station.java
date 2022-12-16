package metro;

import java.util.*;

public class Station {

    private final String name;
    private Station nextStation;
    private Station previousStation;
    private final Set<LineConnection> lineConnections = new HashSet<>();

    public Station(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Optional<Station> getNextStation() {
        return Optional.ofNullable(nextStation);
    }

    public void setNextStation(Station nextStation) {
        this.nextStation = nextStation;
    }

    public Optional<Station> getPreviousStation() {
        return Optional.ofNullable(previousStation);
    }

    public void setPreviousStation(Station previousStation) {
        this.previousStation = previousStation;
    }

    public void addLineConnection(MetroLine metroLine, Station station) {
        lineConnections.add(new LineConnection(metroLine, station));
    }

    public Set<LineConnection> getLineConnections() {
        return Collections.unmodifiableSet(lineConnections);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Station station = (Station) o;
        return Objects.equals(name, station.name) &&
                otherStationsEqual(nextStation, station.nextStation) &&
                otherStationsEqual(previousStation, station.previousStation) &&
                Objects.equals(lineConnections.toString(), station.lineConnections.toString());
    }

    private boolean otherStationsEqual(Station station, Station other) {
        String stationName = station != null ? station.name : "none";
        String otherName = other != null ? other.name : "none";
        return Objects.equals(stationName, otherName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
