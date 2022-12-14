package metro.util;

import metro.MetroLine;
import metro.Station;
import metro.fileReader.StationDTO;

import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparingInt;

public class MetroLineFactory {
    public static MetroLine createMetroLine(Map<String, StationDTO> stationsMap) {
        List<Station> stations = stationsMap.entrySet().stream()
                                                .sorted(comparingInt(entrySet -> Integer.parseInt(entrySet.getKey())))
                                                .map(entry -> getStation(entry.getValue()))
                                                .toList();

        MetroLine metroLine = new MetroLine();
        for (Station station : stations) {
            metroLine.append(station);
        }

        return metroLine;
    }

    private static Station getStation(StationDTO stationDTO) {
       Station station = new Station(stationDTO.getName());
       stationDTO.getTransfer()
               .forEach(connection -> station.addLineConnection(connection.getLine(), connection.getStation()));
       return station;
    }

}
