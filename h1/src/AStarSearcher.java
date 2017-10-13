/**
 * @author Xian Soo
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * A* algorithm search
 * 
 * You should fill the search() method of this class.
 */
public class AStarSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public AStarSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main a-star search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */

	public boolean search() {

		// FILL THIS METHOD

		// explored list is a Boolean array that indicates if a state associated with a given position in the maze has already been explored. 
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
		for(int i = 0; i < maze.getNoOfRows(); i ++ ) {
			for(int j = 0; j < maze.getNoOfCols(); j ++) {
				explored[i][j] = false;
			}
		}

		PriorityQueue<StateFValuePair> frontier = new PriorityQueue<StateFValuePair>();

		// TODO initialize the root state and add
		// to frontier list
		Square startsq = maze.getPlayerSquare();
		explored[startsq.X][startsq.Y] = true;
		State startst = new State(startsq, null, 0, 0);
		StateFValuePair start = new StateFValuePair(startst, fval(startst));
		frontier.add(start);

		while (!frontier.isEmpty()) {

			noOfNodesExpanded++;
			StateFValuePair curr = frontier.poll();
			explored[curr.getState().getX()][curr.getState().getY()] = true;

			// TODO maintain the cost, noOfNodesExpanded (a.k.a. noOfNodesExplored),
			// maxDepthSearched, maxSizeOfFrontier during
			// the search

			// TODO return true if a solution has been found
			if(curr.getState().isGoal(maze)) {
				maxDepthSearched = curr.getState().getDepth();
				cost = curr.getState().getDepth();

				// TODO update the maze if a solution found
				State curr2;
				curr2 = curr.getState().getParent();
				while(curr2.getParent()!=null) {
					maze.setOneSquare(curr2.getSquare(), '.');
					curr2 = curr2.getParent();
				}
				return true;
			}

			// Get successors for current state
			ArrayList<State> succ = curr.getState().getSuccessors(explored, maze);
			ArrayList<StateFValuePair> fsucc = new ArrayList<StateFValuePair>();

			// Convert from State to StateFValuePair
			for(int i = 0; i < succ.size(); i ++) {
				State temst = succ.get(i);
				StateFValuePair temp = new StateFValuePair(temst, fval(temst));
				fsucc.add(temp);
			}

			// Check for repeated states
			for(int i = 0 ; i < fsucc.size(); i++) {
				StateFValuePair n = fsucc.get(i);

				Iterator<StateFValuePair> itr = frontier.iterator();
				while(itr.hasNext()) {
					StateFValuePair m = itr.next();

					// If n is already in frontier
					if(n.getState().getSquare().X == m.getState().getSquare().X 
							&& n.getState().getSquare().Y == m.getState().getSquare().Y) {
						
						// compare g values of value in list and frontier
						if(n.getFValue() < m.getFValue() ) {
							frontier.remove(m);
						} else {
							fsucc.remove(n);
						}
					}
				}
			}
			frontier.addAll(fsucc);

			if(frontier.size() >= maxSizeOfFrontier) {
				maxSizeOfFrontier = frontier.size();
			}


			// use frontier.poll() to extract the minimum stateFValuePair.
			// use frontier.add(...) to add stateFValue pairs
		}

		// TODO return false if no solution
		return false;
	}

	public double fval(State curr) {
		double u = curr.getX();
		double v = curr.getY();
		double p = maze.getGoalSquare().X;
		double q = maze.getGoalSquare().Y;
		double h = Math.sqrt(Math.pow((u - p),2) + Math.pow((v - q), 2));

		return curr.getGValue() + h;
	}
}
