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
		final Map<Node, Integer> distance = new HashMap<Node, Integer>();
		Boolean found = false;
		// if either node does not exist, then shortestPath is null
		if (s == null || t == null) {
			return null;
		} else {
			toVisit.add(s);
			distance.put(s, 0);
			// Goes through toVisit and visits each individual node and adds
			// it to visited
			Node current;
			while (!(toVisit.isEmpty())) {
				current = toVisit.poll();
				visited.add(current);
				// stops search once t is found
				if (current.equals(t)) {
					found = true;
					break;
				}
				// if not continues by adding the neighbors of the node 
				// to toVisited
				for (Node neighbor : current.getNeighbors()) {
					if (!(toVisit.contains(neighbor)) && !(visited.contains(neighbor))) {
						toVisit.add(neighbor);
						distance.put(neighbor, distance.get(current) + 1);
					}
				}
			}
			// if no path is between existing nodes s and t return null
			if (!found) {
				return null;
			} else {
				// otherwise backtrack using distance to find shortestPath
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
	private List<Node> backTrackFrom(Node start, Node end, Stack<Node> path, Map<Node,Integer> distance) {
		final List<Node> shortestPath = new ArrayList<Node>();
		Node n;
		Node current = start;
		shortestPath.add(start);
		while (!path.isEmpty()) {
			n = path.pop();
			// checks the distance and adds the corresponding neighbor node
			if (current.getNeighbors().contains(n) && distance.get(n) + 1 == distance.get(current)) {
				shortestPath.add(n);
				current = n;
				// stop adding to shortestPath once s is found
				if (n.equals(end))
					break;
			}
		}
		Collections.reverse(shortestPath);
		return shortestPath;
	}
}
