package metro.service;

import metro.MetroLine;

import java.util.List;
import java.util.Set;

public interface MetroService {

    void addMetroLine(String metroLineName);

    MetroLine getMetroLine(String metroLineName);

    void appendStation(String metroLineName, String stationName);

    void addHead(String metroLineName, String stationName);

    void removeStation(String metroLineName, String stationName);

    void putMetroLine(String MetroLineName, MetroLine metroLine);

    Set<String> getKeys();

    List<MetroLine> getValues();
}
