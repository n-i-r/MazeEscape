package AdjacencyListGraph;

public class ALGraph {
	private VertexList vList;
	private EdgeList eList;
	private int edgeCount;
	private int vertexCount;
	private boolean debug=false;
	
	public ALGraph()
	{
		vList=new VertexList();
		eList=new EdgeList();
		edgeCount=0;
		vertexCount=0;
	}
	
	public VertexList vertices()
	{
		return vList;
	}
	
	public EdgeList edges()
	{
		return eList;
	}
	
	public IncidenceList incidentEdges(Vertex v)
	{
		return v.getIncidenceList();
	}
	
	public Vertex opposite(Vertex v, Edge e)
	{
		if (e.getVertex1()==v)
		{
			return e.getVertex2();
		}
		else if (e.getVertex2()==v)
		{
			return e.getVertex1();
		}
		else
			throw new VertexNotFoundException();
	}
	
	public Vertex[] endVertices(Edge e)
	{
		Vertex[] vertices = new Vertex[2];
		vertices[0]=e.getVertex1();
		vertices[1]=e.getVertex2();
		
		return vertices;
	}
	
	public boolean areAdjacent(Vertex v1, Vertex v2)
	{
		IncidenceList iL=v1.getIncidenceList();
		boolean found=false;
		for (IncidenceNode n:iL)
		{
			Edge e=n.getEdge();
			if (e.getVertex1()==v1 && e.getVertex2()==v2)
			{
				found=true;
				break;
			}
			else if (e.getVertex1()==v2 && e.getVertex2()==v1)
			{
				found=true;
				break;
			}
		}
		return found;
	}
	
	public void replace(Vertex v, Coordinate obj)
	{
		v.setElement(obj);
	}
	
	public void replace(Edge e, Object obj)
	{
		e.setWeight(obj);
	}
	
	public Vertex insertVertex(Coordinate obj)
	{
		IncidenceList il = new IncidenceList();
		Vertex v=new Vertex(obj, il);
		vList.add(v);
		vertexCount++;
		
		if(debug)System.out.println("=== Insert Vertex ===");
		if(debug)System.out.println("Vertex Coordinate: "+v.getElement()+"\n");
		
		return v;
	}
	
	public Edge insertEdge(Vertex v1, Vertex v2, Object obj)
	{	
		IncidenceList il1=v1.getIncidenceList();
		IncidenceList il2=v2.getIncidenceList();
		
		Edge e=new Edge(obj, v1, v2, new IncidenceNode(null), new IncidenceNode(null));
		
		e.getIncidenceNode1().setEdge(e);
		e.getIncidenceNode2().setEdge(e);
		
		il1.add(e.getIncidenceNode1());
		il2.add(e.getIncidenceNode2());
		
		eList.add(e);
		edgeCount++;
		
		
		
		return e;
	}
	
	public Edge insertEdge(Vertex v1, Vertex v2, Object obj, Coordinate coord)
	{	
		IncidenceList il1=v1.getIncidenceList();
		IncidenceList il2=v2.getIncidenceList();
		
		Edge e=new Edge(obj, v1, v2, new IncidenceNode(null), new IncidenceNode(null), coord);
		
		e.getIncidenceNode1().setEdge(e);
		e.getIncidenceNode2().setEdge(e);
		
		il1.add(e.getIncidenceNode1());
		il2.add(e.getIncidenceNode2());
		
		eList.add(e);
		edgeCount++;
		
		if(debug)System.out.println("=== Insert Edge ===");
		if(debug)System.out.println("Between: "+v1.getElement() + " and " + v2.getElement());
		if(debug)System.out.println("Coordinate: " + e.getIdentity() + " Weight: "+e.getWeight()+"\n");
		if(debug)System.out.println("v1 iList Size: "+ v1.getIncidenceList().size() + " v2 iList Size: "+ v2.getIncidenceList().size());
		
		return e;
	}
	
	public Object removeVertex(Vertex v)
	{
		if (vertexCount<=0)
			throw new NoVertexInGraphException();
		
		Object obj=v.getElement();
		IncidenceList il=v.getIncidenceList();
		for(IncidenceNode n:il)
		{
			Edge e=n.getEdge();
			
			//Cut all ties from the edge to the incidence node
			if (e.getIncidenceNode1()==n)
			{
				e.setIncidenceNode1(null);
			}
			else
			{
				e.setIncidenceNode2(null);
			}
			
			//Cut all ties from the edge to the vertex
			if(e.getVertex1()==v)
			{
				e.setVertex1(null);
			}
			else
			{
				e.setVertex2(null);
			}
			
			//Cut all ties from the incidence node to the edge
			n.setEdge(null);
		}
		
		vList.remove(v);
		vertexCount--;
		
		return obj;
	}
	
	public Object removeEdge(Edge e)
	{
		if (edgeCount<=0)
			throw new NoEdgeInGraphException();
		
		Object obj=e.getWeight();
		
		//Cut all vertex1 related ties
		if(!(e.getIncidenceNode1()==null))
			e.getVertex1().getIncidenceList().remove(e.getIncidenceNode1());
		e.setVertex1(null);
		e.setIncidenceNode1(null);
		
		//Cut all vertex2 related ties
		if(!(e.getIncidenceNode2()==null))
			e.getVertex2().getIncidenceList().remove(e.getIncidenceNode2());
		e.setVertex2(null);
		e.setIncidenceNode2(null);
		
		//Remove node
		eList.remove(e);
		edgeCount--;
		
		return obj;
	}
}
