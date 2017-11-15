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
        }

        toVisit.add(s);
        pathStack.add(s);
        visited.add(s);

        Node u = null;
        while(!toVisit.isEmpty())
        {
            u = toVisit.poll();

            for(Node neighbor : u.getNeighbors())
            {
                if(!visited.contains(neighbor))
                {
                    toVisit.add(neighbor);
                    visited.add(neighbor);
                    pathStack.add(neighbor);
                    if(u.equals(t))
                        break;
                }
            }
        }
        
        //To find the path
        Node node = null;
        Node currentSrc = t;
        shortestPathList.add(t);
        while(!pathStack.isEmpty())
        {
            node = pathStack.pop();
            if(currentSrc.getNeighbors().contains(node))
            {
                shortestPathList.add(node);
                
                currentSrc = node;
                if(node.equals(s) /** == s**/)
                    break;
            }
        }
        Collections.reverse(shortestPathList);
        return shortestPathList;
     }
}
