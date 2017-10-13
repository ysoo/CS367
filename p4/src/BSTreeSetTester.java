///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  SetTesterMain.java
// File:             BSTreeSetTester.java
// Semester:         CS367 Spring 2016
//
// Author:           Yi Xian Soo ysoo@wisc.edu
// CS Login:         ysoo
// Lecturer's Name:  Jim Skrentny
// Lab Section:      Lecture 1
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * SetTesterADT implementation using a Binary Search Tree (BST) as the data
 * structure.
 *
 * <p>The BST rebalances if a specified threshold is exceeded (explained below).
 * If rebalanceThreshold is non-positive (&lt;=0) then the BST DOES NOT
 * rebalance. It is a basic BStree. If the rebalanceThreshold is positive
 * then the BST does rebalance. It is a BSTreeB where the last B means the
 * tree is balanced.</p>
 *
 * <p>Rebalancing is triggered if the absolute value of the balancedFfactor in
 * any BSTNode is &gt;= to the rebalanceThreshold in its BSTreeSetTester.
 * Rebalancing requires the BST to be completely rebuilt.</p>
 *
 * @author CS367
 */
public class BSTreeSetTester <K extends Comparable<K>> implements SetTesterADT<K>{

	/** Root of this tree */
	BSTNode<K> root;

	/** Number of items in this tree*/
	int numKeys;

	/**
	 * A value that used to determine whether your tree is balanced or not. If 
	 * the tree's threshold is positive and the absolute value of 
	 * balanceFactor of any node is larger than the rebalanceThreshold, then 
	 * we say the tree is imbalanced(needs to be rebalanced). Note: If the 
	 * threshold is less than or equal to 0, we consider the tree as balanced.
	 */
	int rebalanceThreshold;

	/**
	 * True iff tree is balanced, i.e., if rebalanceThreshold is NOT exceeded
	 * by absolute value of balanceFactor in any of the tree's BSTnodes.Note if
	 * rebalanceThreshold is non-positive, isBalanced must be true.
	 */
	boolean isBalanced;


	/**
	 * Constructs an empty BSTreeSetTester with a given rebalanceThreshold.
	 *
	 * @param rbt the rebalance threshold
	 */
	public BSTreeSetTester(int rbt) {
		//TODO
		rebalanceThreshold = rbt;
		numKeys = 0;
		root = null;
	}

	/**
	 * Add node to binary search tree. Remember to update the height and
	 * balancedFfactor of each node. Also rebalance as needed based on
	 * rebalanceThreshold.
	 *
	 * @param key the key to add into the BST
	 * @throws IllegalArgumentException if the key is null
	 * @throws DuplicateKeyException if the key is a duplicate
	 */
	public void add(K key){
		//TODO
		if(key == null) throw new IllegalArgumentException();
		numKeys++;
		root = add(root, key);
		if(!isBalanced) rebalance();
	}
	/**
	 * Helper method for add method
	 * @param n root node of the BST
	 * @param key the key to add into the BST
	 * @return root node of the BST
	 */
	
	private BSTNode<K> add(BSTNode<K> n ,K key) {
		if(n == null) {
			BSTNode<K> node =  new BSTNode<K>(key);
			return node;
		}
		
		if(n.getKey().equals(key)) {
			throw new DuplicateKeyException();
		}
		
		if(key.compareTo(n.getKey()) < 0) {
			n.setLeftChild(add(n.getLeftChild(), key));
		} else {
			n.setRightChild(add(n.getRightChild(), key));			
		}
		int height = 0;
		int BF = 0;
		if(n.getLeftChild()!= null && n.getRightChild() != null) {
			if(	n.getRightChild().getHeight() < n.getLeftChild().getHeight()) {
				height = n.getLeftChild().getHeight() + 1; 
			} else {
				height = n.getRightChild().getHeight() + 1;
			}
			BF = n.getLeftChild().getHeight() - n.getRightChild().getHeight();

		} else if (n.getLeftChild()!= null && n.getRightChild() == null){
			height = n.getLeftChild().getHeight() + 1;
			BF = n.getLeftChild().getHeight();
		} else if (n.getRightChild()!= null && n.getLeftChild() == null){
			height = n.getRightChild().getHeight() + 1;
			BF = 0 - n.getRightChild().getHeight();	
		} else {
		}

		n.setHeight(height);
		n.setBalanceFactor(BF);
		if(rebalanceThreshold > 0 && Math.abs(BF) > rebalanceThreshold) 
			isBalanced = false;
		
		return n;
	}

	/**
	 * Rebalances the tree by:
	 * 1. Copying all keys in the BST in sorted order into an array.
	 *    Hint: Use your BSTIterator.
	 * 2. Rebuilding the tree from the sorted array of keys.
	 */
	public void rebalance() {
		K[] keys = (K[]) new Comparable[numKeys];
		//TODO
		Iterator<K> itr = this.iterator();
		int num = 0;
		while(itr.hasNext()) {
			keys[num] = itr.next();
			num++;
		}

		root = sortedArrayToBST(keys, 0, keys.length - 1);
		isBalanced = true;
	}

