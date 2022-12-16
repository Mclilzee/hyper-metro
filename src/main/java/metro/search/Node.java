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
    private boolean visited;
    private Set<Node> neighbors;

    public Node(Station station) {
        this.station = station;
        this.prev = null;
        this.transferStation = false;
        this.visited = false;
        neighbors = setNeighbors(station);
    }

    private Set<Node> setNeighbors(Station station) {
        Set<Node> set = new HashSet<>();
        if (station.getPreviousStation().isPresent()) {
            Node prevNode = new Node(station.getPreviousStation().get());
            set.add(prevNode);
        }

        if (station.getNextStation().isPresent()) {
            Node nextNode = new Node(station.getNextStation().get());
            set.add(nextNode);
        }

        set.addAll(getLineConnectionNeighbors(station));

        return set;
    }

    private Set<Node> getLineConnectionNeighbors(Station station) {
        Set<Node> nodes =  station.getLineConnections().stream()
                .map(LineConnection::station)
                .map(Node::new)
                .collect(Collectors.toSet());

        nodes.forEach(node -> node.setTransferStation(true));
        return nodes;
    }



    public Station getStation() {
        return this.station;
    }

    public boolean isVisited() {
        return this.visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
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

    public Set<Node> getNeighbors() {
        return neighbors;
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
