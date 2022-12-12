package metro;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MetroStations {

    private final Station head = new Station("depot");

    public MetroStations append(Station station) {
        Station lastStation = getLastStation();
        lastStation.setNextStation(station);
        station.setPreviousStation(lastStation);
        return this;
    }

    public MetroStations addHead(Station station) {
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

    public void removeStation(Station station) {
        Optional<Station> foundStation = findStation(station);
        if (foundStation.isEmpty()) {
            return;
        }

        Station previous = foundStation.get().getPreviousStation().orElseThrow();
        Station next = foundStation.get().getNextStation().orElseThrow();

        previous.setNextStation(next);
        next.setPreviousStation(previous);
    }

    private Optional<Station> findStation(Station searchStation) {
        Station station = head;
        while (true) {
            if (station == searchStation) {
                return Optional.of(station);
            }

            if (station.getNextStation().isEmpty()) {
                return Optional.empty();
            }

            station = station.getNextStation().get();
        }
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

        return connectedStations(that).equals(connectedStations(this));
    }

    @Override
    public int hashCode() {
        return Objects.hash(connectedStations(this));
    }

    private String connectedStations(MetroStations metroStations) {
        Optional<Station> firstStation = metroStations.getHead().getNextStation();
        return Stream.iterate(firstStation, Optional::isPresent, station -> station.get().getNextStation())
                     .map(station -> station.get().getName())
                     .collect(Collectors.joining());
    }
}
