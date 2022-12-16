package metro.search;

import metro.Station;

import java.util.Objects;
import java.util.Optional;

public class Node {
    private final Station station;
    private final String transferLine;
    private Node prev;

    public Node(Station station) {
        this(station, "");
    }

    public Node(Station station, String transferLine) {
        this.station = station;
        this.prev = null;
        this.transferLine = transferLine;
    }

    public Station getStation() {
        return this.station;
    }

    public String getName() {
       return station.getName();
    }

    public Optional<String> getTransferLine() {
        return transferLine.isEmpty() ? Optional.empty() : Optional.of(transferLine);
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
