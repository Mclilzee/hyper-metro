package metro;

import java.util.*;

public class Station {

    private final String name;
    private Station nextStation;
    private Station previousStation;
    private final List<LineConnection> lineConnections = new ArrayList<>();

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

    public void addLineConnection(String metroLineName, String stationName) {
        lineConnections.add(new LineConnection(stationName, metroLineName));
    }

    public List<LineConnection> getLineConnections() {
        return Collections.unmodifiableList(lineConnections);
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
        return Objects.equals(name, station.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
