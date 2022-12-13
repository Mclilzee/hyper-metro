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
                .flatMap(station -> getStationString(station).stream())
                .forEach(System.out::println);

        System.out.println("depot");
    }

    private List<String> getStationString(Station station) {
        if (station.getLineConnections().isEmpty()) {
            return List.of(station.getName());
        } else {
            return getLineConnectionsList(station);
        }
    }

    private List<String> getLineConnectionsList(Station station) {
        String stationName = station.getName();

        return station.getLineConnections().stream()
                .map(connection -> formatLineConnectionString(stationName, connection))
                .toList();

    }

    private String formatLineConnectionString(String stationName, LineConnection lineConnection) {
        return String.format("%s - %s (%s)", stationName, lineConnection.stationName(), lineConnection.metroStationsName());

    }
}
