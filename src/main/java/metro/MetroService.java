package metro;

public interface MetroService {

    void appendStation(String metroStation, String station);

    MetroStations getMetroStations(String metroStation);

    void addHead(String metroStation, String station);

    void removeStation(String metroStation, String station);

}
