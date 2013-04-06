package AdjacencyListGraph;

public class Edge implements Markable{
	private Object weight;
	private Vertex v1;
	private Vertex v2;
	private IncidenceNode n1;
	private IncidenceNode n2;
	private Coordinate identity;
	private Markers state;
	
	public Edge(Object obj, Vertex vertex1, Vertex vertex2, IncidenceNode node1, IncidenceNode node2)
	{
		weight=obj;
		v1=vertex1;
		v2=vertex2;
		n1=node1;
		n2=node2;
		state=Markers.UNEXPLORED;
	}
	
	public Edge(Object obj, Vertex vertex1, Vertex vertex2, IncidenceNode node1, IncidenceNode node2, Coordinate i)
	{
		weight=obj;
		v1=vertex1;
		v2=vertex2;
		n1=node1;
		n2=node2;
		identity=i;
		state=Markers.UNEXPLORED;
	}
	
	public Object getWeight()
	{
		return weight;
	}
	
	public void setWeight(Object obj)
	{
		weight=obj;
	}
	
	public Vertex getVertex1()
	{
		return v1;
	}
	
	public void setVertex1(Vertex v)
	{
		v1=v;
	}
	
	public void setVertex2(Vertex v)
	{
		v2=v;
	}
	
	public Vertex getVertex2()
	{
		return v2;
	}
	
	public IncidenceNode getIncidenceNode1()
	{
		return n1;
	}
	
	public void setIncidenceNode1(IncidenceNode n)
	{
		n1=n;
	}
	
	public IncidenceNode getIncidenceNode2()
	{
		return n2;
	}
	
	public void setIncidenceNode2(IncidenceNode n)
	{
		n2=n;
	}
	
	public String toString()
	{
		return "Weight: "+weight;
	}
	
	public Coordinate getIdentity()
	{
		return identity;
	}
	
	public void setIdentity(Coordinate c)
	{
		identity=c;
	}
	
	public Markers getState()
	{
		return state;
	}
	
	public void setState(Markers m)
	{
		state=m;
	}
}
