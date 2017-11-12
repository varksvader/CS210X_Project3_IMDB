import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public abstract class IMDBGraph {

    static class ActorNode implements Node {

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

    static class MovieNode implements Node {

        private String name;
        private Collection<MovieNode> neighbors;

        public MovieNode(String name, Collection<? extends Node> neighbors) {
            this.name = name;
            this.neighbors = (Collection<MovieNode>) neighbors;
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

}
