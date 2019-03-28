package util;

import java.util.Arrays;

public class ArrayUtil {

	public static int max(int[] arr) {
		int max = Integer.MIN_VALUE;
		for (int i : arr)
			if (i > max)
				max = i;
		return max;
	}
	
	public static long max(long[] arr) {
		long max = Long.MIN_VALUE;
		for (long l : arr)
			if (l > max)
				max = l;
		return max;
	}
	
	public static int sum(Integer[] arr) {
		int total = 0;
		for (int i : arr)
			total += i;
		return total;
	}
	
	public static int sum(int[] arr) {
		int total = 0;
		for (int i : arr)
			total += i;
		return total;
	}
	
	/**
	 * Converts the given String array to an integer array. Will throw all the usual RTE's if
	 * provided String array is null, contains invalid values etc. so use at own peril.
	 */
	public static int[] stringToInt(String[] numberStrings) {
		int[] numbers = new int[numberStrings.length];
		for (int i=0; i< numberStrings.length; i++) {
			numbers[i] = Integer.parseInt(numberStrings[i].trim());
		}
		return numbers;
	}
	
	public static String squareToString(int[][] intSquare) {
		String toString = "";
		for (int[] row : intSquare) {
			toString += Arrays.toString(row) + System.lineSeparator();
		}
		return toString;
	}
	
	/**
	 * Returns a copy of the provided two-dimensional array that has been rotated clockwise.
	 * The provided array should hold arrays that are all of equal length, and should have the same
	 * length as the two-dimensional array.
	 * 
	 * @param original - the two-dimensional array that will be rotated.
	 * @return the rotated array.
	 */
	public static char[][] rotate(char[][] original) {
		char[][] rotated = deepCopyTwoDimensional(original);
		
		for (int i=0; i<original.length; i++) {
			int innerArrayLength = original[i].length;
			for (int j=0; j<innerArrayLength; j++) {
				// Convert left-to-right to top-to-bottom and top-to-bottom to right-to-left.
				rotated[j][innerArrayLength - i - 1] = original[i][j];
			}
		}
		return rotated;
	}
	
	/**
	 * Returns a copy of the provided two-dimensional array that has been flipped along the y axis.
	 * The provided array should hold arrays that are all of equal length, and should have the same
	 * length as the two-dimensional array.
	 * 
	 * @param original - the two-dimensional array that will be flipped.
	 * @return the flipped array.
	 */
	public static char[][] flip(char[][] original) {
		char[][] flipped = deepCopyTwoDimensional(original);
		
		for (int i=0; i<flipped.length; i++) {
			reverse(flipped[i]);
		}
		return flipped;
	}
	
	/**
	 * Reverses the given array. 
	 */
	public static void reverse(char[] arr) {
		for (int i=0; i<arr.length/2; i++) {
			char temp = arr[i];
			arr[i] = arr[arr.length - i - 1];
			arr[arr.length - i - 1] = temp;
		}
	}
	
	public static char[][] deepCopyTwoDimensional(char[][] original) {
		if (original == null)
			return null;
		
		char[][] copy = new char[original.length][];
		for (int i=0; i<original.length; i++) {
			copy[i] = Arrays.copyOf(original[i], original[i].length);
		}
		return copy;
	}
	
	public static String noCommaToString(char[] arr) {
		String toString = "";
		for (char c : arr) {
			toString += c;
		}
		return toString + "";
	}
}
