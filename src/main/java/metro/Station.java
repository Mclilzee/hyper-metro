package metro;

import java.util.Optional;

public class Station {

    private final String name;
    private Station nextStation;

    public Station(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Optional<Station> getNextStation() {
        return Optional.ofNullable(nextStation);
    }

    public void setNextStation(Station nextStation) {
        this.nextStation = nextStation;
    }
}
