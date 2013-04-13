package baseMazeCode;
import java.util.*;
import AdjacencyListGraph.*;
import java.io.*;
import HeapPriorityQueue.KeyValPair;

public class MazeSolve {
	public static void main(String args[]) {
		// Declare Variables
		File file;
		boolean argsInput = false;

		// Helper Classes
		Scanner scan;
		GraphGen gGen;

		System.out.println("MazeSolve v1.0\nBy Nicholas Rose");

		// If the path is in args, store it into path
		if (args.length > 0) {
			// file=new File(args[0]);
			System.out.println("\nWe see a command line argument was given.");
			// gGen=new GraphGen(file);
			argsInput = true;
		}

		// else
		// {
		if (!argsInput) {
			System.out.println("\nWe didn't see any command line arguments.");
			System.out
					.println("Main Menu: \n(1) Input a text file (Recommended)");
			System.out.println("(2) Manually enter the maze info line by line");
			System.out.println("(3) Quit");
			System.out.println("(4) Auto-Generate a Maze (Experimental)");
			// System.out.print("[Enter a number] ");
		}

		boolean loop = true;
		while (loop) {
			try {
				int num;
				if (argsInput) {
					num = 1;
				} else {
					scan = new Scanner(System.in);
					num = scan.nextInt();
					// scan.close();
				}

				if (num == 1) {
					if (!argsInput) {
						scan = new Scanner(System.in);
						System.out
								.println("\nTip: To save time next time, you can also input the path\nas a command line argument.\n\nEnter the path for your file:");
						String path = scan.nextLine();
						file = new File(path);
					} else {
						argsInput = false;
						file = new File(args[0]);
					}
					gGen = new GraphGen(file);
					loop = false;

					System.out.println();
					KeyValPair<ALGraph, MazeInfo> vals = gGen.generate();
					ALGraph graph = vals.getKey();
					MazeInfo mz = vals.getValue();
					CalcMST cmst = new CalcMST(graph);
					ALGraph mst = cmst.solve();
					EdgeList eL = mst.edges();

					for (Edge e : eL) {
						System.out.println(e.getIdentity());
					}

					System.out.println();
					MazeDisplay mzd = new MazeDisplay(mz, mst);
					mzd.drawMaze();

					CalcDFS cdfs = new CalcDFS(graph, mst, mz);
					KeyValPair<VertexList, EdgeList> lists = cdfs.findSoln();
					VertexList vSoln = lists.getKey();
					EdgeList eSoln = lists.getValue();
					for (Vertex v : vSoln)
						System.out.println(v.getElement());
					mzd = new MazeDisplay(mz, mst, vSoln, eSoln);
					mzd.drawMaze();
				} else if (num == 2) {
					gGen = new GraphGen();
					loop = false;

					System.out.println();
					KeyValPair<ALGraph, MazeInfo> vals = gGen.manualGenerate();
					ALGraph graph = vals.getKey();
					MazeInfo mz = vals.getValue();
					CalcMST cmst = new CalcMST(graph);
					ALGraph mst = cmst.solve();
					EdgeList eL = mst.edges();

					for (Edge e : eL) {
						System.out.println(e.getIdentity());
					}

					System.out.println();
					MazeDisplay mzd = new MazeDisplay(mz, mst);
					mzd.drawMaze();

					CalcDFS cdfs = new CalcDFS(graph, mst, mz);
					KeyValPair<VertexList, EdgeList> lists = cdfs.findSoln();
					VertexList vSoln = lists.getKey();
					EdgeList eSoln = lists.getValue();
					for (Vertex v : vSoln)
						System.out.println(v.getElement());
					mzd = new MazeDisplay(mz, mst, vSoln, eSoln);
					mzd.drawMaze();
				} else if (num == 3) {
					System.out.println("Thank you. Have a nice day!");
					loop = false;
					System.exit(0);
				} else if (num == 4) {
					gGen = new GraphGen();
					loop = false;

					System.out.println("Enter an N value:");
					scan = new Scanner(System.in);
					int n = scan.nextInt();
					KeyValPair<ALGraph, MazeInfo> vals = gGen.autoGenerate(n);
					ALGraph graph = vals.getKey();
					MazeInfo mz = vals.getValue();
					CalcMST cmst = new CalcMST(graph);
					ALGraph mst = cmst.solve();
					EdgeList eL = mst.edges();

					for (Edge e : eL) {
						System.out.println(e.getIdentity());
					}

					System.out.println();
					MazeDisplay mzd = new MazeDisplay(mz, mst);
					mzd.drawMaze();

					CalcDFS cdfs = new CalcDFS(graph, mst, mz);
					KeyValPair<VertexList, EdgeList> lists = cdfs.findSoln();
					VertexList vSoln = lists.getKey();
					EdgeList eSoln = lists.getValue();
					for (Vertex v : vSoln)
						System.out.println(v.getElement());
					mzd = new MazeDisplay(mz, mst, vSoln, eSoln);
					mzd.drawMaze();
				} else {
					System.out
							.println("Sorry, we didn't quite understand that.");
					System.out.println("Please try again.");
					System.out
							.println("Main Menu: \n(1) Input a text file\n(2) Manually enter the maze info line by line");
					System.out.println("(3) Quit");
					// System.out.print("[Enter a number]");
				}
			} catch (Exception e) {
				System.out.println("Whoops! It looks like that didn't work...");
				System.out.println("Please try again.");
				System.out
						.println("Main Menu: \n(1) Input a text file\n(2) Manually enter the maze info line by line");
				System.out.println("(3) Quit");
				loop = true;
				// System.out.print("[Enter a number]");
			}
		}
	}
	// }
}
