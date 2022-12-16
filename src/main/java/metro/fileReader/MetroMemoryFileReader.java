package metro.fileReader;

import metro.service.MetroMemoryService;
import metro.service.MetroService;

import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MetroMemoryFileReader implements MetroFileReader {

    @Override
    public MetroService loadMetroServiceFromFile(Path path) {
        if (path.toString()
                .endsWith(".json")) {
            return getJsonMetroService(path);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private MetroService getJsonMetroService(Path path) {
        var jsonMap = MetroJsonReader.parseMetroJson(path);
        MetroMemoryService service = new MetroMemoryService();
        if (jsonMap.isEmpty()) {
            System.out.println("Incorrect file");
            return service;
        }

        jsonMap.get().entrySet().stream()
                .map((entry) -> MetroLineFactory.createUnconnectedMetroLine(entry.getKey(), entry.getValue()))
                .forEach(service::addMetroLine);

        connectStations(service, jsonMap.get());

        return service;
    }

    private void connectStations(MetroService service, Map<String, Map<String, StationDTO>> data) {
        data.entrySet().stream()
                .map(entrySet -> generateConnectionString(entrySet.getKey(), entrySet.getValue()))
                .flatMap(Collection::stream)
                .forEach(dto -> service.connectMetroLine(dto.firstMetroStationName, dto.firstStationName, dto.secondMetroStationName, dto.secondStationName));
    }

    private List<ConnectionStringDTO> generateConnectionString(String metroName, Map<String, StationDTO> data) {
        return data.values().stream()
                .filter(dto -> !dto.getTransfer().isEmpty())
                .map(dto -> mapStationDTOToConnectionsList(metroName, dto))
                .flatMap(Collection::stream)
                .toList();
    }

    private List<ConnectionStringDTO> mapStationDTOToConnectionsList(String metroName, StationDTO stationDTO) {
       String stationDTOName = stationDTO.getName();
       return stationDTO.getTransfer().stream()
               .map(transfer -> new ConnectionStringDTO(metroName, stationDTOName, transfer.getLine(), transfer.getStation()))
               .toList();
    }

    record ConnectionStringDTO(String firstMetroStationName,
                         String firstStationName,
                         String secondMetroStationName,
                         String secondStationName) {
    }
}