	/**
	 * Recursively rebuilds a binary search tree from a sorted array.
	 * Reconstruct the tree in a way similar to how binary search would explore
	 * elements in the sorted array. The middle value in the sorted array will
	 * become the root, the middles of the two remaining halves become the left
	 * and right children of the root, and so on. This can be done recursively.
	 * Remember to update the height and balanceFactor of each node.
	 *
	 * @param keys the sorted array of keys
	 * @param start the first index of the part of the array used
	 * @param stop the last index of the part of the array used
	 * @return root of the new balanced binary search tree
	 */
	private BSTNode<K> sortedArrayToBST(K[] keys, int start, int stop) {
		//TODO
		if(start > stop) return null;

		int mid = start + (stop - start) / 2;

		BSTNode<K> n = new BSTNode<K>(keys[mid]);

		n.setLeftChild(sortedArrayToBST(keys, start, mid - 1));
		
		n.setRightChild(sortedArrayToBST(keys, mid + 1, stop));
		
		int height = 1;
		int BF = 1;
		if(n.getLeftChild()!= null && n.getRightChild() != null) {
			if(	n.getRightChild().getHeight() < n.getLeftChild().getHeight()) {
				height = n.getLeftChild().getHeight() + 1; 
			} else {
				height = n.getRightChild().getHeight() + 1;
			}
			BF = n.getLeftChild().getHeight() - n.getRightChild().getHeight();

		} else if (n.getLeftChild()!= null && n.getRightChild() == null){
			height = n.getLeftChild().getHeight() + 1;
			BF = n.getLeftChild().getHeight();
		} else if (n.getRightChild()!= null && n.getLeftChild() == null){
			height = n.getRightChild().getHeight() + 1;
			BF = 0 - n.getRightChild().getHeight();	
		} else  {}

		n.setHeight(height);
		n.setBalanceFactor(BF);
		if(rebalanceThreshold > 0 && Math.abs(BF) > rebalanceThreshold) 
			isBalanced = false;
		
		return n;
	}

	/**
	 * Returns true iff the key is in the binary search tree.
	 *
	 * @param key the key to search
	 * @return true if the key is in the binary search tree
	 * @throws IllegalArgumentException if key is null
	 */
	public boolean contains(K key) {
		//TODO
		if(key == null) throw new IllegalArgumentException();
		boolean contains = contains(key, root);
		return contains;
	}
	
	/**
	 * Helper method for the contains method
	 * @param key the key to search
	 * @param n root node of the BST
	 * @return true if the the key is in the binary search tree
	 */
	private boolean contains(K key, BSTNode<K> n){
		if (n == null) return false;
		if(n.getKey().equals(key)) 
			return true;
		if(n.getKey().compareTo(key) > 0) {
			return contains(key, n.getLeftChild());
		} else {
			return contains(key, n.getRightChild());
		}
	}

	/**
	 * Returns the sorted list of keys in the tree that are in the specified
	 * range (inclusive of minValue, exclusive of maxValue). This can be done
	 * recursively.
	 *
	 * @param minValue the minimum value of the desired range (inclusive)
	 * @param maxValue the maximum value of the desired range (exclusive)
	 * @return the sorted list of keys in the specified range
	 * @throws IllegalArgumentException if either minValue or maxValue is
	 * null, or minValue is larger than maxValue
	 */
	public List<K> subSet(K minValue, K maxValue) {
		//TODO
		if(minValue == null || maxValue == null || minValue.compareTo(maxValue) > 0)
			throw new IllegalArgumentException();

		//Create a new List to store keys
		List<K> l = new ArrayList<K>();	
		find(minValue, maxValue, root, l);
		return l;
	}
	/**
	 * Helper method for the subSet method
	 * @param min minimum value of the desired range
	 * @param max maximum value of the desired range
	 * @param n root node of the BST
	 * @param l sorted list of keys in the specified range
	 */
	private void find(K min, K max,  BSTNode<K> n, List<K> l) {
		if(n == null) return;
		K k = n.getKey();
		find(min, max, n.getLeftChild(), l);
		if(k.compareTo(min) >= 0 && k.compareTo(max) < 0 )
			l.add(k);
		find(min, max, n.getRightChild(), l);
	}

	/**
	 * Return an iterator for the binary search tree.
	 * @return the iterator
	 */
	public Iterator<K> iterator() {
		//TODO
		return new BSTIterator<K>(root);
	}

	/**
	 * Clears the tree, i.e., removes all the keys in the tree.
	 */
	public void clear() {
		//TODO
		if(root!=null) {
			root.setLeftChild(null);
			root.setRightChild(null);
		}
		root = null;
		numKeys = 0;
	}

	/**
	 * Returns the number of keys in the tree.
	 *
	 * @return the number of keys
	 */
	public int size() {
		//TODO
		return numKeys;
	}

	/**
	 * Displays the top maxNumLevels of the tree. DO NOT CHANGE IT!
	 *
	 * @param maxDisplayLevels from the top of the BST that will be displayed
	 */
	public void displayTree(int maxDisplayLevels) {
		if (rebalanceThreshold > 0) {
			System.out.println("---------------------------" +
					"BSTreeBSet Display-------------------------------");
		} else {
			System.out.println("---------------------------" +
					"BSTreeSet Display--------------------------------");   
		}
		displayTreeHelper(root, 0, maxDisplayLevels);
	}

	private void displayTreeHelper(BSTNode<K> n, int curDepth,
			int maxDisplayLevels) {
		if(maxDisplayLevels <= curDepth) return;
		if (n == null)
			return;
		for (int i = 0; i < curDepth; i++) {
			System.out.print("|--");
		}
		System.out.println(n.getKey() + "[" + n.getHeight() + "]{" +
				n.getBalanceFactor() + "}");
		displayTreeHelper(n.getLeftChild(), curDepth + 1, maxDisplayLevels);
		displayTreeHelper(n.getRightChild(), curDepth + 1, maxDisplayLevels);
	}
}
