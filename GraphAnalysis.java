import java.util.*;

/**
 * Class devoted to having static methods to work on any graph
 */
public class GraphAnalysis {



    public static <V,E> Graph<V,E> bfs(Graph<V,E> g, V source){
        if(!g.hasVertex(source)){
            System.out.println("Vertex not in Graph");
            return null;
        }

        Queue<V> toVisit = new LinkedList<>();
        HashSet<V> visited = new HashSet<>();
        Graph<V,E> treeRootV = new AdjacencyMapGraph<>();

        toVisit.add(source);
        visited.add(source);
        while(!toVisit.isEmpty()){
            V v1 =toVisit.poll();
            for(V v2 :g.outNeighbors(v1)){
                if(!visited.contains(v2)){
                    visited.add(v2);
                    toVisit.add(v2);
                   if(!treeRootV.hasVertex(v1)) treeRootV.insertVertex(v1);
                   if(!treeRootV.hasVertex(v2)) treeRootV.insertVertex(v2);
                   treeRootV.insertDirected(v2,v1,g.getLabel(v2,v1));
                  // treeRootV.insertDirected(v1,v2,null );
                   //hopefully should work, in order to have edges in both ways,
                    // but have a difference between them to tell which is the way to the target

                }
            }
        }
        return treeRootV;
    }
    public static <V,E> List<V> getPath(Graph<V,E> tree, V v){
        if(!tree.hasVertex(v)) {
            System.out.println("No path or spelling error");
            return null;
        }
        List<V> path = new ArrayList<>();

        Queue<V> toGo = new LinkedList<>();

        toGo.add(v);

        while(!toGo.isEmpty()){
            V v1 = toGo.poll();
            path.add(v1);
            if(tree.outNeighbors(v1)!=null) {//now redund?
                for (V v2 : tree.outNeighbors(v1)) {
                    if (tree.getLabel(v1, v2) != null) {
                        toGo.add(v2);
                    }
                }
            }
        }



        return path;
    }
    public static <V,E> Set<V> missingVertices(Graph<V,E> graph, Graph<V,E> subgraph){
        Set<V> missing = new HashSet<>();

        for(V v : graph.vertices()){
            if(!subgraph.hasVertex(v)){missing.add(v);}
        }

        return missing;
    }
    public static <V,E> double averageSeparation(Graph<V,E> tree, V root){

        return helper(0,tree,root)/tree.numVertices();
    }

    private static <V,E> double helper(double separation, Graph<V,E> tree, V current){

        double total = 0;

        for(V v : tree.inNeighbors(current)){
            total+=helper( separation+1,tree, v);
        }
        return total+separation;
    }
}
