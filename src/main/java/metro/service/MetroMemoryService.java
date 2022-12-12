package metro.service;

import metro.MetroStations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MetroMemoryService implements MetroService {

    private Map<String, MetroStations> map = new HashMap<>();


    @Override
    public void addMetroStations(String metroStationsName) {
        map.putIfAbsent(metroStationsName, new MetroStations());
    }

    @Override
    public void putMetroStation(String metroStationName, MetroStations metroStations) {
        if (metroStations == null) {
            return;
        }

        map.putIfAbsent(metroStationName, metroStations);
    }

    @Override
    public MetroStations getMetroStations(String metroStationsName) {
        return map.get(metroStationsName);
    }

    @Override
    public void appendStation(String metroStationsName, String stationName) {
        MetroStations metroStations = map.get(metroStationsName);
        if (metroStations == null) {
            return;
        }

        metroStations.append(stationName);
    }

    @Override
    public void addHead(String metroStationsName, String stationName) {
        MetroStations metroStations = map.get(metroStationsName);
        if (metroStations == null) {
            return;
        }

        metroStations.addHead(stationName);
    }

    @Override
    public void removeStation(String metroStationsName, String stationName) {
        MetroStations metroStations = map.get(metroStationsName);
        if (metroStations == null) {
            return;
        }

        metroStations.removeStation(stationName);
    }


    @Override
    public Set<String> getKeys() {
        return map.keySet();
    }

    @Override
    public List<MetroStations> getValues() {
        return map.values().stream().toList();
    }
}
