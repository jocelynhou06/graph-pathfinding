package finalproject;

import java.util.ArrayList;
import java.util.Arrays;


import finalproject.system.Tile;
import finalproject.tiles.DesertTile;

public class TilePriorityQ {
	// TODO level 3: Add fields that can help you implement this data type - DONE
	ArrayList<Tile> priorityQueue = new ArrayList<>();

	// TODO level 3: implement the constructor for the priority queue - DONE
	public TilePriorityQ (ArrayList<Tile> vertices) {
		buildHeap(vertices);
	}

	private void buildHeap(ArrayList<Tile> vertices){
		for(Tile t : vertices){
			priorityQueue.add(t);
		}
		for(int i = priorityQueue.size()/2 - 1; i >= 0; i--){
			downHeap(i, priorityQueue.size() - 1);
		}
	}

	private void downHeap(int startIndex, int maxIndex){
		int i = startIndex;

		while (2*i + 1 <= maxIndex) {
			int child = 2*i + 1;

			if (child + 1 <= maxIndex) {
				if(priorityQueue.get(child + 1).costEstimate < priorityQueue.get(child).costEstimate){
					child++;
				}
			}

			if (priorityQueue.get(child).costEstimate < priorityQueue.get(i).costEstimate) {
				swapElements(i, child);
				i = child;
			}
			else {
				break;
			}
		}

	}

	private void swapElements(int i, int j){
		Tile temp = priorityQueue.get(i);
		priorityQueue.set(i, priorityQueue.get(j));
		priorityQueue.set(j, temp);
	}
	
	// TODO level 3: implement remove min as seen in class - DONE
	public Tile removeMin() {
		Tile tempElement = priorityQueue.get(0);
		Tile last = priorityQueue.remove(priorityQueue.size() - 1);

		if(!priorityQueue.isEmpty()) {
			priorityQueue.set(0, last);
			downHeap(0, priorityQueue.size() - 1);
		}

		return tempElement;
	}
	
	// TODO level 3: implement updateKeys as described in the pdf - DONE
	public void updateKeys(Tile t, Tile newPred, double newEstimate) {
		int i = priorityQueue.indexOf(t);

		if(i > -1){
			double oldEstimate = priorityQueue.get(i).costEstimate;
			t.predecessor = newPred;
			t.costEstimate = newEstimate;

			if(newEstimate > oldEstimate){
				downHeap(i, priorityQueue.size() - 1);
			}
			else{
				upHeap(i);
			}
		}
	}
	private void upHeap(int i) {
		while (i > 0) {
			int parent = (i - 1) / 2;
			if (priorityQueue.get(i).costEstimate < priorityQueue.get(parent).costEstimate) {
				swapElements(i, parent);
				i = parent;
			} else {
				break;
			}
		}
	}

	public static void main (String[] args){
		Tile a = new DesertTile();
		Tile b = new DesertTile();
		Tile c = new DesertTile();
		Tile d = new DesertTile();
		Tile e = new DesertTile();

		a.costEstimate = 3;
		b.costEstimate = 5;
		c.costEstimate = 4;
		d.costEstimate = 2;
		e.costEstimate = 1;

		ArrayList<Tile> input = new ArrayList<>();
		input.add(a);
		input.add(b);
		input.add(c);
		input.add(d);
		input.add(e);

		TilePriorityQ test = new TilePriorityQ(input);
		for(Tile t : test.priorityQueue){
			System.out.println(t.costEstimate);
		}
	}
}
