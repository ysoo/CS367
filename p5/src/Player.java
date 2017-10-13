///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Game.java
// File:             Player.java
// Semester:         CS 367 Spring 2016
//
// Author:           Yi Xian Soo ysoo@wisc.edu
// CS Login:         ysoo
// Lecturer's Name:  Skrentny
// Lab Section:      1
//
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.ArrayList;
import java.util.List;

public class Player extends Object{
	
	private String name;
	private int budget;
	private int spycams;
	private GraphNode startNode;
	private List<String> spycamLocation;
	
	/**
	 * 
	 * @param name - Player's name
	 * @param budget - Player budget to spend on moves
	 * @param spycams - the number of spy cams the player starts with
	 * @param startnode - the node the player starts on
	 */
	public Player(String name, int budget, int spycams, GraphNode startnode) {
		this.name = name;
		this.budget = budget;
		this.spycams = spycams;
		startNode = startnode;
		spycamLocation = new ArrayList<String>();
	}
	/**
	 * @param dec
	 */
	public void decreaseBudget(int dec) {
		budget = budget - dec;
	}
	/**
	 * If there are no remaining spy cams to drop, display "Not enough spycams"
	 * and return false. Otherwise: If there is not a spy cam at the player's 
	 * current location: drop a spy cam (here) D decrement the remaining spy cam count 
	 * if there was not already a spycam
	 * 
	 * @return true if a spycam is dropped
	 */
	public boolean dropSpycam() {
		if(spycams == 0) {
			System.out.println("Not enough spycams");
			return false;
		} else {
			if(!startNode.getSpycam()) {
				startNode.setSpycam(true);
				spycams--;
				spycamLocation.add(startNode.getNodeName());
				System.out.println("Dropped a Spy Cam at " + startNode.getNodeName());
			} else {
				System.out.println("Already a Spy Cam there");
			}
			return true;
		}
	}
	/**
	 * @return remaining budget
	 */
	public int getBudget() {
		return budget;
		
	}
	/**
	 * Returns the node where the player is currently located.
	 * 
	 * @return player's current node
	 */
	public GraphNode getLocation() {
		return startNode;
		
	}
	/**
	 * 
	 * @return node label for the current location of the player
	 */
	public String getLocationName() {
		return startNode.getNodeName();
		
	}
	/**
	 * @return name of player
	 */
	public String getName() {
		return name;
		
	}
	/**
	 * If pickupSpyCam is true, increment the number of spy cams remaining.
	 * 
	 * @param pickupSpyCam - true if a spy cam was picked up. 
	 * False means there was no spy cam
	 */
	public void getSpyCamBack(boolean pickupSpyCam) {
		if(pickupSpyCam) {
			spycams ++;
		} 
	}
	/**
	 * @return number of spycams remaining
	 */
	public int getSpycams() {
		return spycams;
		
	}
	/**
	 * @param name - Neighboring node to move to
	 * @return true if the player successfully moves to this node if the cost
	 *  is greater than 1, decrement budget by that amount
	 */
	public boolean move(String name) {
		boolean moved = false;
		//Check if node is in list of neighbors
		List<Neighbor> neighbors = startNode.getNeighbors();
		Neighbor temp = null;
		for(int i = 0; i < neighbors.size(); i ++) {
			Neighbor curr = neighbors.get(i); 
			if(name.equals(curr.getNeighborNode().getNodeName())) {
				temp = curr;
			}
		}
		
		if (temp == null) {
			System.out.println(name + " is not a neighbor of your current location");
			return false;
		}
		
		//Check if have enough budget or if cost is 1
		int cost = temp.getCost();
		if(cost <= 1) {
			moved = true;
		} else if (budget > cost){
			decreaseBudget(cost);
			moved = true;
		} else {
			System.out.println("Not enough money cost is "+ cost+ " budget is " + budget);
		}
		
		//Change startnode to new node
		if(moved) {
			startNode = temp.getNeighborNode();
		}
		
		return moved;
	}
	/**
	 * Check the node to see if there is a spy cam. If there is a spy cam 
	 * at that node, remove the spy cam from that node. Also, remove the spy
	 * cam name from the Player's list of spy cam names. Otherwise, return false.
	 * 
	 * @param node - The node the player asked to remove a spy cam from.
	 * @return true if a spycam is retrieved
	 */
	public boolean pickupSpycam(GraphNode node) {
		boolean found = false;
		if(node.getSpycam()) {
			node.setSpycam(false);
			found = true;
			spycamLocation.remove(node.getNodeName());
		}
		getSpyCamBack(found);
		return found;
		
	} 
	/**
	 * Display the names of the locations where Spy Cams were dropped 
	 * (and are still there).
	 */
	public void printSpyCamLocations() {
		for(int i = 0; i < spycamLocation.size(); i++) {
			System.out.println("Spy cam at " + spycamLocation.get(i));
		}
	}
}
