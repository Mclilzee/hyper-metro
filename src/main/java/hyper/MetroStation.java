package hyper;

public class MetroStation {

    private final String name;
    private MetroStation nextStation;

    public MetroStation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public MetroStation getNextStation() {
        return nextStation;
    }

    public void setNextStation(MetroStation nextStation) {
        this.nextStation = nextStation;
    }
}
