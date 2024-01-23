package finalproject;

import java.util.ArrayList;
import java.util.HashSet;

import finalproject.system.Tile;
import finalproject.system.TileType;
import finalproject.tiles.*;

public class Graph {
	// TODO level 2: Add fields that can help you implement this data type
    private ArrayList<Tile> vertices;
    private ArrayList<Edge> edges;
    // TODO level 2: initialize and assign all variables inside the constructor
	public Graph(ArrayList<Tile> vertices) {
        this.vertices = vertices;
        this.edges = new ArrayList<>();
	}
	
    // TODO level 2: add an edge to the graph
    public void addEdge(Tile origin, Tile destination, double weight){
        Edge edge = new Edge(origin, destination, weight);
        edges.add(edge);
    }
    
    // TODO level 2: return a list of all edges in the graph
	public ArrayList<Edge> getAllEdges() {
        return edges;
	}
  
	// TODO level 2: return list of tiles adjacent to t
	public ArrayList<Tile> getNeighbors(Tile t) {
        ArrayList<Tile> neighbors = new ArrayList<>();
        for (Tile vertice: vertices)
            for (Edge edge : edges) {
                if (vertice.isWalkable() && edge.getStart().equals(t) && edge.getEnd().equals(vertice)) {
                neighbors.add(vertice);
            }
        }
        return neighbors;
    }
	
	// TODO level 2: return total cost for the input path
	public double computePathCost(ArrayList<Tile> path) {
        double totalCost = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            Tile current = path.get(i);
            Tile next = path.get(i + 1);
            totalCost += getEdgeWeight(current, next);
        }
        return totalCost;
	}

    public double getEdgeWeight(Tile origin, Tile destination) {
        for (Edge edge : edges) {
            if (edge.getStart().equals(origin) && edge.getEnd().equals(destination)) {
                return edge.weight;
            } else if (edge.getStart().equals(destination) && edge.getEnd().equals(origin)) {
                return edge.weight;
            }
        }
        return 0;
    }

    public ArrayList<Tile> getVertices() {
        return vertices;
    }

    public boolean isMetroTile(Tile tile) {
        return tile instanceof MetroTile;
    }

    public double callFixMetroCost(Tile tile, Tile tile2){
        ((MetroTile)tile).fixMetro( tile2);
        return((MetroTile)tile).getMetroDistanceCost();
    }

    public double callFixMetroTimeCost(Tile tile, Tile tile2){
        ((MetroTile)tile).fixMetro( tile2);
        return((MetroTile)tile).getMetroTimeCost();
    }

    public static class Edge{
    	Tile origin;
    	Tile destination;
    	double weight;

        // TODO level 2: initialize appropriate fields
        public Edge(Tile s, Tile d, double cost){
            this.origin = s;
            this.destination = d;
            this.weight = cost;
        }
        
        // TODO level 2: getter function 1
        public Tile getStart(){
            return this.origin;
        }

        
        // TODO level 2: getter function 2
        public Tile getEnd() {
            return this.destination;
        }

    }
    
}