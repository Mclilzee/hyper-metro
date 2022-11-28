package hyper;

public class MetroStations {
    private final Station head = new Station("depot");

    public MetroStations add(Station station) {
        station.setNextStation(head);
        Station lastStation = getLastStation();
        lastStation.setNextStation(station);

        return this;
    }

    public Station getHead() {
        return head;
    }

    private Station getLastStation() {
        if (head.getNextStation() == null) {
            return head;
        }

        Station station = head;
        while (true) {
           if (station.getNextStation() == head) {
               return station;
           }

           station = station.getNextStation();
        }
    }

}
