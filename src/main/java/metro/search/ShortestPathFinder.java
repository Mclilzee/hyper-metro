package metro.search;

import metro.Station;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShortestPathFinder implements StationPathFinder{
    Frontier frontier = new BreadthFrontier();

    @Override
    public Optional<String> findPathString(Station start, Station end) {
        Node startNode = new Node(start);
        Node endNode = new Node(end);

        frontier.addNode(startNode);

        Optional<String> foundString = getPathString(endNode);
        frontier.clear();
        return foundString;
    }

    private Optional<String> getPathString(Node end) {
       while (!frontier.isEmpty()) {
           Node current = frontier.pollNode();

           if (current.equals(end)) {
               return Optional.of(parsePathString(current));
           }

           frontier.addNeighbors(current);
       }

       return Optional.empty();
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
