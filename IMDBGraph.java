import java.io.File;
import java.io.IOException;
import java.util.*;

public class IMDBGraph {
	
	protected static class IMDBNode implements Node {

        final String _name;
        final Collection<IMDBNode> _neighbors;

        /**
         * Constructor
         * @param name the name of the node
         */
        IMDBNode(String name) {
            _name = name;
            _neighbors = new ArrayList<>();
        }

        /**
         * Adds n as a neighbor to the current node
         * @param n the node
         */
        void addNeighbor(IMDBNode n) {
            _neighbors.add(n);
        }

        /**
         * Returns the name of the node
         * @return the name of the node
         */
        @Override
        public String getName() {
            return _name;
        }

        /**
    	 * Returns the collection of all the neighbors of the node
    	 * @return the collection of all the neighbors of the node
    	 */
        @Override
        public Collection<IMDBNode> getNeighbors() {
            return _neighbors;
        }
    }

    final static class ActorNode extends IMDBNode {
    	
        final private Collection<MovieNode> _neighbors;

        /**
         * Constructor
         * @param name the name of the actor
         */
        ActorNode(String name) {
            super(name);
            _neighbors = new ArrayList<>();
        }
    }

    final static class MovieNode extends IMDBNode {

        final private Collection<ActorNode> _neighbors;

        /**
         * Constructor
         * @param name the name of the movie
         */
        public MovieNode(String name) {
            super(name);
            _neighbors = new ArrayList<>();
        }
    }

    final Map<String, ActorNode> actors;
    final Map<String, MovieNode> movies;

    /**
     * Constructor
     * @param actorsFilename the filename of the actors
     * @param actressesFilename the filename of the actresses
     * @throws IOException
     */
    public IMDBGraph(String actorsFilename, String actressesFilename) throws IOException {
        final Scanner actorsScanner = new Scanner(new File(actorsFilename), "ISO-8859-1");
        final Scanner actressesScanner = new Scanner(new File(actressesFilename), "ISO-8859-1");
        actors = new LinkedHashMap<>();
        movies = new LinkedHashMap<>();
        parseData(actorsScanner);
        parseData(actressesScanner);
    }

    /**
     * Parses through the given files from the scanner to construct a graph
     * @param scanner the scanner object used for the IMDB files
     */
    private void parseData(Scanner scanner) {
        final String tab = "\t"; // 4 spaces
        Boolean copyrightInfoDone = false;
        String name;
        ActorNode newActor = null;
        MovieNode newMovie;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
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

                if (line.contains("(TV)") || line.contains("\"")) {
                    continue;
                }

                final String firstMovie = line.substring(line.lastIndexOf(tab) + 1, line.lastIndexOf(")") + 1);

                newMovie = new MovieNode(firstMovie);
                actors.get(newActor.getName()).addNeighbor(newMovie);
                addMovies(newMovie, newActor);
                
            } else {
                if (line.contains("(TV)") || line.contains("\"")) {
                    continue;
                }

                if (!line.isEmpty()) {
                    final String movie = line.substring(tab.length() * 3, line.indexOf(")") + 1);
                    newMovie = new MovieNode(movie);
                    addMovies(newMovie, newActor);
                    actors.get(newActor.getName()).addNeighbor(newMovie);
                }
            }
        }
        checkIfActorHasMovies(newActor);
    }

    private void checkIfActorHasMovies(ActorNode a) {
        if (a == null) {
            return;
        }
        if (a.getNeighbors().isEmpty()) {
            actors.remove(a.getName());
        }
    }
    
    /**
     * Adds specified movie to the specified actor
     * @param movieNode the movie node
     * @param actorNode the actor node
     */
    private void addMovies(MovieNode movieNode, ActorNode actorNode) {
    	// if the movie is already in the list
        if (movies.containsKey(movieNode.getName())) {
            movies.get(movieNode.getName()).addNeighbor(actorNode);
        } else {
        	movieNode.addNeighbor(actorNode);
            movies.put(movieNode.getName(), movieNode);
        }
    }
}
