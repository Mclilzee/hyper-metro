package metro.search.frontier;

import java.util.ArrayDeque;
import java.util.PriorityQueue;

public class FrontierFactory {

    public static Frontier getBreadthFrontier() {
        return new QueueFrontier(new ArrayDeque<>());
    }

    public static Frontier getGreedyBreadthFrontier() {
        return new QueueFrontier(new PriorityQueue<>());
    }

}
