package metro.service;

import metro.MetroLine;
import metro.Station;

import java.util.*;

public class MetroMemoryService implements MetroService {

    private final Map<String, MetroLine> map = new HashMap<>();


    @Override
    public void addMetroLine(String metroLineName) {
        map.putIfAbsent(metroLineName, new MetroLine());
    }

    @Override
    public void putMetroLine(String metroLineName, MetroLine metroLine) {
        if (metroLine == null) {
            return;
        }

        map.putIfAbsent(metroLineName, metroLine);
    }

    @Override
    public Optional<MetroLine> getMetroLine(String metroLineName) {
        return Optional.ofNullable(map.get(metroLineName));
    }

    @Override
    public void appendStation(String metroLineName, String stationName) {
        MetroLine metroLine = map.get(metroLineName);
        if (metroLine == null) {
            return;
        }

        metroLine.append(stationName);
    }

    @Override
    public void addHead(String metroLineName, String stationName) {
        MetroLine metroLine = map.get(metroLineName);
        if (metroLine == null) {
            return;
        }

        metroLine.addHead(stationName);
    }

    @Override
    public void removeStation(String metroLineName, String stationName) {
        MetroLine metroLine = map.get(metroLineName);
        if (metroLine == null) {
            return;
        }

        metroLine.removeStation(stationName);
    }

    @Override
    public void connectMetroLine(String metroLineName, String stationName, String toMetroLine, String toStation) {
        if (!map.containsKey(toMetroLine) || !map.containsKey(metroLineName)) {
            return;
        }

        if (connectingMetroLineContainsStation(toMetroLine, toStation)) {
            MetroLine metroLine = map.get(metroLineName);
            metroLine.addLineConnection(stationName, toMetroLine, toStation);
        }
    }

    private boolean connectingMetroLineContainsStation(String metroLine, String station) {
        return map.get(metroLine).getStationsConnection().stream()
                .map(Station::getName)
                .anyMatch(station::equals);
    }

    @Override
    public Set<String> getKeys() {
        return map.keySet();
    }

    @Override
    public List<MetroLine> getValues() {
        return map.values().stream().toList();
    }
}
