package metro.printing;

import metro.MetroLine;
import metro.Station;

import java.util.List;
import java.util.stream.Collectors;

public class ThreeStationsPrinter implements MetroPrinter {

    @Override
    public String getMetroLinePrintString(MetroLine metroLine) {
        if (metroLine == null) {
            return "";
        }

        List<Station> stations = metroLine.stream().toList();
        return stations.stream()
                .limit(stations.size() - 1)
                .map(this::generateStationsString)
                .collect(Collectors.joining("\n"));

    }

    private String generateStationsString(Station station) {
        Station secondStation = station.getNextStation().orElseThrow();
        Station thirdStation = secondStation.getNextStation().orElse(new Station("depot", 0));

        return String.format("%s - %s - %s", station.getName(), secondStation.getName(), thirdStation.getName());
    }
}
