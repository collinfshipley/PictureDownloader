
/**
 * Class to define a simple tuple. 
 * 
 * @author Collin Shipley
 * 
 */
public class Pair<T1, T2> {
	public final T1 left;
	public final T2 right;
	
	/**
	 * Constructor
	 * 
	 * @param left	left side of the pair
	 * @param right	right side of the pair
	 */
	public Pair(T1 left, T2 right) {
		this.left = left;
		this.right = right;
	}
}
