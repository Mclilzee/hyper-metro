package metro.printing;

import metro.LineConnection;
import metro.MetroLine;
import metro.Station;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LineConnectionsPrinter implements MetroPrinter{

    @Override
    public String getMetroLinePrintString(MetroLine metroLine) {
        if (metroLine == null || metroLine.stream().toList().size() <= 1) {
            return "";
        }

       String printString =  metroLine.stream()
                                      .flatMap(station -> getStationFullStrings(station).stream())
                                      .collect(Collectors.joining("\n"));

        return printString + "\ndepot";
    }

    private List<String> getStationFullStrings(Station station) {
        String stationName = station.getName();
        Set<LineConnection> connections = station.getLineConnections();

        if (connections.isEmpty()) {
            return List.of(stationName);
        }

        return station.getLineConnections().stream()
                      .map(connection -> stationName + " - " + connection.getFullName())
                      .toList();
    }
}
