package hyper;

public class Station {

    private final String name;
    private Station nextStation;

    public Station(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Station getNextStation() {
        return nextStation;
    }

    public void setNextStation(Station nextStation) {
        this.nextStation = nextStation;
    }
}
