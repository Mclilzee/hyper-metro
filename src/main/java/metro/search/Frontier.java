package metro.search;

public interface Frontier {

    Node getNode();

    boolean isEmpty();

    Node addNode(Node node);

    Node addNeighbors(Node node);

}
