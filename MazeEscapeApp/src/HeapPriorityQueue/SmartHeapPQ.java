//The purpose of SmartHeapPQ is to update each vertex to let it know if it's in the
//heap or not. This allows the MST algorithm to potentially run at a faster rate.

package HeapPriorityQueue;

import AdjacencyListGraph.Edge;
import AdjacencyListGraph.Vertex;

public class SmartHeapPQ extends HeapPQ<Double, KeyValPair<Vertex,Edge>>{

	public Entry<Double, KeyValPair<Vertex,Edge>> insert(double d, KeyValPair<Vertex, Edge> me)
	{
		super.checkKey(d);
		me.getKey().setHeapStatus(true);
		Entry<Double, KeyValPair<Vertex,Edge>> entry = new KeyValPair<Double, KeyValPair<Vertex,Edge>>(d, me);
		me.getKey().setParentEntry(entry);
		super.upHeap(heap.add(entry));
		return entry;
	}
	
	public Entry<Double, KeyValPair<Vertex,Edge>> removeMin()
	{
		Entry<Double, KeyValPair<Vertex,Edge>> me=super.removeMin();
		me.getValue().getKey().setHeapStatus(false);
		return me;
	}
}
