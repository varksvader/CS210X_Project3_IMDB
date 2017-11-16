import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import java.io.*;

/**
 * Code to test Project 3; you should definitely add more tests!
 */
public class GraphPartialTester {
	Graph actorsGraph, moviesGraph;
	GraphSearchEngine searchEngine;

	@Test(timeout=5000)
	/**
	 * Verifies that there is a shortest path between actor1 and actor2.
	 */
	public void findShortestPath1 () {
		final Node actor1 = actorsGraph.getNodeByName("A'Dair, Michelle");
		final Node actor2 = actorsGraph.getNodeByName("A'Dair, Renee");
		final List<Node> shortestPath = searchEngine.findShortestPath(actor1, actor2);
		final List<Node> correct = new ArrayList<Node>();
		correct.add(actor1);
		correct.add(moviesGraph.getNodeByName("Sierra Spirits (2009)"));
		correct.add(actor2);
		assertEquals(correct, shortestPath);
	}
	
	@Test(timeout = 5000)
	/**
	 * Verifies that there is no shortest path between actor1 and
	 * actor2.
	 */
	public void findShortestPath2() {
		final Node actor1 = actorsGraph.getNodeByName("Brad Pitt");
		final Node actor2 = actorsGraph.getNodeByName("Jonah Hill");
		final List<Node> shortestPath = searchEngine.findShortestPath(actor1, actor2);
		assertNull(shortestPath); // there is no path between these people
		
	}
	
	@Test(timeout = 5000)
	/**
	 * Verifies that there is a shortest path between the same actor.
	 */
	public void findShortestPath3() {
		final Node actor1 = actorsGraph.getNodeByName("A'Dair, Michelle");
		final Node actor2 = actorsGraph.getNodeByName("A'Dair, Michelle");
		final List<Node> shortestPath = searchEngine.findShortestPath(actor1, actor2);
		final List<Node> correct = new ArrayList<>();
		correct.add(actor1);
		assertEquals(correct, shortestPath);
	}

	@Test(timeout = 5000)
	/**
	 * Verifies that there is a shortest path between a specific actor and
	 * actress.
	 */
	public void findShortestPath4() {
		final Node actor1 = actorsGraph.getNodeByName("Actor1");
		final Node actor2 = actorsGraph.getNodeByName("Actor5");
		final List<Node> shortestPath = searchEngine.findShortestPath(actor1, actor2);
		final List<Node> correct = new ArrayList<>();
		correct.add(actor1);
		correct.add(moviesGraph.getNodeByName("Movie1 (2002)"));
		correct.add(actorsGraph.getNodeByName("Actress5"));
		correct.add(moviesGraph.getNodeByName("Movie4 (2002)"));
		correct.add(actor2);
        assertEquals(correct, shortestPath);
	}
	
	@Test(timeout = 5000)
	/**
	 * Verifies that there is a shortest path between movie1 and
	 * movie2.
	 */
	public void findShortestPath5() {
		final Node movie1 = moviesGraph.getNodeByName("Movie5 (2002)");
		final Node movie2 = moviesGraph.getNodeByName("Movie1 (2002)");
		final List<Node> shortestPath = searchEngine.findShortestPath(movie1, movie2);
		final List<Node> correct = new ArrayList<>();
		correct.add(movie1);
		correct.add(actorsGraph.getNodeByName("Actor5"));
		correct.add(moviesGraph.getNodeByName("Movie4 (2002)"));
		correct.add(actorsGraph.getNodeByName("Actor4"));
		correct.add(movie2);
		System.out.println(correct);
		System.out.println(shortestPath);
		assertEquals(correct, shortestPath);
	}
	
	@Test(timeout = 5000)
	/**
	 * Verifies that there is a shortest path between a specific actor and
	 * actress.
	 */
	public void findShortestPath6() {
		final Node actor1 = actorsGraph.getNodeByName("Actor1");
		final Node actor2 = actorsGraph.getNodeByName("Actor6");
		final List<Node> shortestPath = searchEngine.findShortestPath(actor1, actor2);
		final List<Node> correct = new ArrayList<>();
		correct.add(actor1);
		correct.add(moviesGraph.getNodeByName("Movie1 (2002)"));
		correct.add(actorsGraph.getNodeByName("Actress5"));
		correct.add(moviesGraph.getNodeByName("Movie4 (2002)"));
		correct.add(actor2);
		assertEquals(correct, shortestPath);
	}

	@Test(timeout = 5000)
	/**
	 * Verifies that there is a shortest path between a specific actor and
	 * actress.
	 */
	public void findShortestPath7() {
		final Node actor1 = actorsGraph.getNodeByName("Actor1");
		final Node actor2 = actorsGraph.getNodeByName("Actor7");
		final List<Node> shortestPath = searchEngine.findShortestPath(actor1, actor2);
		final List<Node> correct = new ArrayList<>();
		correct.add(actor1);
		correct.add(moviesGraph.getNodeByName("Movie1 (2002)"));
		correct.add(actorsGraph.getNodeByName("Actress5"));
		correct.add(moviesGraph.getNodeByName("Movie4 (2002)"));
		correct.add(actorsGraph.getNodeByName("Actor5"));
		correct.add(moviesGraph.getNodeByName("Movie5 (2002)"));
		correct.add(actor2);

		assertEquals(correct, shortestPath);
	}

	@Test(timeout = 5000)
	/**
	 * Verifies that the shortest path is returned if there are multiple paths
	 * Uses actors and actresses
	 */
	public void findShortestPath8() {
		final Node actor1 = actorsGraph.getNodeByName("Actor1");
		final Node actor2 = actorsGraph.getNodeByName("Actress4");
		final List<Node> shortestPath = searchEngine.findShortestPath(actor1, actor2);
		final List<Node> correct = new ArrayList<>();
		correct.add(actor1);
		correct.add(moviesGraph.getNodeByName("Movie1 (2002)"));
		correct.add(actor2);
		assertEquals(correct, shortestPath);
	}


	@Test
	/**
	 * Verifies that an actor that has only starred in TV shows in not present in the actors list
	 */
	public void testActorOnlyInTVShows() {
		assertNull(actorsGraph.getNodeByName("$, Chaw"));
	}



	@Before
	/**
	 * Instantiates the actors and movies graphs
	 */
	public void setUp () throws IOException {
		actorsGraph = new IMDBActorsGraph("actors_first_10000_lines.list", "actresses_first_10000_lines.list");
		moviesGraph = new IMDBMoviesGraph("actors_first_10000_lines.list", "actresses_first_10000_lines.list");
		searchEngine = new GraphSearchEngineImpl();
	}

	@Test
	/**
	 * Just verifies that the graphs could be instantiated without crashing.
	 */
	public void finishedLoading () {
		assertTrue(true);
		// Yay! We didn't crash
	}

	@Test
	/**
	 * Verifies that a specific movie has been parsed.
	 */
	public void testSpecificMovie () {
		testFindNode(moviesGraph, "Mixing Nia (1998)");
	}

	@Test
	/**
	 * Verifies that a specific actress has been parsed.
	 */
	public void testSpecificActress () {
		testFindNode(actorsGraph, "A. Ross, Marissa");
	}

	/**
	 * Verifies that the specific graph contains a node with the specified name
	 * @param graph the Graph to search for the node
	 * @param name the name of the Node
	 */
	private static void testFindNode (Graph graph, String name) {
		final Collection<? extends Node> nodes = graph.getNodes();
		boolean found = false;
		for (Node node : nodes) {
			if (node.getName().trim().equals(name)) {
				found = true;
			}
		}
		assertTrue(found);
	}
}
