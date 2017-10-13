///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  SetTesterMain.java
// File:             BSTIterator.java
// Semester:         CS367 Spring 2016
//
// Author:           Yi Xian Soo ysoo@wisc.edu
// CS Login:         ysoo
// Lecturer's Name:  Jim Skrentny
// Lab Section:      Lecture 1
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * The Iterator for Binary Search Tree (BST) that is built using Java's Stack
 * class. This iterator steps through the items BST using an INORDER traversal.
 *
 * @author CS367
 */
public class BSTIterator<K> implements Iterator<K> {

	/** Stack to track where the iterator is in the BST*/
	Stack<BSTNode<K>> stack;

	/**
	 * Constructs the iterator so that it is initially at the smallest
	 * value in the set. Hint: Go to farthest left node and push each node
	 * onto the stack while stepping down the BST to get there.
	 *
	 * @param n the root node of the BST
	 */
	public BSTIterator(BSTNode<K> n){
		//TODO
		stack = new Stack<BSTNode<K>>();
		inorder(n);
	}
	/**
	 * Iterates to the farthest left node of the BST to obtain the smallest value
	 * and pushes the node into the stack.
	 * 
	 * @param n the root node of the BST
	 */
	private void inorder(BSTNode<K> n) {
		if(n == null) return;
		stack.push(n);
		inorder(n.getLeftChild());
	}

	/**
	 * Returns true iff the iterator has more items.
	 *
	 * @return true iff the iterator has more items
	 */
	public boolean hasNext() {
		//TODO
		if(stack.isEmpty()) return false;
		else return true;
	}

	/**
	 * Returns the next item in the iteration.
	 *
	 * @return the next item in the iteration
	 * @throws NoSuchElementException if the iterator has no more items
	 */
	public K next() {
		//TODO
		if(!hasNext()) throw new NoSuchElementException();
		BSTNode<K> curr = stack.pop();
		K result = curr.getKey();
		if(curr.getRightChild() != null) {
			curr = curr.getRightChild();
			inorder(curr);
		}
		return result;
	}

	/**
	 * Not Supported
	 */
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
