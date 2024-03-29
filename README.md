# Hyper Metro System

Hyper metro is a project I enjoyed working on a lot, however challenging in some area.

It represents a connection of metro systems, and make use of searching algorithms where you can search for fastest, shortest routes to take from one station to another.

# Requirement
- Java version 17+ <a href="https://www.oracle.com/de/java/technologies/downloads/">Java download Link</a>

# Build - Run Project
- Clone repository and navigate into repo's directory
- Run project with `$ ./gradlew run --args="path/to/file"`
- `--args=""` takes arguments separated by spaces, if your path file include spaces in the name, use single quotes.
  Example: `--args="'File with space in name' other/argument`


After launching the program you will be prompt for command, use one of the following commands
Note line and station names can have spaces in them, if so then it need to be put inside double quotes such as "Linka A".
- `/metro-lines` will print out all metro lines currently loaded.
- `/output "Metro line name"` will print out all metro stations connections, including other metro lines connections for the current chosen metro line.
- `/append "Metro line name" "Metro station name" "Duration from last station"` will append a station to the end of the metro line, plus how long it takes to arrived there. Duration is in minutes, and is used to calculate the fastest route.
- `/add-head "Metro line name" "Metro station name" "Duration from depot"` Will add station to the beginning after depot station directly, duration in minutes.
- `/remove "Metro line name" "Metro station name"` Will remove a station from the metro line.
- `/connect "First Metro line name" "First metro line station" "Second metro line name" "Second metro line station"` Will connect two metro lines, using two metro stations.
- `/route "First Metro line name" "First metro line station" "Second metro line name" "Second metro line station"` The first searching algorithm to find the shortest route using a Breadth first search algorithm.
- `/fastest-route "First Metro line name" "First metro line station" "Second metro line name" "Second metro line station"` The second searching algorithm to find the fastest route using A* searching algorithm, taking the duration between each stations into consideration.
- `/exit` to quit the application

Two json of metro line connections is provided, one that is shorter for testing purposes while the other is a real metro station connections based of ( <a href="https://en.wikipedia.org/wiki/Prague_Metro">Prague Metro</a> ).
- `metros.json`
- `prague.json`

# Metro Json Format
```json
{
  "Metro-Railway": {
    "3": {
      "name": "Baker street",
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
# Example Usage
```console
$ ./gradlew run --args="metros.json" --console=plain
Loading metro: metros.json
Metro Ready, Check README.md for commands
$ /output Metro-Railway

depot
Bishops-road
Edgver road
Baker street - Baker street (Hammersmith-and-City)
depot
$ /output Metro-Railway

depot
Bishops-road
Edgver road
Baker street - Baker street (Hammersmith-and-City)
depot
$ /exit
```

# Finding Shortest Routes Examples
```console
$ ./gradlew run --args="prague.json" --console=plain
Loading metro: prague.json
Metro Ready, Check README.md for commands
$ /route "Linka A" Malostranska "Linka B" Zlicin

Malostranska
Staromestska
Mustek
Transition to line Linka B
Mustek
Narodni trida
Karlovo namesti
Andel
Smichovske nadrazi
Radlicka
Jinonice
Nove Butovice
Hurka
Luziny
Luka
Stodulky
Zlicin
```
# Finding Fastest Route
```console
$ /fastest-route "Linka A" Dejvicka "Linka B" "Karlovo namesti"

Dejvicka
Hradcanska
Malostranska
Staromestska
Mustek
Transition to line Linka B
Mustek
Narodni trida
Karlovo namesti
Total: 32 minutes in the way
/exit
```
