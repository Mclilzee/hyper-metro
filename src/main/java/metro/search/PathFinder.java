package metro.search;

import metro.Station;

import java.util.List;

public interface PathFinder {

    List<Node> findPath(Station start, Station end);
}
