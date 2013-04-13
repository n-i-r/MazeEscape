package baseMazeCode;
//CalcMST implements the PrimJarnik Algorithm to generate the minimum spanning tree
//for the maze-graph.

import HeapPriorityQueue.*;
import AdjacencyListGraph.*;
import java.util.*;

public class CalcMST {
	private ALGraph graph; 			// The graph to find the MST
	private ALGraph mst; 	  		// The output minimum spanning tree
	private VertexList vertices; 	// The list of vertices in the input graph
	private SmartHeapPQ pq; 		// A "smarter" priority queue based heap
	private VertexList newVertices; // A list of new vertices created by this class
	
	public CalcMST(ALGraph g) {
		newVertices = new VertexList();
		graph = g;
		mst = new ALGraph();
		pq = new SmartHeapPQ();
		vertices = graph.vertices();
	}

	public ALGraph solve() {
		//Get the initial vertex
		Vertex v = vertices.get(0);

		// Set the label of the initial vertex to 0
		v.setLabel(0);

		//This loop sets the label of all the other vertices to +Infinity
		//Then it inserts it into pq
		for (Vertex u : vertices) {
			// If the vertex != v, then set its label to +Infinity
			if (u != v) {
				u.setLabel(Double.POSITIVE_INFINITY);
			}

			//Store the vertex in a key/value pair
			KeyValPair<Vertex, Edge> tempEntry = new KeyValPair<Vertex, Edge>(u, null);
			
			//Insert the key/value pair into pq
			pq.insert(u.getLabel(), tempEntry);
		}

		while (!(pq.isEmpty())) {
			// Remove the smallest entry from the priority queue
			Entry<Double, KeyValPair<Vertex, Edge>> min = pq.removeMin();
			
			/*Note about naming conventions:
			  The "c" in names like "cVertex" stands for "current"
			  Aka this is the current vertex.
			  Later on there will also be an "o" in some of the names.
			  The "o" stands for "opposite. So oVertex is a vertex
			  opposite to the "current" vertex.*/
			
			// Extract the Vertex and Edge from the key/value pair
			Vertex cVertex = min.getValue().getKey();
			Edge cEdge = min.getValue().getValue();

			// Insert the Vertex and/or Edge into the MST
			if (cEdge == null) {
				//If the current vertex doesn't have an edge associated with it,
				//we can just insert it into the MST graph.
				Vertex newVertex = mst.insertVertex(cVertex.getElement());
				newVertices.add(newVertex);
			} else {
				//If the current vertex does have an edge associated with it, then
				//we have to insert the vertex, and then connect the edge between
				//its corresponding vertices.
				Vertex newVertex = mst.insertVertex(cVertex.getElement());
				newVertices.add(newVertex);
				mst.insertEdge(findNewVertex(cEdge.getVertex1()),
						findNewVertex(cEdge.getVertex2()), cEdge.getWeight(),
						cEdge.getIdentity());
			}

			// Iterates through the IncidenceList (the list of all vertices incident to the current vertex)
			for (IncidenceNode n : cVertex.getIncidenceList()) {
				//*** Declarations for variables that be used momentarily... ***
				//An arraylist that will be used to store the vertices that aren't the one that we're looking for
				ArrayList<Entry<Double, KeyValPair<Vertex, Edge>>> tempArr = new ArrayList<Entry<Double, KeyValPair<Vertex, Edge>>>();
				//A boolean for the search later
				boolean found = false;
				//The entry opposite from the current one (we will find this later, thus it's null for now)
				Entry<Double, KeyValPair<Vertex, Edge>> oEntry = null;
				//The edge connected to our current vertex:
				Edge oEdge = n.getEdge();
				//The vertex opposite from our current one:
				Vertex oVertex = (n.getEdge().getVertex1() == cVertex) ? n.getEdge().getVertex2() : n.getEdge().getVertex1();
				//*** End declarations ***
				
				// Searches through the heap to see if the vertex is in there
				while (pq.size() > 0) {
					//Create a temporary entry to hold the candidate entry that's being checked
					Entry<Double, KeyValPair<Vertex, Edge>> tempEntry = pq.removeMin();
					
					//If the entry in tempEntry is the one that we're looking for,
					//then store it in oEntry. Otherwise toss it in our arraylist.
					if (tempEntry.getValue().getKey() == oVertex) {
						oEntry = tempEntry;
						found = true;
						break;
					} else {
						tempArr.add(new KeyValPair<Double, KeyValPair<Vertex, Edge>>(
								tempEntry.getKey(), tempEntry.getValue()));
					}
				}

				//Toss all of the vertices that we don't need back into pq
				int max = tempArr.size();
				for (int x = 0; x < max; x++) {
					pq.insert(tempArr.get(x).getKey(), tempArr.get(x).getValue());
				}

				//If the while loop from above found the vertex that we're looking for,
				//this code will execute.
				if (found) {
					if ((int) oEdge.getWeight() < oVertex.getLabel()) {
						// Reassign the label in the vertex
						//Remember, there's a chance that oVertex is equal to +Infinity
						oVertex.setLabel((int) oEdge.getWeight());
						oEntry.setValue(new KeyValPair<Vertex, Edge>(oVertex, oEdge));
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
