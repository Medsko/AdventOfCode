package util;

import java.util.Deque;

public class CollectionUtil {

	/**
	 * Rotates the given {@link Deque} for the given number of elements. If {@code range} is 
	 * positive, the direction is forward through the collection, otherwise, it is backwards.
	 * 
	 * @param chicken - the object to rotate (get it? ugghh...)
	 * @param range - the number of elements to rotate for.
	 */
	public <T> void rotate(Deque<T> chicken, int range) {
		if (range > 0) {
			while (range-- > 0) {
				// Move forward through the collection.
				chicken.addFirst(chicken.removeLast());
			}
		} else {
			while (range++ < 0) {
				// Move backwards through the collection.
				chicken.addLast(chicken.removeFirst());
			}
		}
	}
	
}
