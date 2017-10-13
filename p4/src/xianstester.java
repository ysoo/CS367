import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class xianstester<K> {

	public static void main(String[] args) {
		// Create a couple of BST Nodes of type Integer
		/**
		BSTNode<Integer> one = new BSTNode<Integer>(1);
		BSTNode<Integer> two = new BSTNode<Integer>(2);
		BSTNode<Integer> three = new BSTNode<Integer>(3);
		BSTNode<Integer> four = new BSTNode<Integer>(4);
		BSTNode<Integer> five = new BSTNode<Integer>(5);
		**/
		
		//Create an array to test array to BST 
		/** Integer[] arr = new Integer[6];
		arr[0] = 1;
		arr[1] = 2;
		arr[2] = 4;
		arr[3] = 5;
		arr[4] = 6;
		arr[5] = 7;
		
		BSTNode<Integer> n = sortedArrayToBST(arr,0,5);
		print(n);
		System.out.println();
		System.out.print(n.getHeight());
		System.out.println();
		System.out.println(n.getBalanceFactor());
		**/
		
		// Create a BST tree by adding
		BSTreeSetTester<Integer> tree = new BSTreeSetTester<Integer>(1);
		tree.add(42);
		tree.add(13);
		tree.add(76);
		tree.add(86);
		tree.add(31);
		tree.add(93);
		tree.add(15);
		tree.add(98);
		tree.add(66);
		tree.add(72);
	
		//print(tree.root);
		
		// CONTAINS
		System.out.println(tree.contains(72));

		// SUBSET
		List<Integer> l = tree.subSet(13,72);
		for(int i = 0; i < l.size(); i++) {
			System.out.print(l.get(i) + " ");
		}

		// CLEAR
		//tree.clear();
		
	
		// Create an iterator and print out to see if working / inorder
		System.out.println();
		Iterator<Integer> itr = tree.iterator();
		while(itr.hasNext()) {
			System.out.print(itr.next() + " ");
		}
		
		// Check if rebalance is working
		/**Integer[] keys = rebalance(tree.root);
		for(int i = 0; i < keys.length; i ++) {
			System.out.print(keys[i]);
		}
		sortedArrayToBST(keys, 0, 1);
		**/

		// SIZE
		//int size = tree.size();
		//System.out.print(size);
				
	}
	//preorder
	
	public static Integer[] rebalance(BSTNode<Integer> root) {
		Integer[] keys = new Integer[5];
		//TODO
		BSTIterator<Integer> itr = new BSTIterator<Integer>(root);
		int num = 0;
		while(itr.hasNext()) {
			keys[num] = itr.next();
			num++;
		}
		return keys;
	}
	private static void print(BSTNode<Integer> root) {
		if(root == null) return;
		System.out.print(root.getKey());
		print(root.getLeftChild());
		print(root.getRightChild());
	}
	
    private static BSTNode<Integer> sortedArrayToBST(Integer[] arr, int start, int stop) {
        //TODO
    	if(start > stop) return null;
    	
    	int mid = start + ((stop - start) / 2);
    	
    	BSTNode<Integer> node = new BSTNode<Integer>(arr[mid]);
    	
    	BSTNode<Integer> l = sortedArrayToBST(arr, start, mid - 1);
    	node.setLeftChild(l);
    	node.setHeight(node.getHeight() + 1);
    	node.setBalanceFactor(node.getBalanceFactor() + 1 );
    	
    	BSTNode<Integer> r = sortedArrayToBST(arr, mid + 1, stop);
    	node.setRightChild(r);
    	node.setHeight(node.getHeight() + 1);
    	node.setBalanceFactor(node.getBalanceFactor() - 1 );
   
        return node;
    }
    private static BSTNode<Integer> find(Integer min, BSTNode<Integer> n) {
    	if(n.getKey().equals(min)) return n;
    	else if(n.getKey().compareTo(min) < 0) {
    			 return find(min, n.getRightChild());
    		} else {
    			return find(min, n.getLeftChild());
    		} 
    	
   
    }
}
