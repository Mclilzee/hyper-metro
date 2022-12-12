package metro.util;

import metro.MetroStations;

import java.util.Map;
import java.util.TreeMap;

public class MetroStationsFactory {
    public static MetroStations createMetroStations(Map<String, String> stationsMap) {
        stationsMap = new TreeMap<>(stationsMap);
        MetroStations metroStations = new MetroStations();
        for (String stationName : stationsMap.values()) {
            metroStations.append(stationName);
        }

        return metroStations;
    }
}
