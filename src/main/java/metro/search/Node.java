package metro.search;

import metro.Station;

import java.util.Optional;

public class Node {
    private final Station station;
    private boolean transferStation;
    private Node prev;

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

    public void setPrev(Node node) {
        this.prev = node;
    }

    public Optional<Node> getPrev() {
        return Optional.ofNullable(prev);
    }
}
