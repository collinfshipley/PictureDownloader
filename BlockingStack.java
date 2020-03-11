import java.util.concurrent.LinkedBlockingDeque;

/**
 * Extension of LinkedBLockingDeque to act in last in first out order.
 * 
 * @author Collin Shipley
 *
 */
public class BlockingStack<T> extends LinkedBlockingDeque<T> {
	
	/**
	 * Overload of the offer method to act like offerFirst
	 * 
	 * @see java.util.concurrent.LinkedBlockingDeque#offerFirst(java.lang.Object)
	 */
	@Override
	public boolean offer(T t) {
		return super.offerFirst(t);
	}
	
	/**
	 * Overload of remove method to act like removeFirst
	 * 
	 * @see java.util.concurrent.LinkedBlockingDeque#removeFirst()
	 */
	@Override
	public T remove() {
		return super.removeFirst();
	}
}
