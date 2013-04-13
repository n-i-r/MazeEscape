package AdjacencyListGraph;
import HeapPriorityQueue.*;

public class Vertex implements Markable, HeapAware {
	private Coordinate element;
	private IncidenceList iList;
	private double label;
	private Coordinate identity;
	private Markers state;
	private boolean isInHeap;
	private Entry<Double, KeyValPair<Vertex, Edge>> parentEntry;
	
	public Vertex(Coordinate obj, IncidenceList il)
	{
		element=obj;
		iList=il;
		label=0;
		state=Markers.UNEXPLORED;
		isInHeap=false;
	}
	
	public Coordinate getElement()
	{
		return element;
	}
	
	public void setElement(Coordinate e)
	{
		element=e;
	}
	
	public IncidenceList getIncidenceList()
	{
		return iList;
	}
	
	public void setIncidenceList(IncidenceList il)
	{
		iList=il;
	}
	
	public String toString()
	{
		return "Element: " + element;
	}
	
	public double getLabel()
	{
		return label;
	}
	
	public void setLabel(double num)
	{
		label=num;
	}
	
	public void setIdentity(Coordinate c)
	{
		identity=c;
	}
	
	public Coordinate getIdentity()
	{
		return identity;
	}
	
	public void setState(Markers m)
	{
		state=m;
	}
	
	public Markers getState()
	{
		return state;
	}
	
	public boolean getHeapStatus()
	{
		return isInHeap;
	}
	
	public void setHeapStatus(boolean b)
	{
		isInHeap=b;
	}
	
	public void setParentEntry(Entry<Double, KeyValPair<Vertex, Edge>> pe)
	{
		parentEntry=pe;
	}
	
	public Entry<Double, KeyValPair<Vertex, Edge>> getParentEntry()
	{
		return parentEntry;
	}

}
