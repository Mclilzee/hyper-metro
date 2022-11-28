package hyper;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MetroStations {
    private final Station head = new Station("depot");

    public MetroStations add(Station station) {
        getLastStation().setNextStation(station);

        return this;
    }

    public Station getHead() {
        return head;
    }

    private Station getLastStation() {
        Station station = head;
        while (true) {
           if (station.getNextStation() == null) {
               return station;
           }

           station = station.getNextStation();
        }
    }

}
