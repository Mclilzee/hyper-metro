package metro.util;

import metro.MetroStations;
import metro.Station;

import java.util.Map;
import java.util.TreeMap;

public class MetroStationsDTO {

    private String name;
    private MetroStations metroStations;

    public MetroStationsDTO(String name, Map<String, String> stationsMap) {
        this.name = name;
        this.metroStations = generateMetroStations(stationsMap);
    }

    private MetroStations generateMetroStations(Map<String, String> stationsMap) {
        stationsMap = new TreeMap<>(stationsMap);
        MetroStations metroStations = new MetroStations();
        for (String metroName : stationsMap.values()) {
            metroStations.add(new Station(metroName));
        }

        return metroStations;
    }

    public String getName() {
        return name;
    }

    public MetroStations getMetroStations() {
        return metroStations;
    }
}
