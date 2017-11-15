import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class IMDBMoviesGraph extends IMDBGraph implements Graph {

    final private Map<String, MovieNode> _listOfMovies;

    /**
     * Constructor
     * @param actorsFilename the filename of the actors
     * @param actressesFilename the filename of the actresses
     * @throws IOException
     */
    public IMDBMoviesGraph(String actorsFilename, String actressesFilename) throws IOException {
        super(actorsFilename, actressesFilename);
        _listOfMovies = movies;
    }

    /**
	 * Returns a collection of all the nodes in the graph
	 * @return a collection of all the nodes in the graph
	 */
    @Override
    public Collection<MovieNode> getNodes() {
        return new ArrayList<>(movies.values());
    }

    /**
	 * Returns the node of the specified name or null if no such node exists
	 * @param name the name of the specified node
	 * @return the node associated with the specified name or null
	 * if no such node exists.
	 */
    @Override
    public MovieNode getNodeByName(String name) {
        return _listOfMovies.get(name);
    }
}
