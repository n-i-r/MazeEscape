package baseMazeCode;
import AdjacencyListGraph.ALGraph;
import AdjacencyListGraph.EdgeList;
import AdjacencyListGraph.VertexList;
import HeapPriorityQueue.KeyValPair;

public class MazeFactory {
	private int n;            //The size of the maze
	private ALGraph graph;    //The graph representation of the maze
	private MazeInfo mz;      //The info about the maze start, end, size, etc.
	private ALGraph mst;      //The MST through graph. These are walls to be removed
	private VertexList vSoln; //The vertices that are a part of the maze's solution
	private EdgeList eSoln;   //The edges that are a part the maze's solution

	public MazeFactory(int nVal) {
		//Store the size of the maze
		n = nVal;

		// The graph generator that will auto-generate the maze
		GraphGen gGen = new GraphGen();

		// GraphGen will generate the maze and the info for the maze
		// This will be stored in a KeyValPair (which holds two objects)
		KeyValPair<ALGraph, MazeInfo> vals = gGen.autoGenerate(nVal);

		// Split the KeyValPair into its corresponding parts
		graph = vals.getKey();
		mz = vals.getValue();

		// Run the MST alg on the generated graph and store it in mst
		CalcMST cmst = new CalcMST(graph);
		mst = cmst.solve();
		
		//Run the DFS algorithm on the maze to find the shortest path
		CalcDFS cdfs = new CalcDFS(graph, mst, mz);
		KeyValPair<VertexList, EdgeList> lists = cdfs.findSoln();
		
		//Store the VertexList and EdgeList in vSoln and eSoln
		vSoln = lists.getKey();
		eSoln = lists.getValue();
	}

	/**
	 * Returns the walls to be removed from a maze of size n
	 * 
	 * @return A KeyValPair with the MazeInfo and ALGraph of the MST
	 */
	public KeyValPair<MazeInfo, ALGraph> generateMaze() {
		// Package up the maze info and the mst into a KeyValPair
		KeyValPair<MazeInfo, ALGraph> mazeParts = new KeyValPair<MazeInfo, ALGraph>(mz, mst);
		
		//Return the relevant parts for the maze
		return mazeParts;
	}
	
	/**
	 * 
	 * @return An integer with the minimum # of steps in an ideal solution
	 */
	public int getMinimumSteps()
	{
		return vSoln.size();
	}
	
	public int getN()
	{
		return n;
	}
	
	public VertexList getVertexSoln()
	{
		return vSoln;
	}
}
