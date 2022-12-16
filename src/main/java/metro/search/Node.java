package metro.search;

import metro.Station;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final Station station;
    private boolean visited;
    private boolean transferStation;
    private final Node prev;
    private List<Node> neighbors;

    public Node(Station station) {
        this.station = station;
        this.prev = null;
        neighbors = new ArrayList<>();
        this.visited = false;
        this.transferStation = false;

    }

}
