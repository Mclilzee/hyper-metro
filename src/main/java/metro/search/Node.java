package metro.search;

import metro.Station;

import java.util.Objects;
import java.util.Optional;

public class Node implements Comparable<Node> {
    private final Station station;
    private final String transferLine;
    private final int weight;
    private Node prev;

    public Node(Station station) {
        this(station, 0);
    }

    public Node(Station station, int weight) {
        this.station = station;
        this.transferLine = null;
        this.weight = weight;
    }

    public Node(Station station, String transferLine, int weight) {
        this.station = station;
        this.transferLine = transferLine;
        this.weight = 5 + weight;
    }

    public int getWeight() {
        return weight;
    }

    public Station getStation() {
        return this.station;
    }

    public String getName() {
       return station.getName();
    }

    public Optional<String> getTransferLine() {
        return transferLine == null ? Optional.empty() : Optional.of(transferLine);
    }

    public void setPrev(Node node) {
        this.prev = node;
    }

    public Optional<Node> getPrev() {
        return Optional.ofNullable(prev);
    }

    @Override
    public int compareTo(Node o) {
        return Integer.compare(weight, o.weight);
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
