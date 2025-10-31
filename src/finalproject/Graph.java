package finalproject;

import java.util.ArrayList;
import java.util.HashSet;

import finalproject.system.Tile;
import finalproject.system.TileType;
import finalproject.tiles.*;

public class Graph {
	// TODO level 2: Add fields that can help you implement this data type - DONE
    ArrayList<Tile> vertices;
    ArrayList<Edge> allEdges = new ArrayList<>();
    //vertice holding weight from root to there

    // TODO level 2: initialize and assign all variables inside the constructor - DONE
	public Graph(ArrayList<Tile> vertices) {
		this.vertices = vertices;
	}
	
    // TODO level 2: add an edge to the graph - DONE
    public void addEdge(Tile origin, Tile destination, double weight){
        boolean update = false;

        for(Edge e : allEdges){
            if(e.origin == origin && e.destination == destination){
                e.weight = weight;
                update = true;
            }
        }

        if(!update){
            Edge newEdge = new Edge(origin, destination, weight);
            allEdges.add(newEdge);
        }
    }
    
    // TODO level 2: return a list of all edges in the graph - DONE
	public ArrayList<Edge> getAllEdges() {
		return allEdges;
	}
  
	// TODO level 2: return list of tiles adjacent to t - DONE
	public ArrayList<Tile> getNeighbors(Tile t) {
        ArrayList<Tile> returning = new ArrayList<>();

        for(Edge e : allEdges){
            if(e.origin == t){
                returning.add(e.destination);
            }
        }

    	return returning;
    }
	
	// TODO level 2: return total cost for the input path
    // Edge cases?
	public double computePathCost(ArrayList<Tile> path) {

        double totalCost = 0;

        for(int i = 0; i < path.size() - 1; i++){
            for(Edge e : allEdges){
                if(e.origin == path.get(i) && e.destination == path.get(i+1)){
                    totalCost += e.weight;
                }
            }
        }

		return totalCost;
	}
	
   
    public static class Edge{
    	Tile origin;
    	Tile destination;
    	double weight;

        // TODO level 2: initialize appropriate fields - DONE;
        public Edge(Tile s, Tile d, double cost){
        	origin = s;
            destination = d;
            weight = cost;
        }
        
        // TODO level 2: getter function 1 - DONE
        public Tile getStart(){
            return origin;
        }

        
        // TODO level 2: getter function 2 - DONE
        public Tile getEnd() {
            return destination;
        }
        
    }
    
}