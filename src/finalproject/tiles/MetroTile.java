package finalproject.tiles;

import finalproject.system.Tile;
import finalproject.system.TileType;

public class MetroTile extends Tile {
	public double metroTimeCost = 100;
	public double metroDistanceCost = 100;
	public double metroCommuteFactor = 0.2;

    public MetroTile() {
        super(1, 1, 2);
        this.type = TileType.Metro;
    }

    public void fixMetro(Tile node){
        if(node.getTileType() == this.getTileType()){
            int md = Math.abs(this.xCoord - node.xCoord) + Math.abs(this.yCoord - node.yCoord);
            metroTimeCost = md * metroCommuteFactor;
            metroDistanceCost = md / metroCommuteFactor;
        }
    }
}