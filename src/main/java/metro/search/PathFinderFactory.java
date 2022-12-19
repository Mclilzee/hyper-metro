package metro.search;

import metro.search.BreadthPathFinder;
import metro.search.PathFinder;
import metro.search.frontier.Frontier;
import metro.search.frontier.FrontierFactory;

public class PathFinderFactory {

    public static PathFinder getShortestPathFinder() {
        Frontier frontier = FrontierFactory.getBreadthFrontier();
        return new BreadthPathFinder(frontier);
    }

    public static PathFinder getFastestPathFinder() {
        Frontier frontier = FrontierFactory.getGreedyBreadthFrontier();
        return new BreadthPathFinder(frontier);
    }

}
