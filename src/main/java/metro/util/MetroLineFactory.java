package metro.util;

import metro.MetroLine;
import metro.Station;
import metro.fileReader.StationDTO;

import java.util.Map;

import static java.util.Comparator.comparingInt;

public class MetroLineFactory {
    public static MetroLine createMetroLine(String metroLineName, Map<String, StationDTO> stationsMap) {
        MetroLine metroLine = new MetroLine(metroLineName);
        stationsMap.entrySet().stream()
                   .sorted(comparingInt(entrySet -> Integer.parseInt(entrySet.getKey())))
                   .map(entry -> getStation(entry.getValue()))
                   .forEach(metroLine::append);

        return metroLine;
    }

    private static Station getStation(StationDTO stationDTO) {
        return new Station(stationDTO.getName());
    }

}
