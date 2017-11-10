import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class IMDBActorsGraph implements Graph {

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

    private Scanner actorsScanner, actressesScanner;
    private List<ActorNode> actors;

    public IMDBActorsGraph(String actorsFilename, String actressesFilename) throws IOException {
        actorsScanner = new Scanner(new File(actorsFilename), "ISO-8859-1");
        actressesScanner = new Scanner(new File(actressesFilename), "ISO-8859-1");
        actors = new ArrayList<ActorNode>();
    }

    private void parseData() {

        Boolean copyrightInfoDone = false;

        while (actorsScanner.hasNextLine()) {
            String line = actorsScanner.nextLine();

            // Skips the first few hundred lines with the copyright information
            if (!copyrightInfoDone) {
                if (line.equals("----			------")) {
                    copyrightInfoDone = true;
                }
                continue;
            }

            final String name = line.substring(0, line.indexOf("\t"));
            final List<String> movies = new ArrayList<>();
            final String firstMovie = line.substring(line.indexOf("\t") + 1, line.indexOf(")") + 1);
            movies.add(firstMovie);

            while (!actorsScanner.nextLine().equals("")) {
                line = actorsScanner.nextLine();
                String movie = line.substring(0, line.indexOf(")") + 1);
                movies.add(movie);
            }

            // actors.add(new ActorNode(name, movies));

        }

        actorsScanner.close();
    }


    @Override
    public Collection<? extends Node> getNodes() {
        return actors;
    }

    @Override
    public Node getNodeByName(String name) {
        return null;
    }
}
