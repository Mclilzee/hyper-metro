package metro;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MetroStations {

    private final Station head = new Station("depot");

    public MetroStations append(String stationName) {
        Station station = new Station(stationName);

        Station lastStation = getLastStation();
        lastStation.setNextStation(station);
        station.setPreviousStation(lastStation);
        return this;
    }

    public MetroStations addHead(String stationName) {
        Station station = new Station(stationName);

        Optional<Station> firstStation = head.getNextStation();
        station.setNextStation(firstStation.orElse(null));
        station.setPreviousStation(head);
        head.setNextStation(station);

        firstStation.ifPresent(foundStation -> foundStation.setPreviousStation(station));

        return this;
    }

    public Station getHead() {
        return head;
    }

    private Station getLastStation() {
        Station station = head;
        while (true) {
            if (station.getNextStation().isEmpty()) {
                return station;
            }

            station = station.getNextStation().get();
        }
    }

    public void removeStation(String stationName) {
        Optional<Station> foundStation = findStation(stationName);
        if (foundStation.isEmpty()) {
            return;
        }

        Station previous = foundStation.get().getPreviousStation().orElseThrow();
        Station next = foundStation.get().getNextStation().orElseThrow();

        previous.setNextStation(next);
        next.setPreviousStation(previous);
    }

    public void addLineConnection(String stationName, String connectedMetrosStationName, String connectedStationName) {
        Optional<Station> station = findStation(stationName);
        if (station.isEmpty()) {
            return;
        }

        station.get().addLineConnection(connectedMetrosStationName, connectedStationName);
    }

    private Optional<Station> findStation(String stationName) {
        Station station = head;
        while (true) {
            if (Objects.equals(station.getName(), stationName)) {
                return Optional.of(station);
            }

            if (station.getNextStation().isEmpty()) {
                return Optional.empty();
            }

            station = station.getNextStation().get();
        }
    }

    public List<Station> getStationsConnection() {
        Optional<Station> firstStation = Optional.of(head);
        return Stream.iterate(firstStation, Optional::isPresent, station -> station.get().getNextStation())
                     .map(Optional::orElseThrow)
                     .toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MetroStations that = (MetroStations) o;

        return getStationsConnection().equals(that.getStationsConnection());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStationsConnection().toString());
    }
}
