///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  SpyGraph.java
// Semester:         CS 367 Spring 2016
//
// Author:           Yi Xian Soo ysoo@wisc.edu
// CS Login:         ysoo
// Lecturer's Name:  Skrentny
// Lab Section:      1
//
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.*;
/**
 * Stores all vertexes as a list of GraphNodes.  Provides necessary graph operations as
 * need by the SpyGame class.
 * 
 * @author strominger
 *
 */
public class SpyGraph implements Iterable<GraphNode> {

	/** DO NOT EDIT -- USE THIS LIST TO STORE ALL GRAPHNODES */
	private List<GraphNode> vlist;


	/**
	 * Initializes an empty list of GraphNode objects
	 */
	public SpyGraph(){
		// TODO initialize data member(s)
		vlist = new ArrayList<GraphNode>();
	}

	/**
	 * Add a vertex with this label to the list of vertexes.
	 * No duplicate vertex names are allowed.
	 * @param name The name of the new GraphNode to create and add to the list.
	 */
	public void addGraphNode(String name){
		// TODO implement this method
		boolean found = false;
		for(int i = 0; i < vlist.size(); i ++) {
			String curr = vlist.get(i).getNodeName();
			if(name.equals(curr))
				found = true;
		}
		if(!found) vlist.add(new GraphNode(name));
	}

	/**
	 * Adds v2 as a neighbor of v1 and adds v1 as a neighbor of v2.
	 * Also sets the cost for each neighbor pair.
	 *   
	 * @param v1name The name of the first vertex of this edge
	 * @param v2name The name of second vertex of this edge
	 * @param cost The cost of traveling to this edge
	 * @throws IllegalArgumentException if the names are the same
	 */
	public void addEdge(String v1name, String v2name, int cost) throws IllegalArgumentException{
		// TODO implement this method
		if(v1name.equals(v2name)) throw new IllegalArgumentException();

		GraphNode v1 = getNodeFromName(v1name);
		GraphNode v2 = getNodeFromName(v2name);
		v1.addNeighbor(v2, cost);
		v2.addNeighbor(v1, cost);
	}

	/**
	 * Return an iterator through all nodes in the SpyGraph
	 * @return iterator through all nodes in alphabetical order.
	 */
	public Iterator<GraphNode> iterator() {
		return vlist.iterator();
	}

	/**
	 * Return Breadth First Search list of nodes on path 
	 * from one Node to another.
	 * @param start First node in BFS traversal
	 * @param end Last node (match node) in BFS traversal
	 * @return The BFS traversal from start to end node.
	 */
	public List<Neighbor> BFS(String start, String end ) {
		// TODO implement this method
		// may need and create a companion method
		Neighbor startn = new Neighbor(1, getNodeFromName(start));
		List<Neighbor> visited = new ArrayList<Neighbor>();

		//Create a List of predecessor
		HashMap<GraphNode, Neighbor> pred = new HashMap<GraphNode, Neighbor> ();

		Queue<Neighbor> qn = new LinkedList<Neighbor>();
		GraphNode endn = getNodeFromName(end);

		qn.add(startn);
		visited.add(startn);
		pred.put(getNodeFromName(start), null);

		while(!qn.isEmpty()) {
			Neighbor add = qn.remove();
			GraphNode curr = add.getNeighborNode();

			for(Neighbor k: curr.getNeighbors()) {

				if(visit(visited, k) == false) {
					visited.add(k);
					qn.add(k);
					pred.put(k.getNeighborNode(), add);
				} 
			}
		}

		if(pred.get(endn) == null) {
			throw new IllegalArgumentException();
		}

		Stack<Neighbor> path = new Stack<Neighbor>();

		Neighbor curr = pred.get(endn);
		while(curr.compareTo(startn)!= 0) {
			path.push(curr);
			curr = pred.get(curr.getNeighborNode());
		}

		List<Neighbor> pathl = new ArrayList<Neighbor>();
		while(!path.isEmpty()) {
			pathl.add(path.pop());
		}

		Neighbor scnlast = null;
		if(!pathl.isEmpty()) {
			scnlast = pathl.get(pathl.size() - 1);
		}
		//to get last neighbor node
		if(scnlast != null && scnlast.getNeighborNode().getNodeName().compareTo(endn.getNodeName()) != 0) {
			for(Neighbor m: scnlast.getNeighborNode().getNeighbors()) {
				if(m.getNeighborNode().getNodeName().compareTo(endn.getNodeName()) == 0) {
					pathl.add(m);
				}
			}
		} else {
			for(Neighbor m: getNodeFromName(start).getNeighbors()) {
				if(m.getNeighborNode().getNodeName().compareTo(endn.getNodeName()) == 0) {
					pathl.add(m);
				}
			}
		}

		return pathl;
	}

	private boolean visit(List<Neighbor> visited, Neighbor n) {
		boolean visit = false;
		for(int i = 0; i < visited.size(); i ++) {
			if(visited.get(i).compareTo(n) == 0)
				visit = true;
		}
		return visit;
	}

	/**
	 * @param name Name corresponding to node to be returned
	 * @return GraphNode associated with name, null if no such node exists
	 */
	public GraphNode getNodeFromName(String name){
		for ( GraphNode n : vlist ) {
			if (n.getNodeName().equalsIgnoreCase(name))
				return n;
		}
		return null;
	}

	/**
	 * Return Depth First Search list of nodes on path 
	 * from one Node to another.
	 * @param start First node in DFS traversal
	 * @param end Last node (match node) in DFS traversal
	 * @return The DFS traversal from start to end node.
	 */
	public List<Neighbor> DFS(String start, String end) {
		// TODO implement this method
		// may need and create a companion method

		Stack<Neighbor> stack = new Stack<Neighbor>();
		List<Neighbor> visit = new ArrayList<Neighbor>();
		Neighbor startn = new Neighbor(1, getNodeFromName(start));
		GraphNode endn = getNodeFromName(end);
		visit.add(startn);

		stack = dfs1(getNodeFromName(start), endn, visit, stack);
		return stack;

	}

	private Stack<Neighbor> dfs1(GraphNode start, GraphNode end, List<Neighbor> visited, Stack<Neighbor> s) {

		if(start.compareTo(end) == 0) {
			return s;
		}
		for(Neighbor m: start.getNeighbors()) {
			if(visit(visited, m) == false) {
				visited.add(m);
				s.push(m);
				dfs1(m.getNeighborNode(), end, visited, s);
			}
		}
		return s;
	}

	/**
	 * OPTIONAL: Students are not required to implement Dijkstra's ALGORITHM
	 *
	 * Return Dijkstra's shortest path list of nodes on path 
	 * from one Node to another.
	 * @param start First node in path
	 * @param end Last node (match node) in path
	 * @return The shortest cost path from start to end node.
	 */
	public List<Neighbor> Dijkstra(String start, String end){

		// TODO: implement Dijkstra's shortest path algorithm
		// may need and create a companion method

		PriorityQueue aj = new PriorityQueue();
		return new ArrayList<Neighbor>();
	}


	/**
	 * DO NOT EDIT THIS METHOD 
	 * @return a random node from this graph
	 */
	public GraphNode getRandomNode() {
		if (vlist.size() <= 0 ) {
			System.out.println("Must have nodes in the graph before randomly choosing one.");
			return null;
		}
		int randomNodeIndex = Game.RNG.nextInt(vlist.size());
		return vlist.get(randomNodeIndex);
	}


}
