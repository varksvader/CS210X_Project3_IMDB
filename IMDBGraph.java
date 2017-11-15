import java.io.File;
import java.io.IOException;
import java.util.*;

public class IMDBGraph {
	
	protected static class IMDBNode implements Node {

        final protected String _name;
        final protected Collection<IMDBNode> _neighbors;

        /**
         * Constructor
         * @param name the name of the node
         */
        public IMDBNode(String name) {
            _name = name;
            _neighbors = new ArrayList<>();
        }

        /**
         * Adds n as a neighbor to the current node
         * @param n the node
         */
        protected void addNeighbor(IMDBNode n) {
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
        
        /**
         * Overrides original equals only to compare names of the node
	 * @param o an object to compare to the called object
         */
        @Override
        public boolean equals(Object o) {
    		return (((Node) o).getName().equals(_name));
    	}
    }

    final protected static class ActorNode extends IMDBNode {

        /**
         * Constructor
         * @param name the name of the actor
         */
        public ActorNode(String name) {
            super(name);
        }
    }

    final protected static class MovieNode extends IMDBNode {

        /**
         * Constructor
         * @param name the name of the movie
         */
        public MovieNode(String name) {
            super(name);
        }
    }

    final protected Map<String, ActorNode> actors;
    final protected Map<String, MovieNode> movies;

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
            //Skip copyright info
        	if (!copyrightInfoDone) {
                if (line.equals("----			------")) {
                    copyrightInfoDone = true;
                }
                continue;
            }
        	//Skip bottom of file
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
                final String firstMovie = getMovieAtLine(line);
                newMovie = new MovieNode(firstMovie);
                actors.get(newActor.getName()).addNeighbor(newMovie);
                addMovies(newMovie, newActor);
            } else {
                if (line.contains("(TV)") || line.contains("\"")) {
                    continue;
                }
                if (!line.isEmpty()) {
                    final String movie = getMovieAtLine(line);
                    newMovie = new MovieNode(movie);
                    addMovies(newMovie, newActor);
                    actors.get(newActor.getName()).addNeighbor(newMovie);
                }
            }
        }
        checkIfActorHasMovies(newActor);
    }

    /**
     * Extracts the movie title from the given line
     * @param line The line of the data file
     * @return the movie title
     */
    private static String getMovieAtLine(String line) {
        System.out.println(line);
        return line.substring(line.lastIndexOf("\t") + 1, line.lastIndexOf(")") + 1);
    }

    /**
     * If an actor does not have any movies (excluding TV movies), 
     * he/she is not included in the graph
     * @param a the actor node
     */
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
