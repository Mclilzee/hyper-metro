package metro.search;

import metro.Station;

public class Node {
    private final Station station;
    private boolean transferStation;
    private final Node prev;

    public Node(Station station) {
        this.station = station;
        this.prev = null;
        this.transferStation = false;

    }

    public Station getStation() {
        return this.station;
    }

    public boolean isTransferStation() {
        return transferStation;
    }

    public void setTransferStation(boolean transferStation) {
        this.transferStation = transferStation;
    }
}
