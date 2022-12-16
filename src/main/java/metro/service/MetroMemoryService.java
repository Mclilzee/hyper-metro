package metro.service;

import metro.MetroLine;
import metro.Station;
import metro.search.ShortestPathFinder;
import metro.search.StationPathFinder;

import java.util.*;

public class MetroMemoryService implements MetroService {

    private final Map<String, MetroLine> metroLines = new HashMap<>();

    @Override
    public void addMetroLine(MetroLine metroLine) {
        if (metroLine == null) {
            return;
        }

        metroLines.putIfAbsent(metroLine.getName(), metroLine);
    }

    @Override
    public Optional<MetroLine> getMetroLine(String metroLineName) {
        return Optional.ofNullable(metroLines.get(metroLineName));
    }

    @Override
    public void appendStation(String metroLineName, String stationName) {
        MetroLine metroLine = metroLines.get(metroLineName);
        if (metroLine == null || metroLine.findStationByName(stationName).isPresent()) {
            return;
        }

        metroLine.append(new Station(stationName));
    }

    @Override
    public void addHead(String metroLineName, String stationName) {
        MetroLine metroLine = metroLines.get(metroLineName);
        if (metroLine == null || metroLine.findStationByName(stationName).isPresent()) {
            return;
        }

        metroLine.addHead(new Station(stationName));
    }

    @Override
    public void removeStation(String metroLineName, String stationName) {
        MetroLine metroLine = metroLines.get(metroLineName);
        if (metroLine == null) {
            return;
        }

        metroLine.removeStation(stationName);
    }

    @Override
    public void connectMetroLine(String firstMetroLineName, String firstStationName, String secondMetroLineName, String secondStationName) {
        MetroLine firstMetroLine = metroLines.get(firstMetroLineName);
        MetroLine secondMetroLine = metroLines.get(secondMetroLineName);
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
    public String findShortestPath(String metroLineName, String stationName, String toMetroLine, String toStation) {
        MetroLine firstMetroLine = metroLines.get(metroLineName);
        MetroLine secondMetroLine = metroLines.get(toMetroLine);
        if (firstMetroLine == null || secondMetroLine == null) {
            return "No connection found";
        }

        Optional<Station> startStation = firstMetroLine.findStationByName(stationName);
        Optional<Station> endStation = secondMetroLine.findStationByName(toStation);
        if (startStation.isPresent() && endStation.isPresent()) {
            StationPathFinder finder = new ShortestPathFinder();
            return finder.findPathString(startStation.get(), endStation.get()).orElse("No connection found");
        }

        return "No connection found";
    }

    @Override
    public Set<String> getKeys() {
        return metroLines.keySet();
    }

    @Override
    public List<MetroLine> getValues() {
        return metroLines.values().stream().toList();
    }
}
