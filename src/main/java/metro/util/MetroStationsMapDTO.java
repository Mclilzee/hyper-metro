package metro.util;

import java.util.Map;

public class MetroStationsMapDTO {

    private String name;
    private Map<String, String> stationsMap;

    public MetroStationsMapDTO(String name, Map<String, String> stationsMap) {
        this.name = name;
        this.stationsMap = stationsMap;
    }

    public String getName() {
        return this.name;
    }
}
