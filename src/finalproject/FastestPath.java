package finalproject;

import finalproject.system.Tile;
import finalproject.system.TileType;
import finalproject.tiles.MetroTile;

public class FastestPath extends PathFindingService {
    //TODO level 6: find time prioritized path - DONE
    public FastestPath(Tile start) {
        super(start);
        g = new Graph(GraphTraversal.DFS(source));
        generateGraph();
    }

	@Override
	public void generateGraph() {
		// TODO Auto-generated method stub
        for(Tile t : GraphTraversal.DFS(source)){
            for(Tile o : t.adjacentTiles){
                if(o.isWalkable()){
                    if(t.getTileType() == TileType.Metro && o.getTileType() == TileType.Metro){
                        ((MetroTile) t).fixMetro(o);
                        g.addEdge(t, o, ((MetroTile) o).metroTimeCost);
                        g.addEdge(o, t, ((MetroTile) t).metroTimeCost);
                    }
                    else{
                        g.addEdge(t, o, o.timeCost);
                        g.addEdge(o, t, t.timeCost);
                    }
                }
            }
        }
	}

}
