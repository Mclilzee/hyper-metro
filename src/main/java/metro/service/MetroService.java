package metro.service;

import metro.MetroStations;

import java.util.List;
import java.util.Set;

public interface MetroService {

    void addMetroStations(String metroStationsName);

    MetroStations getMetroStations(String metroStation);

    void appendStation(String metroStation, String station);

    void addHead(String metroStation, String station);

    void removeStation(String metroStation, String station);

    Set<String> getKeys();

    List<MetroStations> getValues();
}
