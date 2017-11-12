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
        parseData();
        System.out.println(actors.size());
        for (ActorNode n : actors) {
            System.out.println(n.getName());
            if (n.getName().equals("$hutter")) {
                System.out.println(n.getNeighbors().toString());
            }
        }
    }

//    private void parseData() {
//
//        Boolean copyrightInfoDone = false;
//
//        while (actorsScanner.hasNextLine()) {
//
//            String line = actorsScanner.nextLine();
//
//            // Skips the first few hundred lines with the copyright information
//            if (!copyrightInfoDone) {
//                if (line.equals("----			------")) {
//                    copyrightInfoDone = true;
//                    actorsScanner.nextLine();
//                }
//                continue;
//            }
//            if (line.isEmpty()) {
//                continue;
//            }
//
//            final String name = line.substring(0, line.indexOf("     "));
//            final List<String> movies = new ArrayList<>();
//
//            System.out.println(name);
//
//            // checks if the line with the actor's name has a TV show
//            if (!(line.contains("(TV)") || line.contains("\""))) {
//                final String firstMovie = line.substring(line.lastIndexOf("     ") + 6, line.indexOf(")") + 1);
//                movies.add(firstMovie);
//            }
//
//            // recurs through the list of movies/TV shows for the actor at hand
//            while (!actorsScanner.nextLine().isEmpty() && actorsScanner.hasNextLine()) {
//                String line2 = actorsScanner.nextLine();
//                System.out.println("Line: " + line2);
//                if (line2.contains("(TV)") || line2.contains("\"")) {
//                    continue;
//                }
//                String movie = line2.substring(0, line2.indexOf(")") + 1);
//                System.out.println("Movie: " + movie);
//                movies.add(movie);
//            }
//
//            actors.add(new ActorNode(name, convertStringsToMovieNodes(movies)));
//        }
//        actorsScanner.close();
//    }

    private void parseData() {
        final String tab = "    ";
        Boolean copyrightInfoDone = false;
        String name = "";
        List<String> movies = new ArrayList<>();

        while (actorsScanner.hasNextLine()) {
            String line = actorsScanner.nextLine();

            // Skips the first few hundred lines with the copyright information
            if (!copyrightInfoDone) {
                if (line.equals("----			------")) {
                    copyrightInfoDone = true;
                    actorsScanner.nextLine();
                }
                continue;
            }
            System.out.println(line);

//            if (line.isEmpty()) {
//                continue;
//            }

            // If new actor on this line
            if (line.indexOf(tab) != 0 && !line.isEmpty()) {
                movies = new ArrayList<>();
                name = line.substring(0, line.indexOf("     "));

                if (line.contains("(TV)") || line.contains("\"")) {
                    continue;
                }

                final String firstMovie = line.substring(line.lastIndexOf("     ") + tab.length() + 1, line.indexOf(")") + 1);
                System.out.println("First Movie: " + firstMovie);
                movies.add(firstMovie);
            } else {
                if (line.contains("(TV)") || line.contains("\"")) {
                    continue;
                }

                if (line.isEmpty()) {
                    actors.add(new ActorNode(name, convertStringsToMovieNodes(movies)));
                } else {
                    final String movie = line.substring(tab.length() * 3, line.indexOf(")") + 1);
                    System.out.println("Movie: " + movie);
                    movies.add(movie);
                }

                if (!actorsScanner.hasNextLine()) {
                    actors.add(new ActorNode(name, convertStringsToMovieNodes(movies)));
                }
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


    @Override
    public Collection<? extends Node> getNodes() {
        return actors;
    }

    @Override
    public Node getNodeByName(String name) {
        return null;
    }
}
