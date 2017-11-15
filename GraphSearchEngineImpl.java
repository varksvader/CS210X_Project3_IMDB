import java.util.*;

public class GraphSearchEngineImpl implements GraphSearchEngine {

	//Implied constructor

	/**
	 * Returns the list of nodes from node s to node t or null if 
	 * no such path exists
	 * @param s the requested starting node
	 * @param t the requested ending node
	 * @return the list of nodes from node s to node t 
	 * or null if no such path exists
	 */
    @Override
    public List<Node> findShortestPath(Node s, Node t) {
		final Queue<Node> toVisit = new LinkedList<Node>();
		final Stack<Node> visited = new Stack<Node>();
		final HashMap<Node, Integer> distance = new HashMap<Node, Integer>();
		Boolean found = false;
		if (s == null || t == null) {
            return null;
        } else {
        	toVisit.add(s);
    		distance.put(s, 0);
    		Node current;
    		while (!(toVisit.isEmpty())) {
    			current = toVisit.poll();
    			visited.add(current);
    			if (current.equals(t)) {
    				found = true;
    				break;
    			}
    			for (Node neighbor : current.getNeighbors()) {
    				if (!(toVisit.contains(neighbor)) && !(visited.contains(neighbor))) {
    					toVisit.add(neighbor);
    					distance.put(neighbor, distance.get(current) + 1);
    				}
    			}
    		}
    		if (!found) {
    			return null;
    		} else {
    			return backTrackFrom(t, s, visited, distance);
    		}
        }
	}

    
    /**
     * Uses backtracking to return the shortest possible path from 
     * the requested starting node to the requested ending node
     * @param start the requested ending node
     * @param end the requested starting node
     * @param path the stack of nodes accumulated
     * @param distance the distance between each of the nodes
     * @return the shortest possible path from the requested starting node 
     * to the requested ending node
     */
    private List<Node> backTrackFrom(Node start, Node end, Stack<Node> path, HashMap<Node,Integer> distance) {
    	final ArrayList<Node> shortestPath = new ArrayList<Node>();
    	Node n;
		Node current = start;
		shortestPath.add(start);
		while (!path.isEmpty()) {
			n = path.pop();
			if (current.getNeighbors().contains(n) && distance.get(n) + 1 == distance.get(current)) {
				shortestPath.add(n);
				current = n;
				if (n.equals(end))
					break;
			}
		}
		Collections.reverse(shortestPath);
		return shortestPath;
    }
}
