import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class IMDBGraph {

    static class ActorNode implements Node {

        private String name;
        ArrayList<MovieNode> neighbors;

        public ActorNode(String name) {
            this.name = name;
            this.neighbors = new ArrayList<>();
        }

        public String getName() {
            return name;
        }

        @Override
        public Collection<MovieNode> getNeighbors() {
            return neighbors;
        }

        void addNeighbor(MovieNode m) {
            neighbors.add(m);
        }
    }

    static class MovieNode implements Node {

        private String name;
        private Collection<ActorNode> neighbors;

        public MovieNode(String name) {
            this.name = name;
            this.neighbors = new ArrayList<>();  
        }

        public String getName() {
            return name;
        }

        @Override
        public Collection<ActorNode> getNeighbors() {
            return neighbors;
        }

        void addNeighbor(ActorNode m) {
            neighbors.add(m);
        }
    }


    Map<String, ActorNode> actors;
    Map<String, MovieNode> movies;

    public IMDBGraph(String actorsFilename, String actressesFilename) throws IOException {
        final Scanner actorsScanner = new Scanner(new File(actorsFilename), "ISO-8859-1");
        final Scanner actressesScanner = new Scanner(new File(actressesFilename), "ISO-8859-1");
        actors = new LinkedHashMap<>();
        movies = new LinkedHashMap<>();
        parseData(actorsScanner);
        parseData(actressesScanner);
    }

    private void parseData(Scanner scanner) {
        final String tab = "\t";
        Boolean copyrightInfoDone = false;
        String name;
        ActorNode newActor = null;
        MovieNode newMovie;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            // Skips the first few hundred lines with the copyright information
            if (!copyrightInfoDone) {
                if (line.equals("----			------")) {
                    copyrightInfoDone = true;
                }
                continue;
            }
            if (line.equals("-----------------------------------------------------------------------------")) {
                return;
            }

            // If new actor on this line
            if (line.indexOf(tab) != 0 && !line.isEmpty()) {
                name = line.substring(0, line.indexOf(tab));
                checkIfActorHasMovies(newActor);
                newActor = new ActorNode(name);
                actors.put(newActor.getName(), newActor);
                if (checkTVShow(line)) {
                    continue;
                }
                // FIXED ID ISSUE
                final String firstMovie = line.substring(line.lastIndexOf(tab) + 1, line.lastIndexOf(")") + 1);
                newMovie = new MovieNode(firstMovie);
                actors.get(newActor.getName()).addNeighbor(newMovie);
                addMovie(newMovie, newActor);
            } else {
                if (checkTVShow(line)) {
                    continue;
                }
                if (!line.isEmpty()) {
                    final String movie = line.substring(tab.length() * 3, line.indexOf(")") + 1);
                    newMovie = new MovieNode(movie);
                    addMovie(newMovie, newActor);
                    actors.get(newActor.getName()).addNeighbor(newMovie);
                }
            }
        }
        checkIfActorHasMovies(newActor);
    }

    private static boolean checkTVShow(String line) {
        return (line.contains("(TV)") || line.contains("\""));
    }

    private void addMovie(MovieNode newMovie, ActorNode newActor) {
        if (movies.containsKey(newMovie.getName())) {
            movies.get(newMovie.getName()).addNeighbor(newActor);
        } else {
            newMovie.addNeighbor(newActor);
            movies.put(newMovie.getName(), newMovie);
        }
    }

    private void checkIfActorHasMovies(ActorNode a) {
        if (a == null) {
            return;
        }
        if (a.getNeighbors().isEmpty()) {
            actors.remove(a.getName());
        }
    }

}
