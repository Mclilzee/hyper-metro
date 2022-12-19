package metro.search;

import java.util.PriorityQueue;
import java.util.Queue;

public class GreedyBreadthFrontier implements Frontier {

    Queue<Node> queue = new PriorityQueue();

    @Override
    public Node pollNode() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void addNode(Node node) {
    }

    @Override
    public void addNeighbors(Node node) {

    }

    @Override
    public void clear() {

    }
}
