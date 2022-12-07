package metro;

import java.util.ArrayList;
import java.util.List;

public class MetroStations {

    private final Station head = new Station("depot");

    public MetroStations add(Station station) {
        station.setNextStation(head);
        Station lastStation = getLastStation();
        lastStation.setNextStation(station);

        return this;
    }

    public Station getHead() {
        return head;
    }

    private Station getLastStation() {
        Station station = head;
        while (true) {
            if (station.getNextStation().isEmpty() || station.getNextStation().get() == head) {
                return station;
            }

            station = station.getNextStation().get();
        }
    }

    public List<Station[]> getThreeConnectedStations() {
        if (head.getNextStation().isEmpty()) {
            return List.of();
        }

        List<Station[]> stationsList = new ArrayList<>();
        Station firstStation = head;
        while (true) {
            Station secondStation = firstStation.getNextStation().get();
            Station thirdStation = secondStation.getNextStation().get();
            stationsList.add(new Station[]{firstStation, secondStation, thirdStation});

            if (thirdStation == head) {
                return stationsList;
            }

            firstStation = secondStation;
        }
    }
}
