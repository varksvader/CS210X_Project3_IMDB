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
        List<Node> shortestPathList = new ArrayList<Node>();
        List<Node> visited = new ArrayList<Node>();
        Queue<Node> toVisit = new LinkedList<Node>();
        Stack<Node> pathStack = new Stack<Node>();
        if (s == null || t == null) {
            return null;
        } else if (s.equals(t)) {
            shortestPathList.add(s);
            return shortestPathList;
        } else {
        	toVisit.add(s);
            pathStack.add(s);
            visited.add(s);
            Node currentNode = null;
            while(!toVisit.isEmpty())
            {
                currentNode = toVisit.poll();
                for(Node neighbor : currentNode.getNeighbors())
                {
                    if(!visited.contains(neighbor))
                    {
                        toVisit.add(neighbor);
                        visited.add(neighbor);
                        pathStack.add(neighbor);
                        if(currentNode.equals(t)) {
                        	break;
                        }
                    }
                }
            }
            return backTrackFrom(t, s, pathStack);
        }
    }
    
    /**
     * Uses backtracking to return the shortest possible path from 
     * the requested starting node to the requested ending node
     * @param start the requested ending node
     * @param end the requested starting node
     * @param path the stack of nodes accumulated
     * @return the shortest possible path from the requested starting node 
     * to the requested ending node
     */
    private List<Node> backTrackFrom(Node start, Node end, Stack<Node> path) {
    	List<Node> shortestPathList = new ArrayList<Node>();
    	Node nextNode = null;
        Node recent = start;
        shortestPathList.add(start);
        while(!path.isEmpty())
        {
            nextNode = path.pop();
            if(recent.getNeighbors().contains(nextNode))
            {
                shortestPathList.add(nextNode);
                recent = nextNode;
                if(nextNode.equals(end)) {
                	break;
                }
            }
        }
        Collections.reverse(shortestPathList);
        return shortestPathList;
    }
}
