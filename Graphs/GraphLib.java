import java.util.*;

/**
 * Library for graph analysis
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2016
 * 
 */
public class GraphLib {
	/**
	 * Takes a random walk from a vertex, up to a given number of steps
	 * So a 0-step path only includes start, while a 1-step path includes start and one of its out-neighbors,
	 * and a 2-step path includes start, an out-neighbor, and one of the out-neighbor's out-neighbors
	 * Stops earlier if no step can be taken (i.e., reach a vertex with no out-edge)
	 * @param g		graph to walk on
	 * @param start	initial vertex (assumed to be in graph)
	 * @param steps	max number of steps
	 * @return		a list of vertices starting with start, each with an edge to the sequentially next in the list;
	 * 			    null if start isn't in graph
	 */
	public static <V,E> List<V> randomWalk(Graph<V,E> g, V start, int steps) {
		// TODO: your code here

		if(!g.hasVertex(start)){return null;}

		ArrayList<V> output = new ArrayList<V>();
		output.add(start);

		V temp = start;
		for(int i = 0; i<steps; i++){
			if(g.outDegree(temp)>=1){
				double select = -1;
				V toAdd = null;
				for (V vert:g.outNeighbors(temp)){
					double ed = Math.random();
					if(select<ed){
						toAdd = vert;
						select = ed;
					}
				}
				output.add(toAdd);
				temp = toAdd;
			}
		}


		return output;
	}
	
	/**
	 * Orders vertices in decreasing order by their in-degree
	 * @param g		graph
	 * @return		list of vertices sorted by in-degree, decreasing (i.e., largest at index 0)
	 */
	public static <V,E> List<V> verticesByInDegree(Graph<V,E> g) {
		// TODO: your code here
		ArrayList<V> output = new ArrayList<V>();

		if(g==null||g.numVertices()==0){return null;}

		for(V vert : g.vertices()){
			int index = 0;
			while(index<output.size() && g.inDegree(output.get(index))>g.inDegree(vert)){
				index++;
			}
			if(index == output.size()){output.add(vert);}
			else{output.add(index, vert);}

		}

		return output;

	}
}
