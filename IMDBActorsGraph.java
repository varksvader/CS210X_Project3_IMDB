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
        actors = new ArrayList<>();
    }

    private void parseData() {
        
    }


    @Override
    public Collection<? extends Node> getNodes() {
        return null;
    }

    @Override
    public Node getNodeByName(String name) {
        return null;
    }
}
