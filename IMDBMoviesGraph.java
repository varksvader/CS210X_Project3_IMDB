import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class IMDBMoviesGraph extends IMDBGraph implements Graph {

    final private Map<String, MovieNode> _listOfMovies;

    public IMDBMoviesGraph(String actorsFilename, String actressesFilename) throws IOException {
        super(actorsFilename, actressesFilename);
        _listOfMovies = movies;
        System.out.println(movies.size());
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
