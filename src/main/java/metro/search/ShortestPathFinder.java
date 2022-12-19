package metro.search;

import metro.Station;
import metro.search.frontier.Frontier;
import metro.search.frontier.FrontierFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShortestPathFinder implements PathFinder {
    Frontier frontier = FrontierFactory.getBreadthFrontier();

    @Override
    public List<Node> findPath(Station start, Station end) {
        Node startNode = new Node(start);
        Node endNode = new Node(end);

        frontier.addNode(startNode);

        List<Node> nodePath = getPath(endNode);
        frontier.clear();
        return nodePath;
    }

    private List<Node> getPath(Node end) {
       while (!frontier.isEmpty()) {
           Node current = frontier.pollNode();

           if (current.equals(end)) {
               return backtrackNode(current);
           }

           frontier.addNeighbors(current);
       }

       return List.of();
    }

    private List<Node> backtrackNode(Node end) {
        List<Node> nodes = Stream.iterate(Optional.ofNullable(end), Optional::isPresent, node -> node.flatMap(Node::getPrev))
                .map(Optional::orElseThrow)
                .collect(Collectors.toList());

        Collections.reverse(nodes);
        return nodes;
    }
}
