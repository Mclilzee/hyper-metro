package metro.printing;

import metro.MetroStations;
import metro.Station;

import java.util.List;

public class ThreeStationsPrinter implements MetroPrinter {

    @Override
    public void printMetroStations(MetroStations metroStations) {
        if (metroStations == null) {
            return ;
        }

        List<Station> stations = metroStations.getStationsConnection();
        stations.stream()
                .limit(stations.size() - 1)
                .map(this::generateStationsString)
                .forEach(System.out::println);

    }

    private String generateStationsString(Station station) {
        Station secondStation = station.getNextStation().orElseThrow();
        Station thirdStation = secondStation.getNextStation().orElse(new Station("depot"));

        return String.format("%s - %s - %s", station.getName(), secondStation.getName(), thirdStation.getName());
    }
}
