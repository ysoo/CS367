/**
 * @author Xian Soo
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Breadth-First Search (BFS)
 * 
 * You should fill the search() method of this class.
 */
public class BreadthFirstSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public BreadthFirstSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main breadth first search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {
		// FILL THIS METHOD

		// explored list is a 2D Boolean array that indicates if a state associated with a given position in the maze has already been explored.
		// Initialize explored to be 2D array of false values
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
		for(int i = 0; i < maze.getNoOfRows(); i ++ ) {
			for(int j = 0; j < maze.getNoOfCols(); j ++) {
				explored[i][j] = false;
			}
		}

		// Queue implementing the Frontier list 
		LinkedList<State> queue = new LinkedList<State>();

		// Add start node into the queue
		Square startsq = maze.getPlayerSquare();
		explored[startsq.X][startsq.Y] = true;
		State start = new State(startsq, null, 0,  0);
		queue.add(start);

		while (!queue.isEmpty()) {
			// retrieve first element from queue
			State curr = queue.pop();
			explored[curr.getX()][curr.getY()] = true;
			noOfNodesExpanded++;

			// TODO maintain the cost, noOfNodesExpanded (a.k.a. noOfNodesExplored),
			// maxDepthSearched, maxSizeOfFrontier during
			// the search

			// TODO return true if find a solution
			if(maze.getGoalSquare().X == curr.getSquare().X  
					&& maze.getGoalSquare().Y == curr.getSquare().Y) {
				maxDepthSearched = curr.getDepth();
				cost = curr.getDepth();

				// TODO update the maze if a solution found
				curr = curr.getParent();
				while(curr.getParent()!=null) {
					maze.setOneSquare(curr.getSquare(), '.');
					curr = curr.getParent();
				}
				return true;
			}

			ArrayList<State> succ = curr.getSuccessors(explored, maze);

			// Check if newly generated nodes does not have same state in frontier
			for(int i = 0 ; i < succ.size(); i++) {
				State c = succ.get(i);
				Iterator<State> itr = queue.iterator();
				while(itr.hasNext()) {
					State q = itr.next();
					if(q.getSquare().X == c.getSquare().X && q.getSquare().Y == c.getSquare().Y) {
						succ.remove(c);
					}
				}
			}

			queue.addAll(succ);
			if(queue.size() > maxSizeOfFrontier) {
				maxSizeOfFrontier = queue.size();
			}
		}
		// TODO return false if no solution
		return false;
	}


}
