package metro.util;

import metro.MetroStations;
import metro.Station;

import java.util.ArrayList;
import java.util.List;

public class ThreeStationsPrinter implements MetroPrinter {

    @Override
    public void printMetroStations(MetroStations metroStations) {
        getThreeConnectedStations(metroStations).forEach(System.out::println);

    }

    private List<String> getThreeConnectedStations(MetroStations metroStations) {
        if (metroStations.getHead().getNextStation().isEmpty()) {
            return List.of();
        }

        List<String> stationConnections = new ArrayList<>();
        Station station = metroStations.getHead();
        while(station.getNextStation().isPresent()) {
            stationConnections.add(getConnectedStationsString(station));
            station = station.getNextStation().get();
        }

        return stationConnections;
    }

    private String getConnectedStationsString(Station station) {
        Station secondStation = station.getNextStation().orElseThrow();
        Station thirdStation = secondStation.getNextStation().orElse(new Station("depot"));

        return station.getName() + " - " + secondStation.getName() + " - " + thirdStation.getName();
    }
}
