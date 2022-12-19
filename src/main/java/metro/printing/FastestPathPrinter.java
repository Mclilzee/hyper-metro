package metro.printing;

import metro.search.Node;

import java.util.List;
import java.util.stream.Collectors;

public class FastestPathPrinter implements PathPrinter {

    @Override
    public String getPathString(List<Node> nodes) {
        if (nodes.isEmpty()) {
            return "";
        }

        String nodesString = nodes.stream()
                .map(this::parseNode)
                .collect(Collectors.joining("\n"));

        return nodesString + "\nTotal: %d minutes in the way".formatted(getTotalTime(nodes));
    }

    private String parseNode(Node node) {
        if (node.getTransferLine().isPresent()) {
           return "Transition to line %s\n%s".formatted(node.getTransferLine().get(), node.getName());
        }

        return node.getName();
    }

    private int getTotalTime(List<Node> nodes) {
        return nodes.stream()
                .mapToInt(Node::getWeight)
                .sum();
    }
}
