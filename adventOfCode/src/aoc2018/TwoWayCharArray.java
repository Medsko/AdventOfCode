package aoc2018;

import java.util.Arrays;

/**
 * Wrapper class for an array that supports negative indices. 
 */
public class TwoWayCharArray {

	private char[] arr;
	
	/**
	 * The initial value for each field in the array.
	 */
	private char defaultValue;
	
	/**
	 * The distance to the actual zero index of the array from the index provided by the user.
	 */
	private int toZero;
	
	/**
	 * Default constructor. Initializes an array of size 1. 
	 */
	public TwoWayCharArray(char defaultValue) {
		this.defaultValue = defaultValue;
		arr = new char[1];
		arr[0] = defaultValue;
	}
	
	/**
	 * Constructor that initializes an array with the given initial state. 
	 */
	public TwoWayCharArray(char defaultValue, char[] initialState, int negativeCapacity) {
		this.defaultValue = defaultValue;
		arr = initialState;
		toZero = negativeCapacity;
	}
	
	public TwoWayCharArray(char defaultValue, int totalCapacity, int negativeCapacity) {
		this(defaultValue);
		arr = new char[totalCapacity];
		Arrays.fill(arr, defaultValue);
		toZero = negativeCapacity;
	}
	
	public char getAt(int index) {
		index = trueIndex(index);
		if (index < 0)
			throw new ArrayIndexOutOfBoundsException("What d'ya think happened?");
		return arr[index];
	}
	
	public void setAt(char value, int index) {
		if (trueIndex(index) < 0)
			// Index out of bounds. Resize the array.
			fit(index);
		arr[index + toZero] = value;
	}
	
	/**
	 * Returns a <strong>copy</strong> of the specified portion of the array.
	 * @param start - the starting index of the desired portion, inclusive.
	 * @throws ArrayIndexOutOfBoundsException - if either the {@code start} or {@code to} parameter 
	 * is out of bounds of the array.
	 */
	public char[] portion(int start, int to) {
		// Convert to true indices.
		int trueStart = trueIndex(start), trueTo = trueIndex(to);
		if (trueStart < 0)
			throw new ArrayIndexOutOfBoundsException("The specified range is outside of "
					+ "array bounds!");
		return Arrays.copyOfRange(arr, trueStart, trueTo > 0 ? trueTo : arr.length);
	}
	
	/**
	 * Translates the given index - which might be negative - to its actual position in the array -
	 * which may not. 
	 * @param index - the index to test.
	 * @return the converted, or 'true' index, or -1 if index is out of bounds.
	 */
	private int trueIndex(int index) {
		index += toZero;
		if (index < 0 || index >= arr.length) {
			// Index is out of range of current array bounds.
			return -1;
		}
		return index;
	}
	
	/**
	 * Checks whether the given index falls inside the capacity of the array. If out of bounds, 
	 * the capacity is increased to fit the index.
	 */
	public void fit(int index) {
		if (index < 0 && Math.abs(index) > toZero) {
			// Increase the negative capacity.
			int overflow = Math.abs(index) - toZero;
			int newCapacity = arr.length + overflow;
			char[] temp = new char[newCapacity];
			// Fill the fields of the new array with the default value.
			Arrays.fill(temp, defaultValue);
			System.arraycopy(arr, 0, temp, overflow, arr.length);
						
			arr = temp;
			toZero = Math.abs(index);

		} else if (index >= 0 && index >= arr.length - toZero) {
			// Increase the positive capacity.
			int overflow = index - (arr.length - toZero);
			int newCapacity = arr.length + overflow;
			char[] temp = new char[newCapacity];
			// Fill the fields of the new array with the default value.
			Arrays.fill(temp, defaultValue);

			System.arraycopy(arr, 0, temp, 0, arr.length);			
			arr = temp;
		}
	}
	
	public int getLowerLimit() {
		return -toZero;
	}
	
	/**
	 * Returns upper limit of this array, i.e. the 'length' of the positive part. 
	 */
	public int getUpperLimit() {
		return arr.length - toZero;
	}
	
	@Override
	public String toString() {
		return Arrays.toString(arr);
	}
}
