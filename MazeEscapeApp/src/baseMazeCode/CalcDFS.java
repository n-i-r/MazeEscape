package baseMazeCode;
//CalcDFS uses a depth first search algorithm to find a path through the graph

import AdjacencyListGraph.*;
import java.util.*;
import HeapPriorityQueue.MyEntry;

public class CalcDFS {
	private ALGraph graph;           //The input graph to be solved
	private ALGraph mst;             //The MST for the input graph
	private VertexList vertices;     //The vertices from the MST
	private MazeInfo mazeInfo;       //The info for the maze
	private Stack<Markable> path;    //The stack that will track our path through the graph
	private VertexList solnVertices; //The list of vertices in the shortest path
	private EdgeList solnEdges;      //The list of edges in the shortest path
	
	public CalcDFS(ALGraph g, ALGraph m, MazeInfo mi)
	{
		graph=g;
		mst=m;
		mazeInfo=mi;
		vertices=mst.vertices();
		path=new Stack<Markable>();
	}
	
	//Finds the starting point and finish point vertices
	private void findStartFinish()
	{
		//Coordinates for start and finish
		Coordinate startCoord=new Coordinate(mazeInfo.getRStart(), mazeInfo.getCStart());
		Coordinate finishCoord=new Coordinate(mazeInfo.getRFinish(), mazeInfo.getCFinish());
		
		//Vertices for the start and finish
		Vertex startVertex=null;
		Vertex finishVertex=null;
		
		//Find start and finish vertices
		for(Vertex v:vertices)
		{
			if(v.getElement().equals(startCoord))
				startVertex=v;
			else if(v.getElement().equals(finishCoord))
				finishVertex=v;
		}
		
		//Start the DFS algorithm
		findPath(startVertex, finishVertex);
	}
	
	private void findPath(Vertex start, Vertex finish)
	{
		//Set the vertex that we're starting at to "visited"
		start.setState(Markers.VISITED);
		
		//Push the vertex that we're starting at onto the path stack
		path.push(start);
		
		if(start.getElement().equals(finish.getElement()))
		{
			//In this scenario, that vertex that we're starting at is also
			//the destination vertex. So we sort the path stack and return.
			sortStack();
			return;
		}
		else
		{
			//In this scenario, we're not at the finish yet
			//Iterate over all of the nodes incident to the starting
			//vertex that we're currently at.
			for(IncidenceNode n:start.getIncidenceList())
			{
				if (n.getEdge().getState()==Markers.UNEXPLORED)
				{
					//Get the vertex at the other end of this edge
					Vertex w=graph.opposite(start, n.getEdge());
					
					//If the opposite vertex is unexplored, add the path
					//to the path stack and attempt to find a path from that vertex
					if(w.getState()==Markers.UNEXPLORED)
					{
						//Set the edge to a "discovery" edge
						n.getEdge().setState(Markers.DISCOVERY);
						//Push the path onto the stack
						path.push(n.getEdge());
						//Recursively try to find a path from that vertex
						findPath(w, finish);
						//Remove the edge from the path stack
						path.remove(n.getEdge());
					}
				}
				else //We've been there already, so set it to a "back" edge
					n.getEdge().setState(Markers.BACK);
			}
			//Remove this from the path stack
			path.remove(start);
		}
	}
	
	public MyEntry<VertexList, EdgeList> findSoln()
	{
		//Start the DFS process
		findStartFinish();
		
		//At this point, the solution is in the solnVertices and solnEdges
		//Package them up and return them
		//TODO: Throw exception if any list is null
		MyEntry<VertexList, EdgeList> retVals= new MyEntry<VertexList, EdgeList>(solnVertices, solnEdges);
		return retVals;
	}
	
	private void sortStack()
	{
		//Instantiate the solnVertices and solnEdges
		solnVertices = new VertexList();
		solnEdges = new EdgeList();
		
		//Iterate through all the elements in the path stack
		while(!path.isEmpty())
		{
			//Pop an element from the stack
			Markable m = path.pop();
			
			//Identify whether the item is a Vertex or Edge
			//Add the item to its corresponding list
			if(m instanceof Vertex)
			{
				solnVertices.add((Vertex)m);
			}
			else if(m instanceof Edge)
			{
				solnEdges.add((Edge)m);
			}
			else
			{
				//TODO: Throw Exception
			}
		}
	}
		

}
