package metro.service;

import metro.MetroLine;
import metro.Station;

import java.util.*;

public class MetroMemoryService implements MetroService {

    private final Map<String, MetroLine> map = new HashMap<>();


    @Override
    public void addMetroLine(String metroLineName) {
        map.putIfAbsent(metroLineName, new MetroLine(metroLineName));
    }

    @Override
    public void addMetroLine(MetroLine metroLine) {
        if (metroLine == null) {
            return;
        }

        map.putIfAbsent(metroLine.getName(), metroLine);
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
    public void connectMetroLine(String firstMetroLineName, String firstStationName, String secondMetroLineName, String secondStationName) {
        MetroLine firstMetroLine = map.get(firstMetroLineName);
        MetroLine secondMetroLine = map.get(secondMetroLineName);
        if (firstMetroLine == null || secondMetroLine == null) {
            return;
        }

        Optional<Station> firstStation = firstMetroLine.findStationByName(firstStationName);
        Optional<Station> secondStation = secondMetroLine.findStationByName(secondStationName);

        if (firstStation.isPresent() && secondStation.isPresent()) {
            firstMetroLine.addLineConnection(firstStation.get(), secondMetroLine, secondStation.get());
            secondMetroLine.addLineConnection(secondStation.get(), firstMetroLine, firstStation.get());
        }
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
