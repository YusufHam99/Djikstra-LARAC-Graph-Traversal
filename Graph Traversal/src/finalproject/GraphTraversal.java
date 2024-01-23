package finalproject;

import finalproject.system.Tile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class GraphTraversal
{


	//TODO level 1: implement BFS traversal starting from s
	public static ArrayList<Tile> BFS(Tile s)
	{
		ArrayList<Tile> visited = new ArrayList<>();
		LinkedList<Tile> queue = new LinkedList<>(); // Initialize the queue properly

		queue.add(s);
		visited.add(s);

		while (!queue.isEmpty()) {
			Tile current = queue.poll();

			for (Tile neighbor : current.neighbors) {
				if (!visited.contains(neighbor) && neighbor.isWalkable()) {
					queue.add(neighbor);
					visited.add(neighbor);
				}
			}
		}

		return visited;
	}


	//TODO level 1: implement DFS traversal starting from s
	public static ArrayList<Tile> DFS(Tile s) {
		ArrayList<Tile> visited = new ArrayList<>();
		LinkedList<Tile> stack = new LinkedList<>();

		stack.push(s);

		while (!stack.isEmpty()) {
			Tile current = stack.pop();

			if (!visited.contains(current)) {
				visited.add(current);

				for (Tile neighbor : current.neighbors) {
					if (!visited.contains(neighbor) && neighbor.isWalkable()) {
						stack.push(neighbor);
					}
				}
			}
		}

		return visited;
	}

}  