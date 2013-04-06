package baseMazeCode;
//CalcDFS uses a depth first search algorithm to find a path through the graph

import AdjacencyListGraph.*;
import java.util.*;
import HeapPriorityQueue.MyEntry;

public class CalcDFS {
	private ALGraph graph;
	private ALGraph mst;
	@SuppressWarnings("unused")
	private EdgeList removedEdges;
	private VertexList vertices;
	private MazeInfo mazeInfo;
	private Stack<Markable> path;
	private VertexList solnVertices;
	private EdgeList solnEdges;
	private boolean debug=false;
	
	public CalcDFS(ALGraph g, ALGraph m, MazeInfo mi)
	{
		graph=g;
		mst=m;
		removedEdges=mst.edges();
		mazeInfo=mi;
		vertices=mst.vertices();
		path=new Stack<Markable>();
	}
	
	private void findStartFinish()
	{
		Coordinate startCoord=new Coordinate(mazeInfo.getRStart(), mazeInfo.getCStart());
		Coordinate finishCoord=new Coordinate(mazeInfo.getRFinish(), mazeInfo.getCFinish());
		Vertex startVertex=null;
		Vertex finishVertex=null;
		
		//Find start vertex
		for(Vertex v:vertices)
		{
			if(v.getElement().equals(startCoord))
				startVertex=v;
			else if(v.getElement().equals(finishCoord))
				finishVertex=v;
		}
		
		findPath(startVertex, finishVertex);
	}
	
	private void findPath(Vertex start, Vertex finish)
	{
		if(debug)System.out.println("Finding path...");
		start.setState(Markers.VISITED);
		path.push(start);
		
		if(start.getElement().equals(finish.getElement()))
		{
			if(debug)System.out.println("Match found");
			sortStack();
			return;
		}
		else
		{
			if(debug)System.out.println("Else...");
			for(IncidenceNode n:start.getIncidenceList())
			{
				if(debug)System.out.println("Iterating n");
				//if((n.getEdge().getState()==Markers.REMOVED))
				if(true)
				{
					if(debug)System.out.println("Within removed.");
					if (n.getEdge().getState()==Markers.UNEXPLORED)
					{
						Vertex w=graph.opposite(start, n.getEdge());
						if(w.getState()==Markers.UNEXPLORED)
						{
							n.getEdge().setState(Markers.DISCOVERY);
							path.push(n.getEdge());
							findPath(w, finish);
							path.remove(n.getEdge());
							//path.pop();
						}
					}
					else
						n.getEdge().setState(Markers.BACK);
				}
			}
			
			path.remove(start);
			//path.pop();
		}
	}
	
	public MyEntry<VertexList, EdgeList> findSoln()
	{
		findStartFinish();
		//sortStack();
		
		MyEntry<VertexList, EdgeList> retVals= new MyEntry<VertexList, EdgeList>(solnVertices, solnEdges);
		return retVals;
	}
	
	private void sortStack()
	{
		solnVertices = new VertexList();
		solnEdges = new EdgeList();
		
		while(!path.isEmpty())
		{
			Markable m = path.pop();
			
			if(m instanceof Vertex)
			{
				solnVertices.add((Vertex)m);
				if(debug)System.out.println("Vertex");
			}
			else if(m instanceof Edge)
			{
				solnEdges.add((Edge)m);
				if(debug)System.out.println("Edge");
			}
			else
			{
				if(debug)System.out.println("Unsorted error");
			}
		}
	}
		

}
