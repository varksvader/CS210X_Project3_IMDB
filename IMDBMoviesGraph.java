import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class IMDBMoviesGraph extends IMDBGraph implements Graph {

    private Map<String, MovieNode> listOfMovies;

    public IMDBMoviesGraph(String actorsFilename, String actressesFilename) throws IOException {
        super(actorsFilename, actressesFilename);
        this.listOfMovies = movies;
        System.out.println(movies.size());
    }

    @Override
    public Collection<? extends Node> getNodes() {
        return new ArrayList<>(movies.values());
    }

    @Override
    public Node getNodeByName(String name) {
        return listOfMovies.get(name);
    }
}
