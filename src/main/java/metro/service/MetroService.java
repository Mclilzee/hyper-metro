package metro.service;

import metro.MetroLine;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MetroService {

    void addMetroLine(MetroLine metroLine);

    Optional<MetroLine> getMetroLine(String metroLineName);

    void appendStation(String metroLineName, String stationName);

    void addHead(String metroLineName, String stationName);

    void removeStation(String metroLineName, String stationName);

    void connectMetroLine(String metroLineName, String stationName, String toMetroLine, String toStation);

    String findShortestPath(String metroLineName, String stationName, String toMetroLine, String toStation);

    Set<String> getKeys();

    List<MetroLine> getValues();
}
