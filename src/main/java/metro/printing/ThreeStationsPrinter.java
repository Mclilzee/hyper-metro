package metro.printing;

import metro.MetroStations;
import metro.Station;

import java.util.List;
import java.util.stream.Collectors;

public class ThreeStationsPrinter implements MetroPrinter {

    @Override
    public String getMetroStationsPrintString(MetroStations metroStations) {
        if (metroStations == null) {
            return "";
        }

        List<Station> stations = metroStations.getStationsConnection();
        return stations.stream()
                .limit(stations.size() - 1)
                .map(this::generateStationsString)
                .collect(Collectors.joining("\n"));

    }

    private String generateStationsString(Station station) {
        Station secondStation = station.getNextStation().orElseThrow();
        Station thirdStation = secondStation.getNextStation().orElse(new Station("depot"));

        return String.format("%s - %s - %s", station.getName(), secondStation.getName(), thirdStation.getName());
    }
}
