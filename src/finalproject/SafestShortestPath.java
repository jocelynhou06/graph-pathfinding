package finalproject;


import java.util.ArrayList;
import java.util.LinkedList;

import finalproject.system.Tile;
import finalproject.system.TileType;
import finalproject.tiles.MetroTile;

public class SafestShortestPath extends ShortestPath {
	public int health;
	public Graph costGraph;
	public Graph damageGraph;
	public Graph aggregatedGraph;

	//TODO level 8: finish class for finding the safest shortest path with given health constraint - DONE
	public SafestShortestPath(Tile start, int health) {
		super(start);
		this.health = health;
	}

	public void generateGraph() {
		// TODO Auto-generated method stub
		costGraph = new Graph(GraphTraversal.DFS(source));
		damageGraph = new Graph(GraphTraversal.DFS(source));
		aggregatedGraph = new Graph(GraphTraversal.DFS(source));

		for (Tile t : GraphTraversal.DFS(source)) {
			for (Tile o : t.adjacentTiles) {
				if (o.isWalkable()) {
					if (t.getTileType() == TileType.Metro && o.getTileType() == TileType.Metro) {
						((MetroTile) t).fixMetro(o);
						costGraph.addEdge(t, o, ((MetroTile) o).metroDistanceCost);
						costGraph.addEdge(o, t, ((MetroTile) t).metroDistanceCost);
						damageGraph.addEdge(t, o, o.damageCost);
						damageGraph.addEdge(o, t, t.damageCost);
						aggregatedGraph.addEdge(t, o, o.damageCost);
						aggregatedGraph.addEdge(o, t, t.damageCost);
					} else {
						costGraph.addEdge(t, o, o.distanceCost);
						costGraph.addEdge(o, t, t.distanceCost);
						damageGraph.addEdge(t, o, o.damageCost);
						damageGraph.addEdge(o, t, t.damageCost);
						aggregatedGraph.addEdge(t, o, o.damageCost);
						aggregatedGraph.addEdge(o, t, t.damageCost);
					}
				}
			}
		}
	}

	public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints){
		ArrayList<Tile> shortestPath = findingOptimalPath(costGraph, start, waypoints);
		if(damageGraph.computePathCost(shortestPath) < health){
			return shortestPath;
		}


		ArrayList<Tile> leastDamagePath = findingOptimalPath(damageGraph, start, waypoints);
		if(damageGraph.computePathCost(leastDamagePath) > health){
			return null;
		}


		while(true) {
			double multiplier = (costGraph.computePathCost(shortestPath) - costGraph.computePathCost(leastDamagePath)) / (damageGraph.computePathCost(leastDamagePath) - damageGraph.computePathCost(shortestPath));
			updateAggregatedGraph(multiplier);
			ArrayList<Tile> aggregatedPath = findingOptimalPath(aggregatedGraph, start, waypoints);

			if (aggregatedGraph.computePathCost(aggregatedPath) == aggregatedGraph.computePathCost(shortestPath)) {
				return leastDamagePath;
			}
			else if (damageGraph.computePathCost(aggregatedPath) <= health) {
				leastDamagePath = aggregatedPath;
			}
			else {
				shortestPath = aggregatedPath;
			}
		}
	}

	public ArrayList<Tile> findingOptimalPath(Graph assignedGraph, Tile start, LinkedList<Tile> waypoints){
		ArrayList<Tile> optimalPath = new ArrayList<>();
		Tile beginning = start;
		optimalPath.add(beginning);
		g = assignedGraph;

		for(Tile stopHere: waypoints){
			ArrayList<Tile> pathToWaypoint = findPath(beginning, stopHere);

			for(int i = 1; i < pathToWaypoint.size(); i++){
				optimalPath.add(pathToWaypoint.get(i));
			}

			beginning = stopHere;
		}

		ArrayList<Tile> last = findPath(beginning);
		for(int i = 1; i < last.size(); i++){
			optimalPath.add(last.get(i));
		}

		return optimalPath;
	}

	public void updateAggregatedGraph(double multiplier) {
		for (Tile t : GraphTraversal.DFS(source)) {
			for (Tile o : t.adjacentTiles) {
				if (o.isWalkable()) {
					aggregatedGraph.addEdge(t, o, (o.distanceCost + multiplier * o.damageCost));
					aggregatedGraph.addEdge(o, t, (t.distanceCost + multiplier * t.damageCost));
				}
			}
		}
	}

}
