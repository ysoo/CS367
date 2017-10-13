///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Game.java
// File:             Neighbor.java
// Semester:         CS 367 Spring 2016
//
// Author:           Yi Xian Soo ysoo@wisc.edu
// CS Login:         ysoo
// Lecturer's Name:  Skrentny
// Lab Section:      1
//
//////////////////////////// 80 columns wide //////////////////////////////////

public class Neighbor implements Comparable<Neighbor>{

	private int cost;
	private GraphNode neighbor;
	
	/**
	 * A neighbor is added to an existing GraphNode by creating an instance of Neighbor
	 * that stores the neighbor and the cost to reach that neighbor.
	 */
	public Neighbor(int cost, GraphNode neighbor) {
		this.cost = cost;
		this.neighbor = neighbor;
	}
	
	/**
	 * Returns the cost of travelling this edge to get to the Neighbor at the other end 
	 * of this edge.
	 */
	public int getCost() {
		return cost;
	}
	
	/**
	 * Returns the Neighbor (node) that is at the other end of "this" node's edge.
	 */
	public GraphNode getNeighborNode() {
		return neighbor;
	}
	/**
	 * Returns a String representation of this Neighbor. The String that is returned 
	 * shows an arrow (with the cost in the middle) and then the Neighbor node's name. 
	 * Example:
	 * --1--> b 
	 * indicates a cost of 1 to get to node b
	 */
	@Override
	public String toString() {
		String name = neighbor.toString();
		return "--" + cost + "-->" + name;
	}
	@Override
	public int compareTo(Neighbor com) {
		// TODO Auto-generated method stub
		return neighbor.compareTo(com.getNeighborNode());
	}

}
