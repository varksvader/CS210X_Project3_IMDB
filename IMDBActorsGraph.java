import com.sun.xml.internal.ws.api.ha.StickyFeature;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class IMDBActorsGraph extends IMDBGraph implements Graph {

    public IMDBActorsGraph(String actorsFilename, String actressesFilename) throws IOException {
        super(actorsFilename, actressesFilename);
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
