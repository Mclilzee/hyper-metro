package metro.printing;

import metro.LineConnection;
import metro.MetroStations;
import metro.Station;

import java.util.List;
import java.util.stream.Collectors;

public class LineConnectionsPrinter implements MetroPrinter{

    @Override
    public String getMetroStationsPrintString(MetroStations metroStations) {
        if (metroStations == null || metroStations.getStationsConnection().size() <= 1) {
            return "";
        }

       String printString =  metroStations.getStationsConnection().stream()
                .flatMap(station -> getStationFullStrings(station).stream())
                .collect(Collectors.joining("\n"));

        return printString + "\ndepot";
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
