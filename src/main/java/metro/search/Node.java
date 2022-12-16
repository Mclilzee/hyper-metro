package metro.search;

import metro.LineConnection;
import metro.Station;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Node node = (Node) o;
        return Objects.equals(station, node.station);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(station);
    }
}
