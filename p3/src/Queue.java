import java.lang.reflect.Array;
import java.util.*;

/**
 * An ordered collection of items, where items are added to the rear and removed
 * from the front.
 */
public class Queue<E> implements QueueADT<E>
{

	// TODO
	// You may use a naive expandable circular array or a chain of listnodes.
	// You may NOT use Java's predefined classes such as ArrayList or
	// LinkedList.
	private E[] list;
	private static int MAX_CAP = 10;
	private int R;
	private int F;
	private int num;

	@SuppressWarnings("unchecked")
	public Queue()
	{
		list = (E[]) new Object[MAX_CAP];
		R = 0;
		num = 0;
		F = 0;
	}

	/**
	 * Adds an item to the rear of the queue.
	 * 
	 * @param item
	 *            the item to add to the queue.
	 * @throws IllegalArgumentException
	 *             if item is null.
	 */
	public void enqueue(E item)
	{
		if(num == list.length) expandCapacity();
		list[R] = item;
		R++;
		num++;
	}

	/**
	 * Removes an item from the front of the Queue and returns it.
	 * 
	 * @return the front item in the queue.
	 * @throws EmptyQueueException
	 *             if the queue is empty.
	 */
	public E dequeue()
	{	
		if(num == 0) throw new EmptyQueueException();
		E thing = list[F];
		list[F] = null;
		F++;
		num--;
		return thing;
	}

	/**
	 * Returns the item at front of the Queue without removing it.
	 * 
	 * @return the front item in the queue.
	 * @throws EmptyQueueException
	 *             if the queue is empty.
	 */
	public E peek()
	{
		return list[F];
	}

	/**
	 * Returns true iff the Queue is empty.
	 * 
	 * @return true if queue is empty; otherwise false.
	 */
	public boolean isEmpty()
	{
		if(num == 0) return true;
		else return false;
	}

	/**
	 * Removes all items in the queue leaving an empty queue.
	 */
	public void clear()
	{
		for(int i = 0; i < list.length; i ++) {
			list[i] = null;
		}
	}

	/**
	 * Returns the number of items in the Queue.
	 * 
	 * @return the size of the queue.
	 */
	public int size()
	{
		return num;
	}

	private void expandCapacity()
	{
		//expanding should be done using the naive copy-all-elements approach

	}
}
