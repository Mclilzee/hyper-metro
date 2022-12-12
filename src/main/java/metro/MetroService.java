package metro;

public interface MetroService {

    void addMetroStations(String metroStationsName);

    MetroStations getMetroStations(String metroStation);

    void appendStation(String metroStation, String station);

    void addHead(String metroStation, String station);

    void removeStation(String metroStation, String station);
}
