package metro.util;

import metro.MetroService;
import metro.MetroStations;
import metro.Station;

import java.util.HashMap;
import java.util.Map;

public class MetroMapService implements MetroService {

    private Map<String, MetroStations> map = new HashMap<>();


    @Override
    public void addMetroStations(String metroStationsName) {
        map.putIfAbsent(metroStationsName, new MetroStations());
    }

    @Override
    public MetroStations getMetroStations(String metroStation) {
        return map.get(metroStation);
    }

    @Override
    public void appendStation(String metroStationName, String stationName) {
        MetroStations metroStations = map.get(metroStationName);
        if (metroStations == null) {
            return;
        }

        metroStations.append(stationName);
    }

    @Override
    public void addHead(String metroStationName, String stationName) {
        MetroStations metroStations = map.get(metroStationName);
        if (metroStations == null) {
            return;
        }

        metroStations.addHead(stationName);
    }

    @Override
    public void removeStation(String metroStationName, String stationName) {
        MetroStations metroStations = map.get(metroStationName);
        if (metroStations == null) {
            return;
        }

        metroStations.removeStation(stationName);
    }

}
