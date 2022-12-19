package metro.printing;

import metro.search.Node;

import java.util.List;
import java.util.stream.Collectors;

public class ShortestPathPrinter implements PathPrinter {

    @Override
    public String getPathString(List<Node> nodes) {
        return nodes.stream().map(this::getNodeString).collect(Collectors.joining("\n"));
    }

    private String getNodeString(Node node) {
        if (node.getTransferLine().isPresent()) {
            return String.format("Transition to line %s\n%s", node.getTransferLine().get(), node.getName());
        }

        return node.getStation().getName();
    }
}
