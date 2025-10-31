package finalproject;

import finalproject.system.Tile;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public abstract class PathFindingService {
	Tile source;
	Graph g;
	
	public PathFindingService(Tile start) {
    	this.source = start;
    }

	public abstract void generateGraph();
    
    //TODO level 4: Implement basic dijkstra's algorithm to find a path to the final unknown destination - DONE
    public ArrayList<Tile> findPath(Tile startNode) {

        initialize(g.vertices, startNode);

        ArrayList<Tile> returning = new ArrayList<>();
        TilePriorityQ pq = new TilePriorityQ(g.vertices);
        Tile destination = null;

        while(!pq.priorityQueue.isEmpty()){
            Tile beginning = pq.removeMin();

            if(beginning.isDestination == true){
                destination = beginning;
            }

            for(Tile ending : g.getNeighbors(beginning)){
                for(Graph.Edge w : g.allEdges){
                    if(w.origin == beginning && w.destination == ending){
                        double oldCost = ending.costEstimate;
                        relax(beginning, ending, w);

                        if(oldCost > ending.costEstimate){
                            pq.updateKeys(ending, beginning, ending.costEstimate);
                        }

                    }
                }
            }

        }

        while(destination.predecessor != null){
            returning.add(0, destination);
            destination = destination.predecessor;
        }
        returning.add(0, destination);

    	return returning;
    }

    private void initialize(ArrayList<Tile> vertices, Tile source){
        for(Tile t : vertices){
            t.costEstimate = Integer.MAX_VALUE;
            t.predecessor = null;
        }
        source.costEstimate = 0;
    }

    private void relax(Tile u, Tile v, Graph.Edge w){
        if(v.costEstimate > u.costEstimate + w.weight){
            v.costEstimate = u.costEstimate + w.weight;
            v.predecessor = u;
        }
    }
    
    //TODO level 5: Implement basic dijkstra's algorithm to path find to a known destination - DONE
    public ArrayList<Tile> findPath(Tile start, Tile end) {
        ArrayList<Tile> returning = new ArrayList<>();
        findPath(start);

        while(end.predecessor != null){
            returning.add(0, end);
            end = end.predecessor;
        }
        returning.add(0, end);

        return returning;
    }

    //TODO level 5: Implement basic dijkstra's algorithm to path find to the final destination passing through given waypoints - DONE
    public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints){
        ArrayList<Tile> returning = new ArrayList<>();
        Tile beginning = start;
        returning.add(beginning);

        for(Tile stopHere: waypoints){
            ArrayList<Tile> pathToWaypoint = findPath(beginning, stopHere);

            for(int i = 1; i < pathToWaypoint.size(); i++){
                returning.add(pathToWaypoint.get(i));
            }

            beginning = stopHere;
        }

        ArrayList<Tile> last = findPath(beginning);
        for(int i = 1; i < last.size(); i++){
            returning.add(last.get(i));
        }

    	return returning;
    }
        
}

