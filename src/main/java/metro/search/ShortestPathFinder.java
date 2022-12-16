package metro.search;

import metro.LineConnection;
import metro.Station;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShortestPathFinder implements StationPathFinder{
    Queue<Node> queue = new ArrayDeque<>();
    Set<Node> visited = new HashSet<>();

    @Override
    public String findPathString(Station start, Station end) {
        Node startNode = new Node(start);
        Node endNode = new Node(end);

        queue.add(startNode);
        visited.add(startNode);

        return getShortestPathString(endNode);
    }

    private String getShortestPathString(Node end) {
       while (!queue.isEmpty()) {
           Node current = queue.poll();

           if (current.equals(end)) {
               return parsePathString(current);
           }

           addNeighbors(current);
       }

       return "";
    }

    private void addNeighbors(Node current) {
        current.getStation().getNextStation().ifPresent(station -> addConnectedNode(current, station));
        current.getStation().getPreviousStation().ifPresent(station -> addConnectedNode(current, station));
        addLineConnectedNodes(current);
    }

    private void addConnectedNode(Node current, Station station) {
        Node prevNode = new Node(station);
        if (!visited.contains(prevNode)) {
            prevNode.setPrev(current);
            queue.add(prevNode);
            visited.add(prevNode);
        }
    }

    private void addLineConnectedNodes(Node current) {
        List<Node> nodes = current.getStation().getLineConnections().stream()
                .map(connection -> new Node(connection.station(), connection.metroLine().getName()))
                .filter(eachNode -> !visited.contains(eachNode))
                .toList();

        for (Node node : nodes) {
            queue.add(node);
            visited.add(node);
            node.setPrev(current);
        }
    }

    private String parsePathString(Node end) {
        List<Node> nodes = Stream.iterate(Optional.ofNullable(end), Optional::isPresent, node -> node.flatMap(Node::getPrev))
                .map(Optional::orElseThrow)
                .collect(Collectors.toList());

        Collections.reverse(nodes);
        return nodes.stream()
                .map(this::getNodeString)
                .collect(Collectors.joining("\n"));
    }

    private String getNodeString(Node node) {
        if (node.getTransferLine().isPresent()) {
            return String.format("Transition to line %s\n%s", node.getTransferLine().get(), node.getName());
        }

        return node.getStation().getName();
    }
}
