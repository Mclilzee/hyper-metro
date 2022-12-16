package metro.search;

import metro.Station;

import java.util.*;

public class PathFinder {
    private final Node start;
    private final Node end;
    private Set<Station> visited = new HashSet<>();

    public PathFinder(Station start, Station end) {
        this.start = new Node(start);
        this.end = new Node(end);
    }

    public String getShortestPath() {
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(start);
        visited.add(start.getStation());

        while(!queue.isEmpty()) {
            Node current = queue.poll();

            if (current == end) {
                return getPathString(current);
            }

            addNodeNeighbors(current);
        }

        return null;
    }

    private void addNodeNeighbors(Node node) {
        Station station = node.getStation();

        Optional<Station> prevStation = station.getPreviousStation();
        if (prevStation.isPresent() && !visited.contains(prevStation.get())) {
            Node prevNode = new Node(prevStation.get());
            prevNode.setPrev(node);
            visited.add(prevNode.getStation());

        }
    }
    private String getPathString(Node node) {

        return "";
    }
}
