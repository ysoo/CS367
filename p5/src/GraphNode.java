///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Game.java
// File:             GraphNode.java
// Semester:         CS 367 Spring 2016
//
// Author:           Yi Xian Soo ysoo@wisc.edu
// CS Login:         ysoo
// Lecturer's Name:  Skrentny
// Lab Section:      1
//
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class GraphNode implements Comparable<GraphNode>{
	//A value that can be used if a cost is needed even if the GraphNode is 
	//not a neighbor of a node.
	public static int NOT_NEIGHBOR;
	
	private String name;
	public boolean spycam;
	private List<Neighbor> neighbors;

	/**
	 * Represents a valid location in the game graph. There can be a
	 * player, a spycam, or a spy at any graph node.
	 * 
	 * @param name - The label that uniquely identifies this graph node.
	 */
	public GraphNode(String name) {
		this.name = name;
		neighbors = new ArrayList<Neighbor>();
		spycam = false;
	}

	/**
	 * Maintains sorted order of neighbors by neighbor name.
	 * 
	 * @param neighbor - An adjacent node (a neighbor)
	 * @param cost - The cost to move to that node (from this node)
	 */
	public void addNeighbor(GraphNode neighbor, int cost) {

		Neighbor fresh = new Neighbor(cost, neighbor);
		neighbors.add(fresh);
		Collections.sort(neighbors);;
	}

	/**
	 * Print a list of neighbors and the cost of the edge to them
	 */
	public void displayCostToEachNeighbor() {
		for(int i = 0; i < neighbors.size(); i ++) {
			Neighbor curr = neighbors.get(i);
			System.out.println(curr.getCost() + " " + curr.getNeighborNode().getNodeName() );
		}
	}

	/**
	 * @param neighborName - name of potential neighbor
	 * @return cost to neighborName
	 * @throws NotNeighborException - if neighborName is not a neighbor
	 */
	public int getCostto(String neighborName) throws NotNeighborException {
		int cost = 0;
		boolean found = false;
		for(int i = 0; i < neighbors.size(); i++) {
			Neighbor curr = neighbors.get(i);
			if(neighborName.equals(curr.getNeighborNode().getNodeName())) {
				cost = curr.getCost();
				found = true;
			}
		}
		if(!found) throw new NotNeighborException();
		return cost;
	}
	/**
	 * @param name - name of potential neighbor
	 * @return the GraphNode associated with name that is a neighbor of 
	 * the current node
	 * @throws NotNeighborException - if name is not a neighbor of the GraphNode
	 */
	public GraphNode getNeighbor(String NeighborName) throws NotNeighborException {
		boolean found = false;
		GraphNode done = null;
		for(int i = 0; i < neighbors.size(); i++) {
			Neighbor curr = neighbors.get(i);
			if(NeighborName.equals(curr.getNeighborNode().getNodeName())) {
				done = curr.getNeighborNode();
				found = true;
			}
		}
		if(!found) throw new NotNeighborException();
		return done;
	}

	/**
	 * Returns an iterator that can be used to find neighbors of this GraphNode.
	 * 
	 * @return An iterator of String node labels
	 */
	public Iterator<String> getNeighborNames() {
		List<String> l = new ArrayList<String>();
		for(Neighbor n: neighbors) {
			l.add(n.getNeighborNode().getNodeName());
		}
		return l.iterator();

	}

	/**
	 * @return a list of neighbors
	 */
	public List<Neighbor> getNeighbors() {
		return neighbors;
	}

	public String getNodeName() {
		return name;
	}

	/**
	 * @return true if the GraphNode has a spycam
	 */
	public boolean getSpycam() {
		return spycam;
	}
	
	/**
	 * Returns true if this node name is a neighbor of current node.
	 * 
	 * @param neighborName - to look for
	 * @return true if the node is an adjacent neighbor.
	 */
	public boolean isNeighbor(String neighborName) {
		boolean found = false;
		for(int i = 0; i < neighbors.size(); i++) {
			Neighbor curr = neighbors.get(i);
			if(neighborName.equals(curr.getNeighborNode().getNodeName())) {
				found = true;
			}
		}
		return found;
	}
	
	/**
	 * Display's the node name followed by a list of neighbors to this node. 
	 */
	public void printNeighborNames() {
		for(int i = 0; i < neighbors.size(); i ++) {
			Neighbor curr = neighbors.get(i);
			System.out.println(curr.getCost() + " " + curr.getNeighborNode().getNodeName() );
		}
	}
	
	/**
	 * 
	 * @param cam
	 */
	public void setSpycam(boolean cam) {
		spycam = cam;
	}
	/**
	 * toString in class java.lang.Object
	 * 
	 * @return name of node
	 */
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(GraphNode arg) {
		// TODO Auto-generated method stub
		return name.compareTo(arg.name);
	}

}
