package baseMazeCode;

import AdjacencyListGraph.ALGraph;
import AdjacencyListGraph.EdgeList;
import AdjacencyListGraph.VertexList;
import HeapPriorityQueue.KeyValPair;

public class Solver {
	private CalcDFS dfsAlg;
	private ALGraph graph;
	private ALGraph mst;
	private MazeInfo mazeInfo;
	private VertexList vSoln;
	private EdgeList eSoln;
	
	public Solver(ALGraph g, ALGraph m, MazeInfo mi)
	{
		graph = g;
		mst = m;
		mazeInfo = mi;
		dfsAlg = new CalcDFS(graph, mst, mazeInfo);
	}
	
	public void solve()
	{
		//Get the solution vertices and edges for the maze
		KeyValPair<VertexList,EdgeList> mazeSoln=dfsAlg.findSoln();
		vSoln = mazeSoln.getKey();
		eSoln = mazeSoln.getValue();
	}

}
