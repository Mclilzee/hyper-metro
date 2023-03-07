# Hyper Metro System

- Require Java version 17+ <a href="https://www.oracle.com/de/java/technologies/downloads/">Java download Link</a>
- Requires Gradle build tools <a href="https://gradle.org/install/">Gradle Installation Walkthrough</a>

Hyper metro is a project I enjoyed working on a lot, however challenging in some area.

It represents a connection of metro systems, and make use of searching algorithms where you can search for fastest, shortest routes to take from one station to another.

Start by building the project with `gradle build`
Launch the program with `java -jar ./build/libs/hyper-metro-1.0.jar "path to metro lines json file"`

After launching the program you will be prompt for command, use one of the following commands
Note line and station names can have spaces in them, if so then it need to be put inside double quotes such as "Linka A".
- `/output "Metro line name"` will print out all metro stations connections, including other metro lines connections for the current chosen metro line.
`/append "Metro line name" "Metro station name" "Duration from last station"` will append a station to the end of the metro line, plus how long it takes to arrived there.
The unit of measurement is not defined, it could be anything from hours, to minutes, to seconds. It is used for calculating the fastest route.
- `/add-head "Metro line name" "Metro station name" "Duration from depot"` Will add station to the begining after depot station directly, duration will be the units of measuremnts from depot.
- `/remove "Metro line name" "Metro station name"` Will remove a station from the metro line.
- `/connect "First Metro line name" "First metro line station" "Second metro line name" "Second metro line station"` Will connect two metro lines, using two metro stations.
- `/route "First Metro line name" "First metro line station" "Second metro line name" "Second metro line station"` The first searching algorithm to find the shortest route using a Breadth first search algorithm.
- `/fastest-route "First Metro line name" "First metro line station" "Second metro line name" "Second metro line station"` The second searching algorithm to find the fastest route using A* searching algorithm, taking the duration between each stations into consideration.

Two json of metro line connections is provided, one that is shorter for testing purposes while the other is a real metro station connections based of (Prague Metro).

Metro json format example :
```json lines
{
  "Metro Line Name": {
    "3": {
      "name": "Station A",
      "transfer": [
        {
          "line": "Hammersmith-and-City",
          "station": "Baker street"
        }
      ],
      "time": 1
    },
    "1": {
      "name": "Bishops-road",
      "transfer": [],
      "time": 2
    },
    "2": {
      "name": "Edgver road",
      "transfer": [],
      "time": 3
    }
  },
  "Hammersmith-and-City": {
    "2": {
      "name": "Westbourne-park",
      "transfer": [],
      "time": 3
    },
    "1": {
      "name": "Hammersmith",
      "transfer": [],
      "time": 1
    },
    "3": {
      "name": "Baker street",
      "transfer": [
        {
          "line": "Metro-Railway",
          "station": "Baker street"
        }
      ],
      "time": 3
    }
  }
}
```
Examples : 
