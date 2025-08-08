// import org.bytedeco.javacv.FrameFilter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.*;


/**
 * Currently, this class successfully(?) stores the relationships between actors within an adjacency graph
 * Can delete the first two instance variables and their getters to save space once known it works,
 * as they only matter in the constructor
 */
public class BaconGame {

    private AdjacencyMapGraph<String, HashSet<String>> actorMovieGraph; //Adjacency Map to store all the relationships

    public BaconGame(String actorFilename, String movieFilename, String actorMovieFilename){

        String[] files = {actorFilename,movieFilename,actorMovieFilename};
        HashMap<Integer, String> actorIDMap =new HashMap<>();
        HashMap<Integer, String> movieIDMap = new HashMap<>();
        HashMap<Integer, ArrayList<Integer>> movieCast = new HashMap<>();
        actorMovieGraph = new AdjacencyMapGraph<>();
        BufferedReader reading;
        for (int i=0; i<3; i++) {
            try {
                reading = new BufferedReader(new FileReader(files[i]));
            } catch (Exception e) {
                System.out.println(e);
                return;
            }

            String line = "Will store all the lines";
            while (line != null) { // what does a buffered reader return at the end of a file?
                try {
                    line = reading.readLine();
                } catch (Exception e) {
                    line = null;
                }

                if (line != null) {
                    String[] iDthenName = line.split("\\|");
                   //System.out.println(iDthenName[0] + iDthenName[1]); //print out the array, for testing

                    //Now, add all of the names to the maps

                   if(i==0) actorIDMap.put(Integer.parseInt(iDthenName[0]), iDthenName[1]);
                   if(i==1)movieIDMap.put(Integer.parseInt(iDthenName[0]),iDthenName[1]);
                   if(i==2) {
                       Integer movID = Integer.parseInt(iDthenName[0]);
                       Integer actID = Integer.parseInt(iDthenName[1]);
                       if(!movieCast.containsKey(movID)){
                           movieCast.put(movID,new ArrayList<Integer>());
                       }
                       movieCast.get(movID).add(actID);
                   }


                }
            }
        }


//        //For testing
//        for (Integer i : movieCast.keySet()){
//            System.out.println(movieCast.get(i));
//        }

        //Now, put all of this stuff into the graph

        for (Integer movie: movieCast.keySet()){
            for(Integer actor1 : movieCast.get(movie)){
                String actor1Name = actorIDMap.get(actor1);
                String movieName = movieIDMap.get(movie);

                if(!actorMovieGraph.hasVertex(actor1Name)) {actorMovieGraph.insertVertex(actor1Name);}

                for(Integer actor2 : movieCast.get(movie)){
                    if(actor1!=actor2){
                        String actor2Name = actorIDMap.get(actor2);
                        if(!actorMovieGraph.hasVertex(actor2Name)){actorMovieGraph.insertVertex(actor2Name);}
                        if(!actorMovieGraph.hasEdge(actor1Name,actor2Name)) {
                            actorMovieGraph.insertUndirected(actor1Name, actor2Name, new HashSet<>());
                        }
                            actorMovieGraph.getLabel(actor1Name,actor2Name).add(movieName);//no dupes, no check

                    }
                }

            }
        }
    }


    public AdjacencyMapGraph<String, HashSet<String>> getActorMovieGraph(){return actorMovieGraph;}


    public static void main(String[] args) {
        String actorFile = "inputs/BaconData/actors.txt";
        String movieFile = "inputs/BaconData/movies.txt";
        String movieActorsFile = "inputs/BaconData/movie-actors.txt";
        BaconGame BakeOn = new BaconGame(actorFile, movieFile,movieActorsFile);


       // System.out.println(BakeOn.getActorMovieGraph());

        //System.out.println();
        Graph<String, HashSet<String>> tree = GraphAnalysis.bfs(BakeOn.getActorMovieGraph(), "Kevin Bacon");

       // System.out.println(tree);
        List<String> path = (ArrayList<String>) GraphAnalysis.getPath(tree,"ThÃ\u0088ophile SowiÃ\u0088");

        for(int i=0; i<path.size()-1;i++)
        {
            System.out.println(path.get(i)+" appeared in:"+BakeOn.getActorMovieGraph().getLabel(path.get(i+1),path.get(i))+"with:" +path.get(i+1));
        }
       // System.out.println(GraphAnalysis.averageSeparation(tree, "Kevin Bacon"));

    }



}
