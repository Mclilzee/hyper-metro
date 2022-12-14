package metro.service;

import metro.MetroLine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MetroMemoryService implements MetroService {

    private final Map<String, MetroLine> map = new HashMap<>();


    @Override
    public void addMetroLine(String metroLineName) {
        map.putIfAbsent(metroLineName, new MetroLine());
    }

    @Override
    public void putMetroLine(String MetroLineName, MetroLine metroLine) {
        if (metroLine == null) {
            return;
        }

        map.putIfAbsent(MetroLineName, metroLine);
    }

    @Override
    public MetroLine getMetroLine(String metroLineName) {
        return map.get(metroLineName);
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
    public Set<String> getKeys() {
        return map.keySet();
    }

    @Override
    public List<MetroLine> getValues() {
        return map.values().stream().toList();
    }
}
