package AdjacencyListGraph;

public class IncidenceNode {
	private IncidenceNode next;
	private IncidenceNode prev;
	private Edge edge;
	
	public IncidenceNode(Edge e)
	{
		//next=n;
		//prev=p;
		edge=e;
	}
	
	
	public IncidenceNode getNext()
	{
		return next;
	}
	
	public void setNext(IncidenceNode n)
	{
		next=n;
	}
	
	public IncidenceNode getPrev()
	{
		return prev;
	}
	
	public void setPrev(IncidenceNode p)
	{
		prev=p;
	}
	
	public Edge getEdge()
	{
		return edge;
	}
	
	public void setEdge(Edge e)
	{
		edge=e;
	}
}
