package metro.service;

import metro.MetroLine;
import metro.Station;
import metro.printing.*;
import metro.search.Node;
import metro.search.PathFinder;
import metro.search.PathFinderFactory;

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
    public void appendStation(String metroLineName, String stationName, int time) {
        MetroLine metroLine = metroLines.get(metroLineName);
        if (metroLine == null || metroLine.findStationByName(stationName).isPresent()) {
            return;
        }

        metroLine.append(new Station(stationName, time));
    }

    @Override
    public void addHead(String metroLineName, String stationName, int time) {
        MetroLine metroLine = metroLines.get(metroLineName);
        if (metroLine == null || metroLine.findStationByName(stationName).isPresent()) {
            return;
        }

        metroLine.addHead(new Station(stationName, time));
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
    public String getMetroLineInformation(String metroLineName) {
        MetroLine metroLine = getMetroLine(metroLineName);
        MetroPrinter printer = new LineConnectionsPrinter();

        return printer.getMetroLinePrintString(metroLine);
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
        Station startStation = getStation(metroLineName, stationName);
        Station endStation = getStation(toMetroLine, toStation);

        PathFinder finder = PathFinderFactory.getShortestPathFinder();
        List<Node> path = finder.findPath(startStation, endStation);
        PathPrinter printer = new ShortestPathPrinter();

        return printer.getPathString(path);
    }

    @Override
    public String findFastestPath(String metroLineName, String stationName, String toMetroLine, String toStation) {
        Station startStation = getStation(metroLineName, stationName);
        Station endStation = getStation(toMetroLine, toStation);

        PathFinder finder = PathFinderFactory.getFastestPathFinder();
        List<Node> path = finder.findPath(startStation, endStation);
        PathPrinter printer = new FastestPathPrinter();

        return printer.getPathString(path);
    }

    private MetroLine getMetroLine(String metroLineName) {
        MetroLine metroLine = metroLines.get(metroLineName);
        if (metroLine == null) {
            throw new IllegalArgumentException("No metro line with name " + metroLineName);
        }

        return metroLine;
    }

    private Station getStation(String metroLineName, String stationName) {
        MetroLine metroLine = getMetroLine(metroLineName);
        Optional<Station> station = metroLine.findStationByName(stationName);
        return station.orElseThrow(() -> new IllegalArgumentException("No station with name " + stationName + " found"));
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
