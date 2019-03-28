package util;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * This utility class is intended to make sorting Map objects easy.
 * When {@link #sortMap(Map, Comparator)} is called with a {@link Map} to be sorted and a 
 * {@link Comparator} specifying how sorting should be structured, the given Map will
 * be returned as a sorted TreeMap.
 * 
 * Several reusable implementations of {@link Comparator} are provided as static inner classes.
 */
public class MapSortingUtil {

	// A curse on those who would instantiate a utility class!
	private MapSortingUtil() {}
	
	// Several inner classes implementing {@link Comparator}, to facilitate the use of {@link sortMapByKeys}. 
	
	/** Compares integer arrays representing [x, y] positions on a matrix. Compares by y first, then by x. */
	public static class PositionComparator implements Comparator<int[]> {

		@Override
		public int compare(int[] first, int[] second) {
			// First, order by y axis. 
			if (first[1] > second[1])
				return 1;
			else if (first[1] < second[1])
				return -1;
			// If y axis is equal, compare by x axis.
			else if (first[0] > second[0])
				return 1;
			else if (first[0] < second[0])
				return -1;
			else
				return 0;
		}
	}
	
	/** 
	 * Similar to {@link PositionComparator}, but sorts the y axis backwards, so that the position with 
	 * the highest y and the lowest x is the lowest (first in the sorted collection).
	 */
	public static class SpiralMatrixComparator implements Comparator<int[]> {
		@Override
		public int compare(int[] first, int[] second) {
			// First, order by y axis. To make it easier to print the result, make sure the highest y is put
			// at the beginning of the collection.
			if (first[1] > second[1])
				return -1;
			else if (first[1] < second[1])
				return 1;
			// If y axis is equal, compare by x axis.
			else if (first[0] > second[0])
				return 1;
			else if (first[0] < second[0])
				return -1;
			else
				return 0;
		}
	}
	
	
	// Inner class that makes it possible to compare entries of a Map by values instead of keys.
	/**
	 * Inner class that can be used to create a Comparator that compares two keys in the given
	 * Map by their corresponding values. This is achieved by using the keys to get their values 
	 * from the Map and calling compareTo() on these values, thereby effectively comparing the
	 * entries in by value. Because compareTo() is called on the values, these should be of 
	 * a type that implements {@link Comparable}.
	 */
	public static class ValueComparator<K, V extends Comparable<V>> implements Comparator<K> {
		
		private Map<K, V> map;
		
		/** 
		 * Constructor that takes the Map that will be sorted as an argument. 
		 * When compare() is called, the keys are used to retrieve their values from the 
		 * {@link #map} so these values can be compared instead.
		 */
		public ValueComparator(Map<K, V> map) {
			this.map = map;
		}

		@Override
		public int compare(K first, K second) {
			// Use the two keys to get their value, and order the entries by 
			// comparing their values.
			return map.get(first).compareTo(map.get(second));
		}
	}
	
	
	/**
	 * Sorts the entries of the given map by their keys, in the manner specified in the given
	 * comparator. 
	 * @param Map<K,V> mapToSort - the map of which the entries should be sorted.
	 * @param Comparator<K> comparator - 
	 * @return TreeMap sortedMap - a TreeMap holding the sorted entries of mapToSort.
	 */
	public static <K, V> TreeMap<K, V> sortMap(Map<K, V> mapToSort, Comparator<K> comparator) {
		TreeMap<K, V> sortedMap = new TreeMap<>(comparator);
		sortedMap.putAll(mapToSort);
		return sortedMap;
	}
	
}
