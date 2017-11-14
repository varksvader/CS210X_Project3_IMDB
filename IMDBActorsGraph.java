import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class IMDBActorsGraph extends IMDBGraph implements Graph {

    private Map<String, ActorNode> listOfActors;

    public IMDBActorsGraph(String actorsFilename, String actressesFilename) throws IOException {
        super(actorsFilename, actressesFilename);
        this.listOfActors = actors;
    }

    @Override
    public Collection<? extends Node> getNodes() {
        return new ArrayList<>(actors.values());
    }

    @Override
    public Node getNodeByName(String name) {
        return listOfActors.get(name);
    }
}
