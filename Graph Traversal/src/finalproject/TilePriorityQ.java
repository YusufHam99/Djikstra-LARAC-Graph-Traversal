package finalproject;

import java.util.ArrayList;
import java.util.Arrays;


import finalproject.system.Tile;

public class TilePriorityQ {
	// TODO level 3: Add fields that can help you implement this data type
	private ArrayList<Tile> heap;

	// TODO level 3: implement the constructor for the priority queue
	public TilePriorityQ (ArrayList<Tile> vertices) {
		this.heap = new ArrayList<>(vertices);
		buildMinHeap();
	}
	
	// TODO level 3: implement remove min as seen in class
	public Tile removeMin() {
		Tile minTile = heap.get(0);
		int lastIndex = heap.size() - 1;
		heap.set(0, heap.get(lastIndex));
		heap.remove(lastIndex);
		minHeapify(0);
		return minTile;
	}
	
	// TODO level 3: implement updateKeys as described in the pdf
	public void updateKeys(Tile t, Tile newPred, double newEstimate) {
		int index = heap.indexOf(t);
		if (index != -1) {
			t.predecessor = newPred;
			t.costEstimate = newEstimate;

			int parentIndex = parent(index);
			if (index > 0 && heap.get(parentIndex).costEstimate > heap.get(index).costEstimate) {
				heapifyUp(index);
			} else {
				heapifyDown(index);
			}
		}
	}

	private void buildMinHeap() {
		for (int i = (heap.size() / 2) - 1; i >= 0; i--) {
			minHeapify(i);
		}
	}

	private void minHeapify(int index) {
		int left = 2 * index + 1;
		int right = 2 * index + 2;
		int smallest = index;

		if (left < heap.size() && heap.get(left).costEstimate < heap.get(smallest).costEstimate) {
			smallest = left;
		}

		if (right < heap.size() && heap.get(right).costEstimate < heap.get(smallest).costEstimate) {
			smallest = right;
		}

		if (smallest != index) {
			swap(index, smallest);
			minHeapify(smallest);
		}
	}

	private void heapifyUp(int index) {
		while (index > 0 && heap.get(parent(index)).costEstimate > heap.get(index).costEstimate) {
			swap(index, parent(index));
			index = parent(index);
		}
	}

	private void heapifyDown(int index) {
		int left = 2 * index + 1;
		int right = 2 * index + 2;
		int smallest = index;

		if (left < heap.size() && heap.get(left).costEstimate < heap.get(index).costEstimate) {
			smallest = left;
		}

		if (right < heap.size() && heap.get(right).costEstimate < heap.get(smallest).costEstimate) {
			smallest = right;
		}

		if (smallest != index) {
			swap(index, smallest);
			heapifyDown(smallest);
		}
	}

	private int parent(int index) {
		return (index - 1) / 2;
	}

	// Helper method to swap two elements in the heap
	private void swap(int i, int j) {
		Tile temp = heap.get(i);
		heap.set(i, heap.get(j));
		heap.set(j, temp);
	}

	//helper boolean methods for Djikstra
	public boolean isEmpty() {
		return heap.isEmpty();
	}

	public boolean contains(Tile tile) {
		return heap.contains(tile);
	}
}
