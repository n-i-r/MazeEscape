//CalcMST implements the PrimJarnik Algorithm to generate the minimum spanning tree
//for the maze-graph.

import HeapPriorityQueue.*;
import AdjacencyListGraph.*;
import java.util.*;

public class CalcMST {
	private ALGraph graph; // The graph to find the MST
	private ALGraph mst; // The output minimum spanning tree
	private VertexList vertices; // The list of vertices in the input graph
	// private HeapPQ<Double,MyEntry<Vertex,Edge>> pq; //The priority queue
	// based heap
	private SmartHeapPQ pq; // A "smarter" priority queue based heap
	private VertexList newVertices; // A list of new vertices created by this
									// class
	private boolean debug = false; // An internal toggle to aid debugging

	public CalcMST(ALGraph g) {
		newVertices = new VertexList();
		graph = g;
		mst = new ALGraph();
		// pq=new HeapPQ<Double, MyEntry<Vertex,Edge>>();
		pq = new SmartHeapPQ();
		vertices = graph.vertices();
	}

	public ALGraph solve() {
		// TODO Make sure vertices >0
		if (debug)
			System.out.println("Solve Activated");
		Vertex v = vertices.get(0);

		// Set the label of the initial vertex to 0
		v.setLabel(0);

		for (Vertex u : vertices) {
			// Set all the label of all the other vertices to +Infinity
			if (u != v) {
				u.setLabel(Double.POSITIVE_INFINITY);
				if (debug)
					System.out.println("Setting label: Vertex "
							+ u.getElement());
			}

			MyEntry<Vertex, Edge> tempEntry = new MyEntry<Vertex, Edge>(u, null);
			pq.insert(u.getLabel(), tempEntry);
			if (debug)
				System.out.println("Insert: " + u.getElement());
		}

		while (!(pq.isEmpty())) {
			// Remove the smallest entry from the priority queue
			Entry<Double, MyEntry<Vertex, Edge>> min = pq.removeMin();

			// Extract the Vertex and Edge from the extracted entry
			Vertex cVertex = min.getValue().getKey();
			Edge cEdge = min.getValue().getValue();

			// Insert the Vertex and/or Edge into the MST
			if (cEdge == null) {
				if (debug)
					System.out.println("Inserting Vertex Only...");
				Vertex newVertex = mst.insertVertex(cVertex.getElement());
				newVertices.add(newVertex);
			} else {
				if (debug)
					System.out.println("Inserting both Vertex and Edge...");
				Vertex newVertex = mst.insertVertex(cVertex.getElement());
				newVertices.add(newVertex);
				mst.insertEdge(findNewVertex(cEdge.getVertex1()),
						findNewVertex(cEdge.getVertex2()), cEdge.getWeight(),
						cEdge.getIdentity());
			}

			if (debug)
				System.out.println("cVertex: " + cVertex.getElement());
			if (debug)
				System.out.println("cVertex Incidence List Size: "
						+ cVertex.getIncidenceList().size());

			// Iterates through the IncidenceList
			for (IncidenceNode n : cVertex.getIncidenceList()) {
				ArrayList<Entry<Double, MyEntry<Vertex, Edge>>> tempArr = new ArrayList<Entry<Double, MyEntry<Vertex, Edge>>>();
				boolean found = false;
				Entry<Double, MyEntry<Vertex, Edge>> oEntry = null;
				Edge oEdge = n.getEdge();
				Vertex oVertex = (n.getEdge().getVertex1() == cVertex) ? n
						.getEdge().getVertex2() : n.getEdge().getVertex1();

				if (debug)
					System.out.println("oEdge: " + oEdge.getIdentity());
				if (debug)
					System.out.println("oVertex: " + oVertex.getElement());

				int removed = 0;

				// Searches through the heap to see if the vertex is in there
				while (pq.size() > 0) {
					if (debug)
						System.out.println("Searching for "
								+ oVertex.getElement() + "... | removed="
								+ removed + " tempArr.size(): "
								+ tempArr.size());
					Entry<Double, MyEntry<Vertex, Edge>> tempEntry = pq
							.removeMin();
					removed++;

					if (tempEntry.getValue().getKey() == oVertex) {
						if (debug)
							System.out.println("Found value!");
						oEntry = tempEntry;
						found = true;
						break;
					} else {
						tempArr.add(new MyEntry<Double, MyEntry<Vertex, Edge>>(
								tempEntry.getKey(), tempEntry.getValue()));
					}
				}

				if (found == false)
					if (debug)
						System.out.println("oVertex not in heap.");
				int max = tempArr.size();
				for (int x = 0; x < max; x++) {
					if (debug)
						System.out.println("Inserting back to PQ... | x=" + x);
					pq.insert(tempArr.get(x).getKey(), tempArr.get(x)
							.getValue());
				}
				// found=oVertex.getHeapStatus();
				// System.out.println("oVertex.getHeapStatus(): " +
				// oVertex.getHeapStatus());
				if (found) {
					// oEntry=oVertex.getParentEntry();
					if ((int) oEdge.getWeight() < oVertex.getLabel()) {
						// Reassign the label in the vertex (if it was in the
						// heap)
						if (debug)
							System.out.print("Setting oVertex label from "
									+ oVertex.getLabel());
						oVertex.setLabel((int) oEdge.getWeight());
						if (debug)
							System.out.println(" to " + oVertex.getLabel()
									+ "\nReinserting into PQ");
						oEntry.setValue(new MyEntry<Vertex, Edge>(oVertex,
								oEdge));
						oEntry.setKey(oVertex.getLabel());
						pq.insert(oEntry.getKey(), oEntry.getValue());
					} else {
						pq.insert(oEntry.getKey(), oEntry.getValue());
					}
				}
			}
		}

		return mst;
	}

	// Translates a vertex from the input graph to a vertex for the output graph
	private Vertex findNewVertex(Vertex oV) {
		Vertex newVertex = null;
		for (Vertex nV : newVertices) {
			if (oV.getElement().equals(nV.getElement())) {
				newVertex = nV;
				break;
			}
		}
		return newVertex;
	}

}
