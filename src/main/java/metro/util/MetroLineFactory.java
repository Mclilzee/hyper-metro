package metro.util;

import metro.MetroLine;
import metro.Station;
import metro.fileReader.StationDTO;

import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparingInt;

public class MetroLineFactory {
    public static MetroLine createMetroLine(Map<String, StationDTO> stationsMap) {
//        List<Station> stationNames = stationsMap.entrySet().stream()
//                                                .sorted(comparingInt(entrySet -> Integer.parseInt(entrySet.getKey())))
//                                                .map(Map.Entry::getValue)
//                                                .toList();

        MetroLine metroLine = new MetroLine();
//        for (String stationName : stationNames) {
//            metroLine.append(stationName);
//        }

        return metroLine;
    }
}
