package metro;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class MetroLine {

    private final String name;
    private final Station head = new Station("depot");

    public MetroLine(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public MetroLine append(Station station) {
        Station lastStation = getLastStation();
        lastStation.setNextStation(station);
        station.setPreviousStation(lastStation);
        return this;
    }

    public MetroLine addHead(Station station) {
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

    public void addLineConnection(Station station, MetroLine connectedMetrosStation, Station connectedStation) {
        Optional<Station> foundStation = findStationByName(station.getName());
        if (foundStation.isEmpty()) {
            return;
        }

        foundStation.get().addLineConnection(connectedMetrosStation, connectedStation);
    }

    public Optional<Station> findStationByName(String stationName) {
        return stream().filter(station -> station.getName().equals(stationName))
                .findFirst();
    }

    public Stream<Station> stream() {
        Optional<Station> firstStation = Optional.of(head);
        return Stream.iterate(firstStation, Optional::isPresent, station -> station.flatMap(Station::getNextStation))
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

        return stream().toList().equals(that.stream().toList()) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name + stream().toList());
    }
}
