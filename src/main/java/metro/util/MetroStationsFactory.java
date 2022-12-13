package metro.util;

import metro.MetroStations;

import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparingInt;

public class MetroStationsFactory {
    public static MetroStations createMetroStations(Map<String, String> stationsMap) {
        List<String> stationNames = stationsMap.entrySet().stream()
                                               .sorted(comparingInt(entrySet -> Integer.parseInt(entrySet.getKey())))
                                               .map(Map.Entry::getValue)
                                               .toList();

        MetroStations metroStations = new MetroStations();
        for (String stationName : stationNames) {
            metroStations.append(stationName);
        }

        return metroStations;
    }
}
