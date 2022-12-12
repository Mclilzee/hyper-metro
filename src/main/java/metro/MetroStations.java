package metro;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MetroStations {

    private final Station head = new Station("depot");

    public MetroStations add(Station station) {
        Station lastStation = getLastStation();
        lastStation.setNextStation(station);
        station.setPreviousStation(lastStation);
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

    public List<String> getThreeConnectedStations() {
        if (head.getNextStation().isEmpty()) {
            return List.of();
        }

        List<String> stationConnections = new ArrayList<>();
        Station station = head;
        while(station.getNextStation().isPresent()) {
            stationConnections.add(getConnectedStationsString(station));
            station = station.getNextStation().get();
        }

        return stationConnections;
    }

    private String getConnectedStationsString(Station station) {
        Station secondStation = station.getNextStation().orElseThrow();
        Station thirdStation = secondStation.getNextStation().orElse(head);

        return station.getName() + " - " + secondStation.getName() + " - " + thirdStation.getName();
    }
}
