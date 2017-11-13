import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class IMDBGraph {

    private static class ActorNode implements Node {

        private String name;
        private Collection<? extends Node> neighbors;

        public ActorNode(String name, Collection<? extends Node> neighbors) {
            this.name = name;
            this.neighbors = neighbors;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Collection<? extends Node> getNeighbors() {
            return neighbors;
        }

    }

    private static class MovieNode implements Node {

        private String name;
        private Collection<MovieNode> neighbors;

        public MovieNode(String name, Collection<? extends Node> neighbors) {
            this.name = name;
            this.neighbors = (Collection<MovieNode>) neighbors;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Collection<? extends Node> getNeighbors() {
            return neighbors;
        }
    }


    private Scanner actorsScanner, actressesScanner;
    private List<ActorNode> actors;
    private List<MovieNode> movies;

    public IMDBGraph(String actorsFilename, String actressesFilename) throws IOException {
        actorsScanner = new Scanner(new File(actorsFilename), "ISO-8859-1");
        actressesScanner = new Scanner(new File(actressesFilename), "ISO-8859-1");
        actors = new ArrayList<ActorNode>();
        movies = new ArrayList<MovieNode>();
        parseData(actorsScanner);
        // parseData(actressesScanner);
    }

    private void parseData(Scanner scanner) {
        final String tab = "    "; // 4 spaces
        Boolean copyrightInfoDone = false;
        String name = "";
        List<String> moviesForCurrActor = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            // Skips the first few hundred lines with the copyright information
            if (!copyrightInfoDone) {
                if (line.equals("----			------")) {
                    copyrightInfoDone = true;
                    scanner.nextLine();
                }
                continue;
            }

            // If new actor on this line
            if (line.indexOf(tab) != 0 && !line.isEmpty()) {
                moviesForCurrActor = new ArrayList<>();
                name = line.substring(0, line.indexOf("     "));

                if (line.contains("(TV)") || line.contains("\"")) {
                    continue;
                }

                final String firstMovie = line.substring(line.lastIndexOf("     ") + tab.length() + 1, line.indexOf(")") + 1);
                moviesForCurrActor.add(firstMovie);
//                final List actorsInMovie = new ArrayList();
//                actorsInMovie.add(new ActorNode(name, null));
//
//                if (movies.contains(firstMovie))
//                movies.add(new MovieNode(firstMovie, actorsInMovie));

            } else {
                if (line.contains("(TV)") || line.contains("\"")) {
                    continue;
                }

                if (line.isEmpty()) {
                    actors.add(new ActorNode(name, convertStringsToMovieNodes(moviesForCurrActor)));
                } else {
                    final String movie = line.substring(tab.length() * 3, line.indexOf(")") + 1);
                    moviesForCurrActor.add(movie);
                }
            }
            if (!scanner.hasNextLine()) {
                actors.add(new ActorNode(name, convertStringsToMovieNodes(moviesForCurrActor)));
            }
        }
    }

    private Collection<MovieNode> convertStringsToMovieNodes(List<String> list) {
        Collection<MovieNode> movieNodes = new ArrayList<>();

        for (String s : list) {
            movieNodes.add(new MovieNode(s, null));
        }

        return movieNodes;
    }

}
