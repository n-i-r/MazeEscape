//The purpose of SmartHeapPQ is to update each vertex to let it know if it's in the
//heap or not. This allows the MST algorithm to potentially run at a faster rate.

package HeapPriorityQueue;

import AdjacencyListGraph.Edge;
import AdjacencyListGraph.Vertex;

public class SmartHeapPQ extends HeapPQ<Double, MyEntry<Vertex,Edge>>{

	public Entry<Double, MyEntry<Vertex,Edge>> insert(double d, MyEntry<Vertex, Edge> me)
	{
		super.checkKey(d);
		me.getKey().setHeapStatus(true);
		Entry<Double, MyEntry<Vertex,Edge>> entry = new MyEntry<Double, MyEntry<Vertex,Edge>>(d, me);
		me.getKey().setParentEntry(entry);
		super.upHeap(heap.add(entry));
		return entry;
	}
	
	public Entry<Double, MyEntry<Vertex,Edge>> removeMin()
	{
		Entry<Double, MyEntry<Vertex,Edge>> me=super.removeMin();
		me.getValue().getKey().setHeapStatus(false);
		return me;
	}
}
