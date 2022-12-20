package metro.search.frontier;

import metro.Station;
import metro.search.Node;

import java.util.*;

public class QueueFrontier implements Frontier {

    protected final Queue<Node> queue;
    private final Set<Node> visited = new HashSet<>();

    protected QueueFrontier(Queue<Node> queue) {
        this.queue = queue;
    }

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
        Node neighborNode = new Node(station, node.getWeight() + station.getTime());
        neighborNode.setPrev(node);
        addNode(neighborNode);
    }

    private void addLineConnectedNodes(Node current) {
        List<Node> nodes = current.getStation().getLineConnections().stream()
                                  .map(connection -> new Node(connection.station(), connection.metroLine().getName(), current.getWeight()))
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
