import com.sun.xml.internal.ws.api.ha.StickyFeature;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class IMDBActorsGraph extends IMDBGraph implements Graph {


    private Scanner actorsScanner, actressesScanner;
    private List<ActorNode> actors;

    public IMDBActorsGraph(String actorsFilename, String actressesFilename) throws IOException {
        actorsScanner = new Scanner(new File(actorsFilename), "ISO-8859-1");
        actressesScanner = new Scanner(new File(actressesFilename), "ISO-8859-1");
        actors = new ArrayList<ActorNode>();
        parseData(actorsScanner);
        // parseData(actressesScanner);
    }

    private void parseData(Scanner scanner) {
        final String tab = "    ";
        Boolean copyrightInfoDone = false;
        String name = "";
        List<String> movies = new ArrayList<>();

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
                movies = new ArrayList<>();
                name = line.substring(0, line.indexOf("     "));

                if (line.contains("(TV)") || line.contains("\"")) {
                    continue;
                }

                final String firstMovie = line.substring(line.lastIndexOf("     ") + tab.length() + 1, line.indexOf(")") + 1);
                movies.add(firstMovie);

            } else {
                if (line.contains("(TV)") || line.contains("\"")) {
                    continue;
                }

                if (line.isEmpty()) {
                    actors.add(new ActorNode(name, convertStringsToMovieNodes(movies)));
                } else {
                    final String movie = line.substring(tab.length() * 3, line.indexOf(")") + 1);
                    movies.add(movie);
                }
            }
            if (!scanner.hasNextLine()) {
                actors.add(new ActorNode(name, convertStringsToMovieNodes(movies)));
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



    public Collection<? extends Node> getNodes() {
        return actors;
    }

    public Node getNodeByName(String name) {
        for (ActorNode n : actors) {
            if (n.getName().equals(name)) {
                return n;
            }
        }
        return null;
    }
}
