package metro.printing;

import metro.LineConnection;
import metro.MetroStations;
import metro.Station;

import java.util.List;

public class LineConnectionsPrinter implements MetroPrinter{

    @Override
    public void printMetroStations(MetroStations metroStations) {
        if (metroStations == null || metroStations.getStationsConnection().size() <= 1) {
            return;
        }

        metroStations.getStationsConnection().stream()
                .flatMap(station -> getStationFullStrings(station).stream())
                .forEach(System.out::println);

        System.out.println("depot");
    }

    private List<String> getStationFullStrings(Station station) {
        String stationName = station.getName();
        List<LineConnection> connections = station.getLineConnections();

        if (connections.isEmpty()) {
            return List.of(stationName);
        }

        return station.getLineConnections().stream()
                      .map(connection -> stationName + " - " + connection.getFullName())
                      .toList();
    }
}
