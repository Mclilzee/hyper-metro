package metro.search.frontier;

import metro.search.Node;

public interface Frontier {

    Node pollNode();

    boolean isEmpty();

    void addNode(Node node);

    void addNeighbors(Node node);

    void clear();
}
