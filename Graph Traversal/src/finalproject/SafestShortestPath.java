package finalproject;


import java.util.ArrayList;
import java.util.LinkedList;

import finalproject.system.Tile;

public class SafestShortestPath extends ShortestPath {
	public int health;
	public Graph costGraph;
	public Graph damageGraph;
	public Graph aggregatedGraph;

	//TODO level 8: finish class for finding the safest shortest path with given health constraint
	public SafestShortestPath(Tile start, int health) {
		super(start);
		this.health = health;
		generateGraph();
	}

	
	public void generateGraph() {
		// TODO Auto-generated method stub
		super.generateGraph();
		this.costGraph = super.g;

		this.damageGraph = generateDmgGraph();
		this.aggregatedGraph = generateDmgGraph();
	}

	public Graph generateDmgGraph() {
		this.g = new Graph(GraphTraversal.BFS(source));

		for(int i = 0; i < g.getVertices().size(); i++){
			for (int j = i+1; j < g.getVertices().size(); j++){
				if (g.getVertices().get(i).isWalkable() && g.getVertices().get(j).isWalkable() && g.getVertices().get(i).neighbors.contains(g.getVertices().get(j))){
						g.addEdge(g.getVertices().get(i), g.getVertices().get(j), g.getVertices().get(j).damageCost);
						g.addEdge(g.getVertices().get(j), g.getVertices().get(i), g.getVertices().get(i).damageCost);
				}

			}

		}

		return this.g;
	}
	public ArrayList<Tile> findPath(Tile startNode, LinkedList<Tile> waypoints) {

		this.g = this.costGraph;
		ArrayList<Tile> leastDistCostPath = super.findPath(startNode, waypoints);
		if(this.damageGraph.computePathCost(leastDistCostPath) < this.health){
			return leastDistCostPath;
		}

		this.g = this.damageGraph;
		ArrayList<Tile> leastDmgCostPath = super.findPath(startNode, waypoints);
		if(this.g.computePathCost(leastDmgCostPath) > this.health){
			return null;
		}

		return recursiveHelper(leastDistCostPath, leastDmgCostPath, startNode, waypoints);
	}

	public ArrayList<Tile> recursiveHelper(ArrayList<Tile> leastDistCostPath, ArrayList<Tile>leastDmgCostPath, Tile startNode, LinkedList<Tile> waypoints) {

		double multiplier = (this.costGraph.computePathCost(leastDistCostPath) - this.costGraph.computePathCost(leastDmgCostPath))
				/ (this.damageGraph.computePathCost(leastDmgCostPath) - this.damageGraph.computePathCost(leastDistCostPath));

		for(Graph.Edge edge: aggregatedGraph.getAllEdges()) {

			edge.weight = edge.weight * multiplier;

			Graph.Edge connector = null;
			for(Graph.Edge distEdge: costGraph.getAllEdges()) {

				if(distEdge.getStart().equals(edge.getStart()) && distEdge.getEnd().equals(edge.getEnd())) {

					connector = distEdge;
					break;
				}
			}


			edge.weight += connector.weight;
		}

		this.g = this.aggregatedGraph;
		ArrayList<Tile> leastAggCostPath = super.findPath(startNode, waypoints);
		double aggCost = this.aggregatedGraph.computePathCost(leastAggCostPath);

		if(aggCost - this.aggregatedGraph.computePathCost(leastDistCostPath) < 1e-8){
			return leastDmgCostPath;
		}

		else if (this.damageGraph.computePathCost(leastAggCostPath) <= this.health){
			return recursiveHelper(leastDmgCostPath, leastAggCostPath, startNode, waypoints);
		}

		else{
			return recursiveHelper(leastAggCostPath, leastDmgCostPath, startNode, waypoints);
		}
	}
}
