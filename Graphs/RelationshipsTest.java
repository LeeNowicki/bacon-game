/**
 * Test program for graph interface
 */
public class RelationshipsTest {
	public static void main(String [] args) {
		Graph<String, String> relationships = new AdjacencyMapGraph<String, String>();

		relationships.insertVertex("Alice");
		relationships.insertVertex("Bob");
		relationships.insertVertex("Charlie");
		relationships.insertVertex("Dartmouth");
		relationships.insertVertex("Elvis");
		relationships.insertDirected("Alice", "Dartmouth", "follower");
		relationships.insertDirected("Bob", "Dartmouth", "follower");
		relationships.insertDirected("Charlie", "Dartmouth", "follower");
		relationships.insertDirected("Elvis", "Dartmouth", "follower");
		relationships.insertUndirected("Alice", "Bob", "friend");
		relationships.insertDirected("Alice", "Elvis", "friend"); // not symmetric!
		relationships.insertDirected("Charlie", "Elvis", "follower");

		System.out.println("The graph:");
		System.out.println(relationships);

		System.out.println("\nLinks to Dartmouth = " + relationships.inDegree("Dartmouth"));

		System.out.println("\nLinks from Alice:");
		for (String to : relationships.outNeighbors("Alice"))
			System.out.println(to + " ("+relationships.getLabel("Alice", to)+")");

		System.out.println("\nLinks to Dartmouth:");
		for (String from : relationships.inNeighbors("Dartmouth"))
			System.out.println(from + " ("+relationships.getLabel(from, "Dartmouth")+")");

		System.out.println("\nElvis has left the building");
		relationships.removeVertex("Elvis");
		System.out.println("\nLinks from Alice:");
		for (String to : relationships.outNeighbors("Alice"))
			System.out.println(to + " ("+relationships.getLabel("Alice", to)+")");

		System.out.println("\nAlice & Charlie work together");
		relationships.insertUndirected("Alice", "Charlie", "co-worker");
		System.out.println("\nLinks from Alice:");
		for (String to : relationships.outNeighbors("Alice"))
			System.out.println(to + " ("+relationships.getLabel("Alice", to)+")");
		System.out.println("\nLinks from Charlie:");
		for (String to : relationships.outNeighbors("Charlie"))
			System.out.println(to + " ("+relationships.getLabel("Charlie", to)+")");
		
		System.out.println("\nAlice unfrieds Bob");
		relationships.removeDirected("Alice", "Bob");
		System.out.println("and Charlie gets fired");
		relationships.removeUndirected("Alice", "Charlie");
		System.out.println("\nLinks from Alice:");
		for (String to : relationships.outNeighbors("Alice"))
			System.out.println(to + " ("+relationships.getLabel("Alice", to)+")");
		
		System.out.println("\nThe final graph:");
		System.out.println(relationships);


		//Testing for SA-7
		// Graph represents { A->B, A->C, A->D, A->E, B->A, B->C, C->A, C->B, C->D, E->B, E->C }

		Graph<Character, String> test = new AdjacencyMapGraph<>();
		test.insertVertex('A');
		test.insertVertex('B');
		test.insertVertex('C');
		test.insertVertex('D');
		test.insertVertex('E');

		test.insertDirected('A', 'B', "Believes is the chosen one");
		test.insertDirected('A', 'C', "Watching archenemy");
		test.insertDirected('A', 'D', "Waiting for another prophecy");
		test.insertDirected('A', 'E', "Wary: Still unsure");
		test.insertDirected('B', 'A', "Think he's a weird merchant with an odd smell");
		test.insertDirected('B', 'C', "Old friends. Utmost trust.");
		test.insertDirected('C', 'A', "Thinks he's a fool. Too old to be a mentor to B, and too weak.");
		test.insertDirected('C', 'B', "Unfortunate, but willing to sacrifice for personal gain.");
		test.insertDirected('C', 'D', "Knows of existence, unsure of identity.");
		test.insertDirected('E', 'B', "Thinks they show signs of something greater. Watching, protecting.");
		test.insertDirected('E', 'C', "Believes seems too trustworthy. Withholding judgement.");

		for(int i=0; i<20; i++) {
			System.out.println(GraphLib.randomWalk(test, 'A', 30));
		}
		System.out.println(GraphLib.verticesByInDegree(test));

	}
}
