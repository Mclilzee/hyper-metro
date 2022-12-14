package metro;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class MetroLine {

    private final Station head = new Station("depot");

    public MetroLine append(String stationName) {
        Station station = new Station(stationName);

        Station lastStation = getLastStation();
        lastStation.setNextStation(station);
        station.setPreviousStation(lastStation);
        return this;
    }

    public MetroLine append(Station station) {
        Station lastStation = getLastStation();
        lastStation.setNextStation(station);
        station.setPreviousStation(lastStation);
        return this;
    }

    public MetroLine addHead(String stationName) {
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
            if (station.getNextStation().isPresent()) {
                station = station.getNextStation().get();
            } else {
                return station;
            }
        }
    }

    public void removeStation(String stationName) {
        Optional<Station> foundStation = findStationByName(stationName);
        if (foundStation.isEmpty()) {
            return;
        }

        Station previous = foundStation.get().getPreviousStation().orElseThrow();
        Station next = foundStation.get().getNextStation().orElseThrow();

        previous.setNextStation(next);
        next.setPreviousStation(previous);
    }

    public void addLineConnection(String stationName, String connectedMetrosStationName, String connectedStationName) {
        Optional<Station> station = findStationByName(stationName);
        if (station.isEmpty()) {
            return;
        }

        station.get().addLineConnection(connectedMetrosStationName, connectedStationName);
    }

    public boolean containsStation(String stationName) {
        return findStationByName(stationName).isPresent();
    }

    private Optional<Station> findStationByName(String stationName) {
        return stream().filter(station -> station.getName().equals(stationName))
                .findFirst();
    }

    public Stream<Station> stream() {
        Optional<Station> firstStation = Optional.of(head);
        return Stream.iterate(firstStation, Optional::isPresent, station -> station.get().getNextStation())
                     .map(Optional::orElseThrow);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MetroLine that = (MetroLine) o;

        return stream().toList().equals(that.stream().toList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(stream().toList().toString());
    }
}
