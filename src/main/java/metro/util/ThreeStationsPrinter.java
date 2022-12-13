package metro.util;

import metro.MetroStations;
import metro.Station;

import java.util.List;

public class ThreeStationsPrinter implements MetroPrinter {

    @Override
    public void printMetroStations(MetroStations metroStations) {
        getConnectedStationsString(metroStations).forEach(System.out::println);

    }

    private List<String> getConnectedStationsString(MetroStations metroStations) {
        if (metroStations == null) {
            return List.of();
        }

        List<Station> stations = metroStations.getStationsConnection();
        return stations.stream()
                .limit(stations.size() - 1)
                .map(this::generateStationsString)
                .toList();
    }

    private String generateStationsString(Station station) {
        Station secondStation = station.getNextStation().orElseThrow();
        Station thirdStation = secondStation.getNextStation().orElse(new Station("depot"));

        return String.format("%s - %s - %s", station.getName(), secondStation.getName(), thirdStation.getName());
    }
}
