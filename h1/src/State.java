/**
 * @author Xian Soo
 */

import java.util.ArrayList;

/**
 * A state in the search represented by the (x,y) coordinates of the square and
 * the parent. In other words a (square,parent) pair where square is a Square,
 * parent is a State.
 * 
 * You should fill the getSuccessors(...) method of this class.
 * 
 */
public class State {

	private Square square;
	private State parent;

	// Maintain the gValue (the distance from start)
	// You may not need it for the BFS but you will
	// definitely need it for AStar
	private int gValue;
	// States are nodes in the search tree, therefore each has a depth.
	private int depth;

	/**
	 * @param square
	 *            current square
	 * @param parent
	 *            parent state
	 * @param gValue
	 *            total distance from start
	 */
	public State(Square square, State parent, int gValue, int depth) {
		this.square = square;
		this.parent = parent;
		this.gValue = gValue;
		this.depth = depth;
	}

	/**
	 * @param explored
	 *            explored[i][j] is true if (i,j) is already explored
	 * @param maze
	 *            initial maze to get find the neighbors
	 * @return all the successors of the current state
	 */
	public ArrayList<State> getSuccessors(boolean[][] explored, Maze maze) {
		// FILL THIS METHOD

		// New ArrayList for successors
		ArrayList<State> succ = new ArrayList<State>();

		// Current State
		int xc = square.X;
		int yc = square.Y;

		// TODO check all four neighbors (left, down, right, up)
		int left = yc - 1;
		int down = xc + 1;
		int right = yc + 1;
		int up = xc - 1;

		// Create Successor states
		State leftSt = null;
		State downSt = null;
		State rightSt = null;
		State upSt = null;

		//left
		if(valid(xc,left, maze, explored)) {
			leftSt = new State(new Square(xc, left), this, this.gValue + 1, this.depth + 1);
			succ.add(leftSt);
		}

		//down
		if(valid(down,yc, maze, explored)) {
			downSt = new State(new Square(down, yc), this, this.gValue + 1, this.depth + 1);
			succ.add(downSt);
		}

		//right
		if(valid(xc,right, maze, explored)) {
			rightSt = new State(new Square(xc, right), this, this.gValue + 1, this.depth + 1);
			succ.add(rightSt);
		}

		//up
		if(valid(up,yc, maze, explored)) {
			upSt = new State(new Square(up, yc), this, this.gValue + 1, this.depth + 1);
			succ.add(upSt);
		}
		return succ;
	}
	/**
	 * Method to check if a certain coordinate is a valid state
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param maze Maze object of the maze
	 * @param explored 
	 * @return
	 */
	public boolean valid(int x, int y, Maze maze, boolean[][] explored) {
		boolean valid = true;
		char wall = maze.getSquareValue(x, y);

		if(x < 0 || y < 0 || x > maze.getNoOfRows()|| y > maze.getNoOfCols()) 
			valid = false;

		else if(wall == '%') 
			valid = false;

		// Check if coordinate has already been explored
		else if(explored[x][y] == true)
			valid = false;

		return valid;	
	}

	/**
	 * @return x coordinate of the current state
	 */
	public int getX() {
		return square.X;
	}

	/**
	 * @return y coordinate of the current state
	 */
	public int getY() {
		return square.Y;
	}

	/**
	 * @param maze initial maze
	 * @return true is the current state is a goal state
	 */
	public boolean isGoal(Maze maze) {
		if (square.X == maze.getGoalSquare().X
				&& square.Y == maze.getGoalSquare().Y)
			return true;

		return false;
	}

	/**
	 * @return the current state's square representation
	 */
	public Square getSquare() {
		return square;
	}

	/**
	 * @return parent of the current state
	 */
	public State getParent() {
		return parent;
	}

	/**
	 * You may not need g() value in the BFS but you will need it in A-star
	 * search.
	 * 
	 * @return g() value of the current state
	 */
	public int getGValue() {
		return gValue;
	}

	/**
	 * @return depth of the state (node)
	 */
	public int getDepth() {
		return depth;
	}
}
