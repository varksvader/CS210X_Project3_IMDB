import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class IMDBActorsGraph extends IMDBGraph implements Graph {

    final private Map<String, ActorNode> _listOfActors;

    /**
     * Constructor
     * @param actorsFilename the filename of the actors
     * @param actressesFilename the filename of the actresses
     * @throws IOException if the file is not found
     */
    public IMDBActorsGraph(String actorsFilename, String actressesFilename) throws IOException {
        super(actorsFilename, actressesFilename);
        _listOfActors = actors;
    }

    /**
	 * Returns a collection of all the nodes in the graph
	 * @return a collection of all the nodes in the graph
	 */
    @Override
    public Collection<ActorNode> getNodes() {
        return new ArrayList<>(actors.values());
    }
    
    /**
	 * Returns the node of the specified name or null if no such node exists
	 * @param name the name of the specified node
	 * @return the node associated with the specified name or null
	 * if no such node exists.
	 */
    @Override
    public ActorNode getNodeByName(String name) {
        return _listOfActors.get(name);
    }
}
