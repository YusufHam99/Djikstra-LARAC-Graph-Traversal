package finalproject;

import finalproject.system.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public abstract class PathFindingService {
	Tile source;
	Graph g;
	
	public PathFindingService(Tile start) {
    	this.source = start;
    }

	public abstract void generateGraph();
    
    //TODO level 4: Implement basic dijkstra's algorithm to find a path to the final unknown destination
    public ArrayList<Tile> findPath(Tile startNode) {
        djikstra(this.g, startNode);
        Tile destTile = null;
        for (Tile t : g.getVertices()){
            if (t.isDestination){
                destTile = t;
                break;
            }
        }
        ArrayList<Tile> path = new ArrayList<>();
        path.add(destTile);
        while (destTile.predecessor != startNode) {
            path.add(destTile.predecessor);
            destTile = destTile.predecessor;
        }
        path.add(startNode);
        int size = path.size();
        for (int i = 0; i < size / 2; i++) {
            Tile temp = path.get(i);
            path.set(i, path.get(size - i - 1));
            path.set(size - i - 1, temp);
        }
        return path;
    }

    public void djikstra(Graph g, Tile source){
        initializeSingleSource(g.getVertices(), source);

        ArrayList<Tile> S = new ArrayList<>();
        TilePriorityQ Q = new TilePriorityQ(g.getVertices());

        while (!Q.isEmpty()) {
            Tile u = Q.removeMin();
            S.add(u);
            for (Tile v : g.getNeighbors(u)) {
                relax(u, v, g.getEdgeWeight(u, v), Q);
            }
        }
    }

    private void initializeSingleSource(ArrayList<Tile> uniqueTiles, Tile startNode) {
        for (Tile tile : uniqueTiles) {
            tile.costEstimate = Double.POSITIVE_INFINITY;
            tile.predecessor = null;
        }
        startNode.costEstimate = 0;
    }


    private void relax(Tile u, Tile v, double weight, TilePriorityQ Q) {
        if (v.costEstimate > u.costEstimate + weight) {
            v.costEstimate = u.costEstimate + weight;
            v.predecessor = u;
            Q.updateKeys(v, u, v.costEstimate);
        }
    }

    
    //TODO level 5: Implement basic dijkstra's algorithm to path find to a known destination
    public ArrayList<Tile> findPath(Tile start, Tile end) {
        djikstra(this.g, start);

        ArrayList<Tile> path = new ArrayList<>();
        path.add(end);
        while (end.predecessor != start) {
            path.add(end.predecessor);
            end = end.predecessor;
        }
        path.add(start);
        int size = path.size();
        for (int i = 0; i < size / 2; i++) {
            Tile temp = path.get(i);
            path.set(i, path.get(size - i - 1));
            path.set(size - i - 1, temp);
        }
        return path;

    }

    //TODO level 5: Implement basic dijkstra's algorithm to path find to the final destination passing through given waypoints
    public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints){
        ArrayList<Tile> path = new ArrayList<>();

        for (int i = 0; i < waypoints.size(); i++) {
            Tile destination = waypoints.get(i);

            ArrayList<Tile> segment = findPath(start, destination);
            path.addAll(segment.subList(0, segment.size() - 1));
            start = destination;
        }

        Tile destTile = null;
        for (Tile t : g.getVertices()){
            if (t.isDestination){
                destTile = t;
                break;
            }
        }
        path.addAll(findPath(start, destTile));

        return path;
    }
        
}

