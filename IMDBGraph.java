import java.io.File;
import java.io.IOException;
import java.util.*;

public class IMDBGraph {

    static class ActorNode implements Node {

        private String name;
        private Collection<MovieNode> neighbors;

        public ActorNode(String name) {
            this.name = name;
            this.neighbors = new ArrayList<>();
        }

        public void addNeighbor(MovieNode m) {
            neighbors.add(m);
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Collection<MovieNode> getNeighbors() {
            return neighbors;
        }
    }

    static class MovieNode implements Node {

        private String name;
        private Collection<ActorNode> neighbors;

        public MovieNode(String name) {
            this.name = name;
            this.neighbors = new ArrayList<>();
        }

        public void addNeighbor(ActorNode m) {
            neighbors.add(m);
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Collection<ActorNode> getNeighbors() {
            return neighbors;
        }
    }


    private Scanner actorsScanner, actressesScanner;
    Map<String, ActorNode> actors;
    Map<String, MovieNode> movies;

    public IMDBGraph(String actorsFilename, String actressesFilename) throws IOException {
        actorsScanner = new Scanner(new File(actorsFilename), "ISO-8859-1");
        actressesScanner = new Scanner(new File(actressesFilename), "ISO-8859-1");
        actors = new LinkedHashMap<>();
        movies = new LinkedHashMap<>();
        parseData(actorsScanner);
        parseData(actressesScanner);
    }

    private void parseData(Scanner scanner) {
        final String tab = "\t"; // 4 spaces
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
                    // scanner.nextLine();
                }
                continue;
            }

            if (line.equals("-----------------------------------------------------------------------------")) {
                return;
            }

            // If new actor on this line
            if (line.indexOf(tab) != 0 && !line.isEmpty()) {
                name = line.substring(0, line.indexOf(tab));
                newActor = new ActorNode(name);
                actors.put(newActor.getName(), newActor);

                if (line.contains("(TV)") || line.contains("\"")) {
                    continue;
                }

                final String firstMovie = line.substring(line.lastIndexOf(tab) + 1, line.lastIndexOf(")") + 1);

                actors.get(newActor.getName()).addNeighbor(new MovieNode(firstMovie));
                newMovie = new MovieNode(firstMovie);

                // if the movie is already in the list
                if (movies.containsKey(newMovie.getName())) {
                    movies.get(newMovie.getName()).addNeighbor(newActor);
                } else {
                    newMovie.addNeighbor(newActor);
                    movies.put(newMovie.getName(), newMovie);
                }
            } else {
                if (line.contains("(TV)") || line.contains("\"")) {
                    continue;
                }

                if (!line.isEmpty()) {
                    final String movie = line.substring(tab.length() * 3, line.indexOf(")") + 1);
                    newMovie = new MovieNode(movie);

                    // if the movie is already in the list
                    if (movies.containsKey(newMovie.getName())) {
                        movies.get(newMovie.getName()).addNeighbor(newActor);
                    } else {
                        newMovie.addNeighbor(newActor);
                        movies.put(newMovie.getName(), newMovie);
                    }

                    actors.get(newActor.getName()).addNeighbor(newMovie);
                }
            }
        }

        removeActorsWithoutMovies();
    }

    private void removeActorsWithoutMovies() {
        Iterator<String> iterator = actors.keySet().iterator();
        while(iterator.hasNext()) {
            String name = iterator.next();
            ActorNode a = actors.get(name);
            if (a.getNeighbors().isEmpty()) {
                iterator.remove();
            }
        }
    }

}
