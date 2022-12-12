package metro.util;

import metro.MetroStations;
import metro.Station;

import java.util.Map;
import java.util.TreeMap;

public class MetroStationsFactory {
    public static MetroStations createMetroStations(Map<String, String> stationsMap) {
        stationsMap = new TreeMap<>(stationsMap);
        MetroStations metroStations = new MetroStations();
        for (String metroName : stationsMap.values()) {
            metroStations.append(new Station(metroName));
        }

        return metroStations;
    }
}
