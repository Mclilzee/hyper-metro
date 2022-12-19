package metro.service;

import metro.MetroLine;

import java.util.List;
import java.util.Set;

public interface MetroService {

    void addMetroLine(MetroLine metroLine);

    void appendStation(String metroLineName, String stationName, int time);

    void addHead(String metroLineName, String stationName, int time);

    void removeStation(String metroLineName, String stationName);

    String getMetroLineInformation(String metroLineName);

    void connectMetroLine(String metroLineName, String stationName, String toMetroLine, String toStation);

    String findShortestPath(String metroLineName, String stationName, String toMetroLine, String toStation);

    String findFastestPath(String metroLineName, String stationName, String toMetroLine, String toStation);

    Set<String> getKeys();

    List<MetroLine> getValues();
}
