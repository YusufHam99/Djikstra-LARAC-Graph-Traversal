package finalproject;

import finalproject.system.Tile;

public class FastestPath extends PathFindingService {
    //TODO level 6: find time prioritized path
    public FastestPath(Tile start) {
        super(start);
        generateGraph();
    }

	@Override
	public void generateGraph() {
		// TODO Auto-generated method stub
        this.g = new Graph(GraphTraversal.BFS(source));

        for(int i = 0; i < g.getVertices().size(); i++){
            for (int j = i+1; j < g.getVertices().size(); j++){
                if (g.getVertices().get(i).isWalkable() && g.getVertices().get(j).isWalkable() && g.getVertices().get(i).neighbors.contains(g.getVertices().get(j))){
                    if(g.isMetroTile(g.getVertices().get(i)) && g.isMetroTile(g.getVertices().get(j))){
                        g.addEdge((g.getVertices().get(i)), g.getVertices().get(j), g.callFixMetroTimeCost(g.getVertices().get(j), g.getVertices().get(i)));
                        g.addEdge(g.getVertices().get(j), g.getVertices().get(i), g.callFixMetroTimeCost(g.getVertices().get(i), g.getVertices().get(j)));
                    }
                    else {
                        g.addEdge(g.getVertices().get(i), g.getVertices().get(j), g.getVertices().get(j).timeCost);
                        g.addEdge(g.getVertices().get(j), g.getVertices().get(i), g.getVertices().get(i).timeCost);
                    }
                }
            }

        }
	}

}
