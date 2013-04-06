import AdjacencyListGraph.ALGraph;
import HeapPriorityQueue.MyEntry;

public class MazeFactory {
	public MyEntry<MazeInfo, ALGraph> generateMaze(int nVal) {
		// The thing that will generate the maze
		GraphGen gGen = new GraphGen();

		// GraphGen will generate the maze and the info for the maze
		// This will be stored in a MyEntry (which holds two objects)
		MyEntry<ALGraph, MazeInfo> vals = gGen.autoGenerate(nVal);

		// Split the MyEntry into its corresponding parts
		ALGraph graph = vals.getKey();
		MazeInfo mz = vals.getValue();

		// Run the MST alg on the generated graph and store it in mst
		CalcMST cmst = new CalcMST(graph);
		ALGraph mst = cmst.solve();

		// Package back up the maze info and the mst into a MyEntry
		MyEntry<MazeInfo, ALGraph> mazeParts = new MyEntry<MazeInfo, ALGraph>(
				mz, mst);

		// Return the stuff needed to recreate the maze
		return mazeParts;

	}
}
