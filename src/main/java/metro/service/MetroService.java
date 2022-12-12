package metro.service;

import metro.MetroStations;

import java.util.List;
import java.util.Set;

public interface MetroService {

    void addMetroStations(String metroStationsName);

    MetroStations getMetroStations(String metroStationsName);

    void appendStation(String metroStationsName, String stationName);

    void addHead(String metroStationsName, String stationName);

    void removeStation(String metroStationsName, String stationName);

    void putMetroStation(String metroStationName, MetroStations metroStations);

    Set<String> getKeys();

    List<MetroStations> getValues();
}
