package metro.search;

import metro.Station;

import java.util.*;

public class BreadthFrontier implements Frontier {

    Queue<Node> queue = new ArrayDeque<>();
    Set<Node> visited = new HashSet<>();

    @Override
    public Node pollNode() {
        Node node = queue.poll();
        if (node != null) {
            return node;
        }

        throw new NoSuchElementException("Frontier is empty");
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public void addNode(Node node) {
        if (!visited.contains(node)) {
            queue.add(node);
            visited.add(node);
        }
    }

    @Override
    public void addNeighbors(Node node) {
        node.getStation().getNextStation().ifPresent(station -> addConnectedNode(node, station));
        node.getStation().getPreviousStation().ifPresent(station -> addConnectedNode(node, station));
        addLineConnectedNodes(node);
    }

    private void addConnectedNode(Node node, Station station) {
        Node prevNode = new Node(station);
        prevNode.setPrev(node);
        addNode(prevNode);
    }

    private void addLineConnectedNodes(Node current) {
        List<Node> nodes = current.getStation().getLineConnections().stream()
                                  .map(connection -> new Node(connection.station(), connection.metroLine().getName()))
                                  .filter(eachNode -> !visited.contains(eachNode))
                                  .toList();

        for (Node node : nodes) {
            node.setPrev(current);
            addNode(node);
        }
    }

        @Override
    public void clear() {
        queue.clear();
        visited.clear();
    }
}
