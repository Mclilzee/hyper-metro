package metro;

import java.util.Optional;

public class Station {

    private final String name;
    private Station nextStation;
    private Station previousStation;

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

    public Optional<Station> getPreviousStation() {
        return Optional.ofNullable(previousStation);
    }

    public void setPreviousStation(Station previousStation) {
        this.previousStation = previousStation;
    }
}
