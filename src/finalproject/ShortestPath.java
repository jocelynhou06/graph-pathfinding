package finalproject;

import finalproject.system.Tile;
import finalproject.system.TileType;
import finalproject.tiles.MetroTile;

public class ShortestPath extends PathFindingService {
    //TODO level 4: find distance prioritized path - DONE
    public ShortestPath(Tile start) {
        super(start);
        g = new Graph(GraphTraversal.DFS(source));
        generateGraph();
    }

	@Override
	public void generateGraph() {
		// TODO Auto-generated method stub

        for(Tile t : GraphTraversal.DFS(source)){
            for(Tile o : t.adjacentTiles){
                if (o.isWalkable()) {
                    if (t.getTileType() == TileType.Metro && o.getTileType() == TileType.Metro) {
                        ((MetroTile) t).fixMetro(o);
                        g.addEdge(t, o, ((MetroTile) o).metroDistanceCost);
                        g.addEdge(o, t, ((MetroTile) t).metroDistanceCost);
                    } else {
                        g.addEdge(t, o, o.distanceCost);
                        g.addEdge(o, t, t.distanceCost);
                    }
                }
            }
        }
	}
    
}