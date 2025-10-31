package finalproject;

import finalproject.system.Tile;
import finalproject.tiles.DesertTile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class GraphTraversal
{
	//TODO level 1: implement BFS traversal starting from s - DONE?
	public static ArrayList<Tile> BFS(Tile s) {
		ArrayList<Tile> returning = new ArrayList<>();
		LinkedList<Tile> temp = new LinkedList<>();
		HashSet<Tile> alrVisited = new HashSet<>();

		returning.add(s);
		temp.add(s);
		alrVisited.add(s);

		while(temp.size() != 0){
			Tile holder = temp.removeFirst();
			ArrayList<Tile> adjArray = holder.adjacentTiles;

			for(int i = 0; i < adjArray.size(); i++){
				if(adjArray.get(i).isWalkable()){
					if(!alrVisited.contains(adjArray.get(i))) {
						returning.add(adjArray.get(i));
						temp.add(adjArray.get(i));
						alrVisited.add(adjArray.get(i));
					}
				}
			}

		}

		return returning;
	}


	//TODO level 1: implement DFS traversal starting from s - Done
	public static ArrayList<Tile> DFS(Tile s) {

		ArrayList<Tile> returning = new ArrayList<>();
		LinkedList<Tile> temp = new LinkedList<>();
		HashSet<Tile> alrVisited = new HashSet<>();

		temp.add(s);
		alrVisited.add(s);

		while(temp.size() != 0){
			Tile holder = temp.removeLast();
			returning.add(holder);
			ArrayList<Tile> adjArray = holder.adjacentTiles;

			for(int i = 0; i < adjArray.size(); i++){
				if(adjArray.get(i).isWalkable()){
					if(!alrVisited.contains(adjArray.get(i))) {
						temp.add(adjArray.get(i));
						alrVisited.add(adjArray.get(i));
					}
				}
			}

		}

		return returning;
	}
}  