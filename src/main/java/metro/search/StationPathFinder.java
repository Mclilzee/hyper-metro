package metro.search;

import metro.Station;

import java.util.Optional;

public interface StationPathFinder {

    Optional<String> findPathString(Station start, Station end);
}
