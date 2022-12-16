package metro.search;

import metro.Station;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

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
        }

        return null;
    }

    private String getPathString(Node node) {

        return "";
    }
}
